package scau.Allen_HJL.Test3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.text.SimpleDateFormat;
import java.util.*;

public class ItemAnalyse {

    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
//        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
//        env.enableCheckpointing(60 * 1000, CheckpointingMode.EXACTLY_ONCE);
//        env.getCheckpointConfig().setCheckpointTimeout(30 * 1000);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.37.129:9092");
        properties.setProperty("group.id", "ItemAnalyse");    // 第 1 个参数是固定值 group.id，第 2 个参数是自定义的组 ID
        properties.setProperty("auto.offset.reset", "latest");
        DeserializationSchema<String> deserializationSchema = new SimpleStringSchema();
        String topic = "db_item";
        DataStream<String> dataStream = env.addSource(new FlinkKafkaConsumer<String>(topic, deserializationSchema, properties));

        dataStream
                .map(new MapFunction<String, Item>() {
                    @Override
                    public Item map(String s) throws Exception {
//                        System.out.println("s= " + s);
                        JSONObject jsonObject = JSON.parseObject(s);
                        Long timeStamp = jsonObject.getLong("ts");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        Integer id = jsonObject1.getInteger("item_id");
                        String name = jsonObject1.getString("item_name");
                        Integer num = jsonObject1.getInteger("item_num");
                        Integer price = jsonObject1.getInteger("item_price");
                        String type = jsonObject1.getString("item_type");
                        Item item = new Item(id, name, num, price, type, timeStamp);
//                        System.out.println("11111:" + item);
                        return item;
                    }
                })
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Item>() {
                    @Override
                    public long extractAscendingTimestamp(Item item) {
                        return item.getTimeStamp();
                    }
                })
                .keyBy(Item::getItemType)
//                .window(TumblingProcessingTimeWindows.of(Time.days(1), Time.hours(-8)))
                .timeWindow(Time.minutes(1), Time.seconds(5))
//                .timeWindow(Time.seconds(5))
//                .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(1)))
                .aggregate(new MyAggregateFunc(), new MyWindowFunc())
                .keyBy(ItemTypeAndSumPrice::getWindowEnd)
