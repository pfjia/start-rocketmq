package schedule;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author pfjia
 * @since 2018/6/12 8:27
 */
public class ScheduleMessageProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer=new DefaultMQProducer("");
        producer.start();
        int totalMessagesToSend=100;
        for (int i=0;i<totalMessagesToSend;i++){
            Message message=new Message("TopicTest",
                    ("Hello scheduled message "+i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                    );
            message.setDelayTimeLevel(3);
            producer.send(message);
        }
        producer.shutdown();
    }
}
