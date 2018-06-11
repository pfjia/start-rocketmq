package core;

import java.util.concurrent.TimeUnit;

/**
 * @author pfjia
 * @since 2018/6/11 16:13
 */
public class RocketMQConsumerTest {
    public static void main(String[] args) {
        String mqNameServer = Const.NAME_SERVER;
        String topics = Const.TOPIC;
        String consumerMqGroupName = "CONSUMER-MQ-GROUP";
        RocketMQListener mqListener = new RocketMQListener();
        RocketMQConsumer mqConsumer = new RocketMQConsumer(mqListener, mqNameServer, consumerMqGroupName, topics);
        mqConsumer.init();
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
