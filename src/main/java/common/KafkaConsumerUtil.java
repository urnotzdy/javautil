package common;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Description 通用消费者
 * @Author zhangdanyang02
 * @Date 2019/8/13 16:16
 **/
public class KafkaConsumerUtil {

    public static final String BOOTSTRAP_SERVERS =
            "10.135.9.4:9092,10.135.9.5:9092,10.135.9.6:9092,10.135.9.7:9092,10.135.9.8:9092";
    private static KafkaConsumer consumer = null;
    private static String topicName = "";

    public static void init(String topic, String clientId, String groupId) throws Exception {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", BOOTSTRAP_SERVERS);// required
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");// required
        properties.put("client.id", clientId);
        properties.put("group.id", groupId);
        properties.put("max.poll.interval.ms", "5000");
        properties.put("max.poll.records", "20");
        properties.put("enable.auto.commit", "true");
        properties.put("fetch.max.bytes", "20971520");
        consumer = new KafkaConsumer(properties);
        consumer.subscribe(Arrays.asList(topic));
        topicName=topic;
    }

    public static void consumer() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100); // 拉取模式 超时500ms
                if (records != null) {
                    int count = records.count();
                    System.out.println("count----" + count);
                    if (count == 0) {
                        Thread.sleep(10); // 暂无记录 休眠10s
                    } else {
                        for (final ConsumerRecord<String, String> record : records) {
                            String val = record.value();
                            System.out.println("val:" + val);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            consumer.close();
        }
    }

    //清除kafka数据
    public static void main(String[] args) {
        String topic = "hdp_ubu_xxzl_devicefp_sandbox";
        String clientId = "hdp_ubu_xxzl-hdp_ubu_xxzl_devicefp_sandbox-qYqIe";
        String groupId = "test";
        try {
            init(topic, clientId, groupId);
            consumer();
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

