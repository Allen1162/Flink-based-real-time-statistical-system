package scau.Allen_HJL.Test3;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlinkNetcet {

        // 创建Logger对象
        private static final Logger log = LoggerFactory.getLogger(FlinkNetcet.class);

        public static void main(String[] args) throws Exception {
            log.info("1111111111");
            //获取执行环境
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


            //界面上指定参数(本地测试可以忽略，设置了默认值)
            final ParameterTool params = ParameterTool.fromArgs(args);
            String hostName = params.get("hostname", "localhost");
            int port = params.getInt("port", 9000);

            //数据来源(获取sourceStream)
            DataStream<String> sourceStream = env.socketTextStream(hostName, port, "\n");

            //数据去向(打印到控制台)
            sourceStream.print();

            //执行
            env.execute("SocketStreamTest");
        }


}
