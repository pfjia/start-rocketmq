package order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author pfjia
 * @since 2018/6/12 8:11
 */
public class OrderedConsumer {
    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTest","TagA || TagC");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            AtomicLong consumeTimes=new AtomicLong(0);
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(false);
                System.out.printf(Thread.currentThread().getName()+" Receive New Message: "+msgs+"%n");
                consumeTimes.incrementAndGet();
                if (consumeTimes.get()%2==0){
                    return ConsumeOrderlyStatus.SUCCESS;
                }else if (consumeTimes.get()%3==0){
                    return ConsumeOrderlyStatus.ROLLBACK;
                }else if (consumeTimes.get()%4==0){
                    return ConsumeOrderlyStatus.COMMIT;
                }else if (consumeTimes.get()%5==0){
                    context.setSuspendCurrentQueueTimeMillis(3000);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("Consumer Stated.%n");
    }
}
