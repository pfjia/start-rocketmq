package simple;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author pfjia
 * @since 2018/6/11 23:53
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message("TopicTest",
                    "TagA",
                    ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            SendResult result = producer.send(message);
            System.out.printf("%s%n", result);
        }
        producer.shutdown();
    }
}