//                .timeWindowAll(Time.seconds(1))
                .process(new MyProcessFunc2(3))
                .setParallelism(1);


        dataStream.print();

        env.execute("Flink-Kafka demo");
    }


    public static class MyAggregateFunc implements AggregateFunction<Item, Tuple2<Long, Long>, Tuple2<Long, Long>>{

        @Override
        public Tuple2<Long, Long> createAccumulator() {
            return new Tuple2<Long, Long>(0L, 0L);
        }

        @Override
        public Tuple2<Long, Long> add(Item item, Tuple2<Long, Long> longLongTuple2) {
            return new Tuple2<Long, Long>(item.getItemNum()+longLongTuple2.f0, item.getItemPrice()*item.getItemNum()+longLongTuple2.f1);
        }

        @Override
        public Tuple2<Long, Long> getResult(Tuple2<Long, Long> longLongTuple2) {
            return longLongTuple2;
        }

        @Override
        public Tuple2<Long, Long> merge(Tuple2<Long, Long> longLongTuple2, Tuple2<Long, Long> acc1) {
            return new Tuple2<Long, Long>(longLongTuple2.f0+acc1.f0, longLongTuple2.f1+acc1.f1);
        }

//        @Override
//        public Long createAccumulator() {
//            return 0L;
//        }
//        @Override
//        public Long add(Item item, Long aLong) {
//            return (item.getItemNum()*item.getItemPrice() + aLong);
//        }
//
//        @Override
//        public Long getResult(Long aLong) {
//            return aLong;
//        }
//
//        @Override
//        public Long merge(Long aLong, Long acc1) {
//            return acc1+aLong;
//        }
    }

    public static class MyWindowFunc implements WindowFunction<Tuple2<Long, Long> , ItemTypeAndSumPrice, String, TimeWindow>{

//        @Override
//        public void apply(String key, TimeWindow timeWindow, Iterable<Long> iterable, Collector<ItemTypeAndSumPrice> collector) throws Exception {
//            Long sumPrice = iterable.iterator().next();
//            collector.collect(new ItemTypeAndSumPrice(key, sumPrice, timeWindow.getEnd()));
//        }

        @Override
        public void apply(String key, TimeWindow timeWindow, Iterable<Tuple2<Long, Long>> iterable, Collector<ItemTypeAndSumPrice> collector) throws Exception {
            Long sumNum = iterable.iterator().next().f0;
            Long sumPrice = iterable.iterator().next().f1;
            collector.collect(new ItemTypeAndSumPrice(key, sumNum, sumPrice, timeWindow.getEnd()));
        }
    }

    public static class MyProcessFunc2 extends KeyedProcessFunction<Long, ItemTypeAndSumPrice, String>{

        private Jedis jedis;
        private MapState<String, ItemTypeAndSumPrice> itemTypeAndSumPriceMapState;
        private ListState<HistoryItem> historyItemListState;
        private Integer topSize;
//        private ArrayList<ItemTypeAndSumPrice> HistoryBuffer;
        private HashMap<Long, ArrayList<ItemTypeAndSumPrice>> HistoryBuffer = new HashMap<>();
        ArrayList<HistoryItem> historyItems = new ArrayList<>();

        public MyProcessFunc2(int topSize){
            this.topSize = topSize;
        }

        //连接redis以及通过运行时上下文得到MapState和ListState
        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            jedis = new Jedis("192.168.37.129", 6379);
            itemTypeAndSumPriceMapState = getRuntimeContext().getMapState(new MapStateDescriptor<String, ItemTypeAndSumPrice>("itemMapState", String.class, ItemTypeAndSumPrice.class));
            historyItemListState = getRuntimeContext().getListState(new ListStateDescriptor<HistoryItem>("historyItem", HistoryItem.class));
//            HistoryBuffer = new ArrayList<>();
        }

        //每来一条数据执行一次此函数内的操作，注意与UserAnalyse的不同，因为此处是集成了KeyedProcessFunction
        //并注册定时器
        @Override
        public void processElement(ItemTypeAndSumPrice itemTypeAndSumPrice, Context context, Collector<String> collector) throws Exception {
            //实现更新MapState里的数据
            //若以存在，则之间更新；若不存在，则新增一个key-value键值对
            ItemTypeAndSumPrice oldItemTypeAndSumPrice = itemTypeAndSumPriceMapState.get(itemTypeAndSumPrice.getItemType());
            if(oldItemTypeAndSumPrice != null){
                oldItemTypeAndSumPrice.setItemSumPrice(oldItemTypeAndSumPrice.getItemSumPrice() + itemTypeAndSumPrice.getItemSumPrice());
                oldItemTypeAndSumPrice.setItemSumNum(oldItemTypeAndSumPrice.getItemSumNum() + itemTypeAndSumPrice.getItemSumNum());
                itemTypeAndSumPriceMapState.put(oldItemTypeAndSumPrice.getItemType(), oldItemTypeAndSumPrice);
            }else{
                itemTypeAndSumPriceMapState.put(itemTypeAndSumPrice.getItemType(), itemTypeAndSumPrice);
            }
//            HistoryBuffer = new ArrayList<>();
//            HistoryBuffer.add(itemTypeAndSumPrice);

            long ts = itemTypeAndSumPrice.getWindowEnd();
//            System.out.println("ts111 ===" + ts);
            //HistoryBuffer主要用来存储以ts时间戳为key，ArrayList<ItemTypeAndSumPrice>为value的数据结构
            //用以动态实现30s内每隔5s的数据折线图实时展示
            ArrayList<ItemTypeAndSumPrice> HistoryList = HistoryBuffer.get(ts);
            if(HistoryList == null){
                ArrayList<ItemTypeAndSumPrice> itemTypeAndSumPriceList = new ArrayList<>();
                itemTypeAndSumPriceList.add(itemTypeAndSumPrice);
                HistoryBuffer.put(ts,itemTypeAndSumPriceList);
            }else{
                HistoryList.add(itemTypeAndSumPrice);
                HistoryBuffer.put(ts, HistoryList);
            }

            context.timerService().registerEventTimeTimer(itemTypeAndSumPrice.getWindowEnd() +1);

        }

        //当满足定时器注册的时间后，即执行下面的操作，用以统计这最近一分钟内的窗口数据
        @Override
        public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> collector) throws Exception {
//            ctx.timerService().
//            System.out.println("HistoryBuffer size :" + HistoryBuffer.size());

//            ArrayList<HistoryItem> historyItems = new ArrayList<>();

            while(historyItemListState.get().iterator().hasNext()){
                historyItems.add(historyItemListState.get().iterator().next());
            }
//            System.out.println("historyItems size ===" + historyItems.size());
            //实现只统计近30s内的数据，因此size＞5之后便去掉首数据 0~5=6 5s*6=30s
            if(historyItems.size() > 5) historyItems.remove(0);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat1.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

//            queue.addLast(HistoryItem(dateFormat.format(new Date(windowEnd)), elementsBuffer.toArray.sortBy(_.tp)))


            ArrayList<ItemTypeAndSumPrice> itemTypeAndSumPriceArrayList = new ArrayList<>();
            Iterable<String> keys = itemTypeAndSumPriceMapState.keys();
            for(String key : keys){
                itemTypeAndSumPriceArrayList.add(itemTypeAndSumPriceMapState.get(key));
            }
//            System.out.println("timestamp =="  + (timestamp-1));
//            System.out.println("HistoryBuffer.get(timestamp) ==" + HistoryBuffer.get(timestamp-1));
            //timestamp-1是因为定时器设置成了timestamp+1，所以这里要-1才能得到数据
            historyItems.add(new HistoryItem(simpleDateFormat1.format(new Date(timestamp)), HistoryBuffer.get(timestamp-1)));
            HistoryBuffer.remove(timestamp-1);
//            HistoryBuffer = null;
//            HistoryBuffer = new ArrayList<>();
//            System.out.println("historyItems size1111111111 ===" + historyItems.size());
//            System.out.println("historyItems ==\n");
            int len = historyItems.size();
            System.out.println("size =" + len);
            for( int i =0; i<len; i++){
                System.out.println("下标i=" + i);
                System.out.println("time ==" + historyItems.get(i).getWindowEnd());
//                System.out.println(historyItems.get(i).getItemTypeAndSumPriceArrayList());
                int ll = historyItems.get(i).getHistoryItemTypeAndSumPriceArrayList().size();
                for(int j=0; j< ll; j++){
                    System.out.println("    下标j=="+ j );
                    System.out.println("    " + historyItems.get(i).getHistoryItemTypeAndSumPriceArrayList().get(j));
                }
            }
            historyItemListState.update(historyItems);
            JSONObject historyJSONObject = new JSONObject();
//            System.out.println("historyJSONObject.put(\"historyItems\", historyItems.toArray()); ==" + historyItems);
            historyJSONObject.put("historyItems", historyItems.toArray());
            String historyString = JSON.toJSONString(historyJSONObject, SerializerFeature.PrettyFormat);
            String historyKey = "HISTORY:ITEM";
            Transaction historyTra = jedis.multi();
            historyTra.set(historyKey, historyString);
            historyTra.publish(historyKey, historyString);
            historyTra.exec();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("itemTypeAndSumPriceArrayList", itemTypeAndSumPriceArrayList.toArray());

            //新增
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            jsonObject.put("windowsEnd", simpleDateFormat.format(new Date(itemTypeAndSumPriceArrayList.get(itemTypeAndSumPriceArrayList.size()-1).getWindowEnd())));

            String s = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
//            System.out.println("s = " + s);
            Transaction multi = jedis.multi();
            String key = "ITEM:TYPE:SUMNUM:SUMPRICE";
//            multi.set(key, s, "NX", "EX", 60*60*2);
//            multi.setex(key, 60*60*2, s);
            multi.set(key, s);
            multi.publish(key, s);
            multi.exec();

            //按照sumNum排序
            ArrayList<ItemTypeAndSumPrice> sortItemSumNum = new ArrayList<>();
//            int listSize = itemTypeAndSumPriceArrayList.size();
//            for(int i=0; i<listSize; i++){
//                sortItemSumNum.add()
//            }
            Collections.sort(itemTypeAndSumPriceArrayList, new Comparator<ItemTypeAndSumPrice>() {
                @Override
                public int compare(ItemTypeAndSumPrice o1, ItemTypeAndSumPrice o2) {
                    Long sumNum1 = o1.getItemSumNum();
                    Long sumNum2 = o2.getItemSumNum();
                    if( sumNum1 < sumNum2 ) return 1;
                    else if ( sumNum1 > sumNum2 ) return -1;
                    else return 0;
                }
            });
//            if(sortItemSumNum.size() > topSize) {
////                List<ItemTypeAndSumPrice> sortItemSumNum = sortItemSumNum1.subList(0, topSize);
//                int l = sortItemSumNum.size();
//                for(int i=0; i<topSize; i++){
//                    sortItemSumNum.
//                }
//            }
//            else{
//                sortItemSumNum = (ArrayList<ItemTypeAndSumPrice>) sortItemSumNum.subList(0, sortItemSumNum.size());
//            }
            int length = itemTypeAndSumPriceArrayList.size();
            int minLength = 0;
//            System.out.println("length : " + length + " , topSize = " + topSize);
            minLength = Math.min(length, topSize);
//            System.out.println("minLength = " + minLength);
            for(int i=0; i<minLength; i++){
                sortItemSumNum.add(itemTypeAndSumPriceArrayList.get(i));
            }
//            System.out.println("sortItemSumNum ===" + sortItemSumNum);
            JSONObject sortSumNum = new JSONObject();
            sortSumNum.put("sortItemSumNum", sortItemSumNum.toArray());
            String sortSumNumString = JSON.toJSONString(sortSumNum, SerializerFeature.PrettyFormat);
//            System.out.println("sortSumNumString = " + sortSumNumString);
            Transaction sortSumNumMulti = jedis.multi();
            String sortSumNumKey = "ITEM:TYPE:SUMNUM:SUMPRICE:SORTSUMNUM";
//            sortSumNumMulti.set(sortSumNumKey, sortSumNumString);
//            sortSumNumMulti.setex(key, 60*60*2, sortSumNumKey);
            sortSumNumMulti.set(key, sortSumNumKey);
            sortSumNumMulti.publish(sortSumNumKey, sortSumNumString);
            sortSumNumMulti.exec();

            //按照sumPrice排序
            ArrayList<ItemTypeAndSumPrice> sortItemSumPrice = new ArrayList<>();
            Collections.sort(itemTypeAndSumPriceArrayList, new Comparator<ItemTypeAndSumPrice>() {
                @Override
                public int compare(ItemTypeAndSumPrice o1, ItemTypeAndSumPrice o2) {
                    Long SumPrice1 = o1.getItemSumPrice();
                    Long SumPrice2 = o2.getItemSumPrice();
                    if( SumPrice1 < SumPrice2 ) return 1;
                    else if ( SumPrice1 > SumPrice2 ) return -1;
                    else return 0;
                }
            });
//            if(sortItemSumPrice.size() > topSize) {
//                sortItemSumPrice = (ArrayList<ItemTypeAndSumPrice>) sortItemSumPrice.subList(0, topSize);
//            }
            for(int i=0; i<minLength; i++){
                sortItemSumPrice.add(itemTypeAndSumPriceArrayList.get(i));
            }
            JSONObject sortSumPrice = new JSONObject();
            sortSumPrice.put("sortItemSumPrice", sortItemSumPrice.toArray());
            String sortSumPriceString = JSON.toJSONString(sortSumPrice, SerializerFeature.PrettyFormat);
//            System.out.println("sortSumPriceString = " + sortSumPriceString);
            Transaction sortSumPriceMulti = jedis.multi();
            String sortSumPriceKey = "ITEM:TYPE:SUMNUM:SUMPRICE:SORTSUMPRICE";
//            sortSumPriceMulti.set(sortSumPriceKey, sortSumPriceString);
//            sortSumPriceMulti.setex(key, 60*60*2, sortSumPriceKey);
            sortSumPriceMulti.set(key, sortSumPriceKey);
            sortSumPriceMulti.publish(sortSumPriceKey, sortSumPriceString);
            sortSumPriceMulti.exec();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(s).append("\n")
                    .append(sortSumNumString).append("\n")
                    .append(sortSumPriceString).append("\n")
                    .append(historyString).append("\n");
//            System.out.println("stringBuilder.toString() : ");
//            System.out.println(stringBuilder.toString());
            collector.collect(stringBuilder.toString());
        }
    }

