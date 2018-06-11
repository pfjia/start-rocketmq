package core;

import org.apache.rocketmq.common.message.Message;

/**
 * @author pfjia
 * @since 2018/6/11 16:15
 */
public class RocketMQProducerTest {
    public static void main(String[] args) {
        String mqNameServer = Const.NAME_SERVER;
        String topic = Const.TOPIC;
        String producerMqGroupName = "PRODUCER-MQ-GROUP";
        RocketMQProducer mqProducer = new RocketMQProducer(mqNameServer, producerMqGroupName, topic);

        mqProducer.init();
        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            message.setBody(("I send message to RocketMQ " + i).getBytes());
            mqProducer.send(message);
        }
        System.out.println("结束");
    }
}
