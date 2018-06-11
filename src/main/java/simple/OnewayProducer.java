package simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author pfjia
 * @since 2018/6/12 0:03
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer=new DefaultMQProducer("");
        producer.start();

        for (int i=0;i<100;i++){
            Message message=new Message("TopicTest",
                    "TagA",
                    ("Hello RocketMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            producer.sendOneway(message);
        }
        producer.shutdown();
    }
}
