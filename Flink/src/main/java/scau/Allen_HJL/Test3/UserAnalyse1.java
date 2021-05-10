package scau.Allen_HJL.Test3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.text.SimpleDateFormat;
import java.util.*;

public class UserAnalyse1 {


    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.37.129:9092");
        properties.setProperty("group.id", "UserAnalyse2");    // 第 1 个参数是固定值 group.id，第 2 个参数是自定义的组 ID
        properties.setProperty("auto.offset.reset", "latest");
        DeserializationSchema<String> deserializationSchema = new SimpleStringSchema();
        String topic = "db_user";
        DataStream<String> dataStream = env.addSource(new FlinkKafkaConsumer<String>(topic, deserializationSchema, properties));

        dataStream
                .map(new MapFunction<String, User>() {
                    @Override
                    public User map(String s) throws Exception {
//                        System.out.println("s= " + s);
                        JSONObject jsonObject = JSON.parseObject(s);
//                List<String> data = new ArrayList<>();
//                data = JSONObject.parseArray()
                        Long timeStamp = jsonObject.getLong("ts");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dateString = format.format(timeStamp);//注意这里返回的是string类型
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//                Integer id = jsonArray.getInteger(0);
//                String name = jsonArray.getString(1);
                        Integer id = jsonObject1.getInteger("user_id");
                        Integer age = jsonObject1.getInteger("user_age");
                        String name = jsonObject1.getString("user_name");
                        String home = jsonObject1.getString("user_home");
                        Integer outcome = jsonObject1.getInteger("user_outcome");
//                        String source = jsonObject1.getString("user_source");
//                        long income = 5000 + (int) (Math.random() * (25000 - 5000 + 1));
//                        System.out.println(id + "--" + age + "--" + name + "--" + home + "--" + timeStamp
//                                + "--" + dateString + "--" + income + "--" + source);
                        User user = new User(id, name, age, home, timeStamp, outcome);
                        System.out.println("11111:" + user);
                        return user;
                    }
                })
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<User>() {
                    @Override
                    public long extractAscendingTimestamp(User user) {
                        return user.getTimeStamp();
                    }
                })
                .keyBy(User::getUserHome)
                .timeWindow(Time.seconds(1)).aggregate(new MyAggregateFunc(), new MyWindowFunc())
                .keyBy(UserHomeAndOutcome::getWindowEnd)
                .timeWindowAll(Time.seconds(1))
                .process(new MyProcessFunc(5))
                .setParallelism(1);


        dataStream.print();

        env.execute("Flink-Kafka demo");
    }

    public static class MyAggregateFunc implements AggregateFunction<User, Long, Long> {

        @Override
        public Long createAccumulator() {
            return 0L;
        }

        @Override
        public Long add(User user, Long aLong) {
            return user.getUserOutcome() + aLong;
        }

        @Override
        public Long getResult(Long aLong) {
            return aLong;
        }

        @Override
        public Long merge(Long aLong, Long acc1) {
            return acc1 + aLong;
        }
    }


    public static class MyWindowFunc implements WindowFunction<Long, UserHomeAndOutcome, String, TimeWindow> {

        @Override
        public void apply(String key, TimeWindow timeWindow, Iterable<Long> iterable, Collector<UserHomeAndOutcome> collector) throws Exception {
            Long sumIncome = iterable.iterator().next();
            collector.collect(new UserHomeAndOutcome(key, sumIncome, timeWindow.getEnd()));
        }
    }

    public static class MyProcessFunc extends ProcessAllWindowFunction<UserHomeAndOutcome, String, TimeWindow>{

        private Jedis jedis;
        private MapState<String, UserHomeAndOutcome> userHomeAndOutcomeMapState;
        private Integer topSize;

        //TopN排序的N
        public MyProcessFunc(int topSize){
            this.topSize = topSize;
        }

        //连接Redis,以及得到MapState状态
        @Override
        public void open(Configuration parameters) throws Exception {
            jedis = new Jedis("192.168.37.129", 6379);
            userHomeAndOutcomeMapState = getRuntimeContext().getMapState(new MapStateDescriptor<String, UserHomeAndOutcome>("userMapState", String.class, UserHomeAndOutcome.class));
        }

        //将数据进行统计并转换成JSON格式发往Redis
        @Override
        public void process(Context context, Iterable<UserHomeAndOutcome> iterable, Collector<String> collector) throws Exception {
            Iterator<UserHomeAndOutcome> iterator = iterable.iterator();
            ArrayList<UserHomeAndOutcome> userHomeAndOutcomes = new ArrayList<>();
            while(iterator.hasNext()){
                UserHomeAndOutcome newUser = iterator.next();
                String userHome = newUser.getUserHome();
                UserHomeAndOutcome userHomeAndOutcome = userHomeAndOutcomeMapState.get(userHome);
                if(userHomeAndOutcome != null){
                    userHomeAndOutcome.setUserOutcome(userHomeAndOutcome.getUserOutcome() + newUser.getUserOutcome());
                }else{
                    userHomeAndOutcomeMapState.put(userHome, newUser);
                }
//                userHomeAndOutcomes.add(userHomeAndOutcomeMapState.get(userHome));
            }
            Iterable<String> keys = userHomeAndOutcomeMapState.keys();
            for( String key : keys){
                userHomeAndOutcomes.add(userHomeAndOutcomeMapState.get(key));
            }
//            System.out.println("!!!!!!!!!!! "+ userHomeAndOutcomes);
//            System.out.println("@@@@@@@@@@@ "+ userHomeAndOutcomes.toArray());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userHomeAndOutcomes", userHomeAndOutcomes.toArray());
//            System.out.println("222" + jsonObject);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//            jsonObject.put("windowsEnd", simpleDateFormat.format(new Date(context.window().getEnd())));
            String s = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
            System.out.println("s = " + s);
            Transaction multi = jedis.multi();
            String key = "USER:OUTCOME";
            multi.set(key, s);
            multi.publish(key, s);
            multi.exec();

            ArrayList<UserHomeAndOutcome> sortUserHomeAndOutcomes = new ArrayList<>();
            Collections.sort(userHomeAndOutcomes);
            int length = userHomeAndOutcomes.size();
//            topSize = Math.min(length, topSize);
            for(int i=0; i<topSize; i++){
                if( (i+1) > length ) break;
                sortUserHomeAndOutcomes.add(userHomeAndOutcomes.get(i));
            }
            JSONObject sortJsonObject = new JSONObject();
            sortJsonObject.put("sortUserHomeAndOutcomes", sortUserHomeAndOutcomes.toArray());
            String sortString = JSON.toJSONString(sortJsonObject, SerializerFeature.PrettyFormat);
            System.out.println("sortString = " + sortString);
            Transaction sortMulti = jedis.multi();
            String sortKey = "USER:OUTCOME:SORT";
            sortMulti.set(sortKey, sortString);
            sortMulti.publish(sortKey, sortString);
            sortMulti.exec();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(s).append("\n")
                    .append(sortString).append("\n");
            collector.collect(stringBuilder.toString());
        }
    }

}
