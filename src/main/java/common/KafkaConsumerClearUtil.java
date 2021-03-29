package common;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * @Description 清除堆积的kafka数据
 * @Author zhangdanyang02
 * @Date 2019/7/17 16:13
 **/
public class KafkaConsumerClearUtil {

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
//        consumer.subscribe(Arrays.asList(topic));
        consumer.subscribe(Collections.singletonList(topic), new AlwaysSeekToEndListener<String, String>(consumer));
        topicName=topic;
    }

    public static void consumer() {
        try {
            ConsumerRecords<String, String> records = consumer.poll(100); // 拉取模式 超时500ms
//            consumer.seekToEnd(consumer.assignment());
            consumer.commitAsync();
            while (true) {
                records = consumer.poll(100); // 拉取模式 超时500ms
                if (records != null) {
                    int count = records.count();
                    System.out.println("----" + count);
                    if (count == 0) {
                        Thread.sleep(10); // 暂无记录 休眠2s
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
        String groupId = "hdp_ubu_xxzl_kingy@394";
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

    public static class AlwaysSeekToEndListener<K, V> implements ConsumerRebalanceListener {

        private Consumer<K, V> consumer;

        public AlwaysSeekToEndListener(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            consumer.seekToEnd(partitions);
        }
    }

}
