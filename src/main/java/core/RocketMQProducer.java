package core;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.UUID;

/**
 * @author pfjia
 * @since 2018/6/11 16:10
 */
public class RocketMQProducer {
    private DefaultMQProducer producer;
    protected String nameServer;
    protected String groupName;
    protected String topic;


    public RocketMQProducer(String nameServer, String groupName, String topic) {
        this.nameServer = nameServer;
        this.groupName = groupName;
        this.topic = topic;
    }

    public void init(){
        producer=new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(nameServer);
        producer.setInstanceName(UUID.randomUUID().toString());
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void send(Message message){
        message.setTopic(topic);
        try {
            SendResult result=producer.send(message);
            SendStatus status=result.getSendStatus();
            System.out.println("messageId="+result.getMsgId()+",status="+status);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
    }


}
