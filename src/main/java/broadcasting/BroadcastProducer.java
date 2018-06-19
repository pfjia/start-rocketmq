package broadcasting;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author pfjia
 * @since 2018/6/12 8:18
 */
public class BroadcastProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("");
        producer.start();
        for (int i=0;i<100;i++){
            Message message=new Message("TopicTest",
                    "TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET)
                    );
            SendResult result=producer.send(message);
            System.out.printf("%s%n",result);
        }
        producer.shutdown();
    }
}
