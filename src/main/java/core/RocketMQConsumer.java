package core;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

import java.util.UUID;

/**
 * @author pfjia
 * @since 2018/6/11 16:06
 */
public class RocketMQConsumer {
    private DefaultMQPushConsumer consumer;
    private MessageListener listener;
    protected String nameServer;
    protected String groupName;
    protected String topic;


    public RocketMQConsumer(MessageListener listener, String nameServer, String groupName, String topic) {
        this.listener = listener;
        this.nameServer = nameServer;
        this.groupName = groupName;
        this.topic = topic;
    }

    public void init() {
        consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameServer);
        try {
            consumer.subscribe(topic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.setInstanceName(UUID.randomUUID().toString());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.registerMessageListener((MessageListenerConcurrently) listener);
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        System.out.println("RocketMQConsumer Started! group=" + consumer.getConsumerGroup() + ",instance=" + consumer.getInstanceName());
    }
}
