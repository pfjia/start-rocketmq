package order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @author pfjia
 * @since 2018/6/12 8:06
 */
public class OrderedProducer {
    public static void main(String[] args) throws Exception{
        MQProducer producer=new DefaultMQProducer("");
        producer.start();
        String []tags=new String[]{"TagA","TagB","TagC"};
        for (int i=0;i<100;i++){
            int orderId=i%10;
            Message message=new Message("TopicTest",
                    tags[i%tags.length],
                    "KEY"+i,
                    ("Hello RocketMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                    );
            SendResult result=producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    int id= (int) arg;
                    int index=id%mqs.size();
                    return mqs.get(index);
                }
            },orderId);
        }
        producer.shutdown();
    }
}
