package common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Description 发送数据
 * @Author zhangdanyang02
 * @Date 2019/7/17 14:02
 **/
public class KafkaProductUtil {

    public static final String BOOTSTRAP_SERVERS =
            "10.135.9.4:9092,10.135.9.5:9092,10.135.9.6:9092,10.135.9.7:9092,10.135.9.8:9092";
    private static KafkaProducer producer = null;
    private static String topicName = "";

    public static void init(String topic, String clientId) throws Exception {
        topicName = topic;
        Properties properties = new Properties();
        properties.put("bootstrap.servers", BOOTSTRAP_SERVERS);//required
        properties.put("client.id", clientId);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");//required
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");//required
        properties.put("batch.size", "16384");//default 16384 | 16K
        properties.put("acks", "all"); //default 1
        properties.put("buffer.memory", "33554432");// 32M
        properties.put("metadata.max.age.ms", "300000");// default 300000 | 5min
        properties.put("max.block.ms", "60000");// default 60000
        properties.put("request.timeout.ms", "30000");// default 30000
        producer = new KafkaProducer(properties);
    }

    public static void send(String data) {
        ProducerRecord producerRecord = new ProducerRecord(topicName, data);
        producer.send(producerRecord);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String topic = "hdp_ubu_xxzl_devicefp_sandbox";
        String clientId = "hdp_ubu_xxzl-hdp_ubu_xxzl_devicefp_sandbox-qYqIe";

//        String taskId = "38630250136450";
//        String batchNo = "12123";
        JSONObject logObj = new JSONObject();
        String info = "{\"busine\":\"fangchan\",\"catePath\":\"1,16\",\"infoid\":\"38630250136450\",\"@version\":\"1\",\"@timestamp\":\"2019-07-22T11:30:39.813Z\"}";
        logObj = JSON.parseObject(info);
//        logObj.put("taskId", taskId);
//        logObj.put("batchNo", batchNo);
//        logObj.put("addtime", System.currentTimeMillis());
//        try{
//            int a=1/0;
//        }catch (Exception e){
//            logObj.put("log", LogUtil.getErrorMsg(e));
//        }
//        String log = String.format("taskId = %s , batchNo = %s excute start . ", taskId, batchNo);

        try {
            init(topic, clientId);
            int i=0;
            while (true) {
                send(JSON.toJSONString(logObj));
                i++;
                if(i==100){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