//    public static class MyProcessFunc extends ProcessAllWindowFunction<ItemTypeAndSumPrice, String, TimeWindow> {
//        private Jedis jedis;
//        private MapState<String, ItemTypeAndSumPrice> itemTypeAndSumPriceMapState;
//        private Integer topSize;
//
//        public MyProcessFunc(int topSize){
//            this.topSize = topSize;
//        }
//
//        @Override
//        public void open(Configuration parameters) throws Exception {
//            jedis = new Jedis("192.168.37.129", 6379);
//            itemTypeAndSumPriceMapState = getRuntimeContext().getMapState(new MapStateDescriptor<String, ItemTypeAndSumPrice>("itemMapState", String.class, ItemTypeAndSumPrice.class));
//        }
//
//        @Override
//        public void process(Context context, Iterable<ItemTypeAndSumPrice> iterable, Collector<String> collector) throws Exception {
//            Iterator<ItemTypeAndSumPrice> iterator = iterable.iterator();
//            ArrayList<ItemTypeAndSumPrice> itemTypeAndSumPriceArrayList = new ArrayList<>();
//            while(iterator.hasNext()){
//                ItemTypeAndSumPrice newItem = iterator.next();
//                String itemType = newItem.getItemType();
//                ItemTypeAndSumPrice itemTypeAndSumPrice = itemTypeAndSumPriceMapState.get(itemType);
//                if(itemTypeAndSumPrice != null){
//                    itemTypeAndSumPrice.setItemSumNum(itemTypeAndSumPrice.getItemSumNum() + newItem.getItemSumNum());
//                    itemTypeAndSumPrice.setItemSumPrice(itemTypeAndSumPrice.getItemSumPrice() + newItem.getItemSumPrice());
//                }else{
//                    itemTypeAndSumPriceMapState.put(itemType, newItem);
//                }
//            }
////            Iterable<String> keys = itemTypeAndSumPriceMapState.keys();
////            for(String key : keys){
////                itemTypeAndSumPriceArrayList.add(itemTypeAndSumPriceMapState.get(key));
////            }
////            JSONObject jsonObject = new JSONObject();
////            jsonObject.put("itemTypeAndSumPriceArrayList", itemTypeAndSumPriceArrayList.toArray());
////            String s = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
////            System.out.println("s = " + s);
////            Transaction multi = jedis.multi();
////            String key = "ITEM:TYPE:SUMNUM:SUMPRICE";
////            multi.set(key, s);
////            multi.publish(key, s);
////            multi.exec();
////
////            //按照sumNum排序
////            ArrayList<ItemTypeAndSumPrice> sortItemSumNum = new ArrayList<>();
////            Collections.sort(itemTypeAndSumPriceArrayList, new Comparator<ItemTypeAndSumPrice>() {
////                @Override
////                public int compare(ItemTypeAndSumPrice o1, ItemTypeAndSumPrice o2) {
////                    Long sumNum1 = o1.getItemSumNum();
////                    Long sumNum2 = o2.getItemSumNum();
////                    if( sumNum1 < sumNum2 ) return 1;
////                    else if ( sumNum1 > sumNum2 ) return -1;
////                    else return 0;
////                }
////            });
////            int length = itemTypeAndSumPriceArrayList.size();
////            topSize = Math.min(length, topSize);
////            for(int i=0; i<topSize; i++){
////                sortItemSumNum.add(itemTypeAndSumPriceArrayList.get(i));
////            }
////            JSONObject sortSumNum = new JSONObject();
////            sortSumNum.put("sortItemSumNum", sortItemSumNum.toArray());
////            String sortSumNumString = JSON.toJSONString(sortSumNum, SerializerFeature.PrettyFormat);
////            System.out.println("sortSumNumString = " + sortSumNumString);
////            Transaction sortSumNumMulti = jedis.multi();
////            String sortSumNumKey = "ITEM:TYPE:SUMNUM:SUMPRICE:SORTSUMNUM";
////            sortSumNumMulti.set(sortSumNumKey, sortSumNumString);
////            sortSumNumMulti.publish(sortSumNumKey, sortSumNumString);
////            sortSumNumMulti.exec();
////
////            //按照sumPrice排序
////            ArrayList<ItemTypeAndSumPrice> sortItemSumPrice = new ArrayList<>();
////            Collections.sort(itemTypeAndSumPriceArrayList, new Comparator<ItemTypeAndSumPrice>() {
////                @Override
////                public int compare(ItemTypeAndSumPrice o1, ItemTypeAndSumPrice o2) {
////                    Long SumPrice1 = o1.getItemSumPrice();
////                    Long SumPrice2 = o2.getItemSumPrice();
////                    if( SumPrice1 < SumPrice2 ) return 1;
////                    else if ( SumPrice1 > SumPrice2 ) return -1;
////                    else return 0;
////                }
////            });
////            for(int i=0; i<topSize; i++){
////                sortItemSumPrice.add(itemTypeAndSumPriceArrayList.get(i));
////            }
////            JSONObject sortSumPrice = new JSONObject();
////            sortSumPrice.put("sortItemSumPrice", sortItemSumPrice.toArray());
////            String sortSumPriceString = JSON.toJSONString(sortSumPrice, SerializerFeature.PrettyFormat);
////            System.out.println("sortSumPriceString = " + sortSumPriceString);
////            Transaction sortSumPriceMulti = jedis.multi();
////            String sortSumPriceKey = "ITEM:TYPE:SUMNUM:SUMPRICE:SORTSUMPRICE";
////            sortSumPriceMulti.set(sortSumPriceKey, sortSumPriceString);
////            sortSumPriceMulti.publish(sortSumPriceKey, sortSumPriceString);
////            sortSumPriceMulti.exec();
////
////            StringBuilder stringBuilder = new StringBuilder();
////            stringBuilder.append(s).append("\n")
////                    .append(sortSumNumString).append("\n")
////                    .append(sortSumPriceString).append("\n");
//////            System.out.println("stringBuilder.toString() : ");
//////            System.out.println(stringBuilder.toString());
////            collector.collect(stringBuilder.toString());
//            Iterable<String> keys = itemTypeAndSumPriceMapState.keys();
//            for(String key : keys){
//                itemTypeAndSumPriceArrayList.add(itemTypeAndSumPriceMapState.get(key));
//            }
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("itemTypeAndSumPriceArrayList", itemTypeAndSumPriceArrayList.toArray());
//
//            //新增
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//            jsonObject.put("windowsEnd", simpleDateFormat.format(new Date(itemTypeAndSumPriceArrayList.get(0).getWindowEnd())));
//
//            String s = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
//            System.out.println("s = " + s);
//            Transaction multi = jedis.multi();
//            String key = "ITEM:TYPE:SUMNUM:SUMPRICE";
////            multi.set(key, s, "NX", "EX", 60*60*2);
//            multi.setex(key, 20/*60*2*/, s);
//            multi.publish(key, s);
//            multi.exec();
//            multi.close();
//
//            //按照sumNum排序
//            ArrayList<ItemTypeAndSumPrice> sortItemSumNum = new ArrayList<>();
////            int listSize = itemTypeAndSumPriceArrayList.size();
////            for(int i=0; i<listSize; i++){
////                sortItemSumNum.add()
////            }
//            Collections.sort(itemTypeAndSumPriceArrayList, new Comparator<ItemTypeAndSumPrice>() {
//                @Override
//                public int compare(ItemTypeAndSumPrice o1, ItemTypeAndSumPrice o2) {
//                    Long sumNum1 = o1.getItemSumNum();
//                    Long sumNum2 = o2.getItemSumNum();
//                    if( sumNum1 < sumNum2 ) return 1;
//                    else if ( sumNum1 > sumNum2 ) return -1;
//                    else return 0;
//                }
//            });
////            if(sortItemSumNum.size() > topSize) {
//////                List<ItemTypeAndSumPrice> sortItemSumNum = sortItemSumNum1.subList(0, topSize);
////                int l = sortItemSumNum.size();
////                for(int i=0; i<topSize; i++){
////                    sortItemSumNum.
////                }
////            }
////            else{
////                sortItemSumNum = (ArrayList<ItemTypeAndSumPrice>) sortItemSumNum.subList(0, sortItemSumNum.size());
////            }
//            int length = itemTypeAndSumPriceArrayList.size();
//            int minLength = 0;
//            System.out.println("length : " + length + " , topSize = " + topSize);
//            minLength = Math.min(length, topSize);
//            System.out.println("minLength = " + minLength);
//            for(int i=0; i<minLength; i++){
//                sortItemSumNum.add(itemTypeAndSumPriceArrayList.get(i));
//            }
//            System.out.println("sortItemSumNum ===" + sortItemSumNum);
//            JSONObject sortSumNum = new JSONObject();
//            sortSumNum.put("sortItemSumNum", sortItemSumNum.toArray());
//            String sortSumNumString = JSON.toJSONString(sortSumNum, SerializerFeature.PrettyFormat);
//            System.out.println("sortSumNumString = " + sortSumNumString);
//            Transaction sortSumNumMulti = jedis.multi();
//            String sortSumNumKey = "ITEM:TYPE:SUMNUM:SUMPRICE:SORTSUMNUM";
////            sortSumNumMulti.set(sortSumNumKey, sortSumNumString);
//            sortSumNumMulti.setex(key, 20/*60*2*/, sortSumNumKey);
//            sortSumNumMulti.publish(sortSumNumKey, sortSumNumString);
//            sortSumNumMulti.exec();
//            sortSumNumMulti.close();
//
//            //按照sumPrice排序
//            ArrayList<ItemTypeAndSumPrice> sortItemSumPrice = new ArrayList<>();
//            Collections.sort(itemTypeAndSumPriceArrayList, new Comparator<ItemTypeAndSumPrice>() {
//                @Override
//                public int compare(ItemTypeAndSumPrice o1, ItemTypeAndSumPrice o2) {
//                    Long SumPrice1 = o1.getItemSumPrice();
//                    Long SumPrice2 = o2.getItemSumPrice();
//                    if( SumPrice1 < SumPrice2 ) return 1;
//                    else if ( SumPrice1 > SumPrice2 ) return -1;
//                    else return 0;
//                }
//            });
////            if(sortItemSumPrice.size() > topSize) {
////                sortItemSumPrice = (ArrayList<ItemTypeAndSumPrice>) sortItemSumPrice.subList(0, topSize);
////            }
//            for(int i=0; i<minLength; i++){
//                sortItemSumPrice.add(itemTypeAndSumPriceArrayList.get(i));
//            }
//            JSONObject sortSumPrice = new JSONObject();
//            sortSumPrice.put("sortItemSumPrice", sortItemSumPrice.toArray());
//            String sortSumPriceString = JSON.toJSONString(sortSumPrice, SerializerFeature.PrettyFormat);
//            System.out.println("sortSumPriceString = " + sortSumPriceString);
//            Transaction sortSumPriceMulti = jedis.multi();
//            String sortSumPriceKey = "ITEM:TYPE:SUMNUM:SUMPRICE:SORTSUMPRICE";
////            sortSumPriceMulti.set(sortSumPriceKey, sortSumPriceString);
//            sortSumPriceMulti.setex(key, 20/*60*2*/, sortSumPriceKey);
//            sortSumPriceMulti.publish(sortSumPriceKey, sortSumPriceString);
//            sortSumPriceMulti.exec();
//            sortSumPriceMulti.close();
//
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(s).append("\n")
//                    .append(sortSumNumString).append("\n")
//                    .append(sortSumPriceString).append("\n");
////            System.out.println("stringBuilder.toString() : ");
////            System.out.println(stringBuilder.toString());
//            collector.collect(stringBuilder.toString());
//
//        }
//    }

}
