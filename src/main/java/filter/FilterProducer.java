package filter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author pfjia
 * @since 2018/6/12 8:39
 */
public class FilterProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer=new DefaultMQProducer("");
        producer.start();
        Message message=new Message("TopicTest",("Hello RocketMQ ").getBytes(RemotingHelper.DEFAULT_CHARSET));
        message.putUserProperty("a", String.valueOf(1));
        SendResult result=producer.send(message);
        producer.shutdown();
    }
}
