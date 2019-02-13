package messaging.implementations.receivers;

import messaging.implementations.ActiveMQMessageProducer;
import messaging.implementations.ActiveMQMessageReceiver;
import messaging.implementations.producers.SimpleProducer;
import messaging.models.SimpleMessage;

import javax.jms.JMSException;
import javax.jms.Message;

public class ReceiverWithReply extends ActiveMQMessageReceiver {

    private String replyQueue;
    private ActiveMQMessageProducer producer;
    public ReceiverWithReply(String receiveQueue, String replyQueue) {
        super(receiveQueue);
        this.replyQueue = replyQueue;
        producer = new SimpleProducer();
    }

//    @Override
//    public void receiveMessage(SimpleMessage simpleMessage, Message message) {
//        try {
//            producer.sendMessage(new SimpleMessage("Client", "Accepted"), replyQueue, message.getJMSMessageID());
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//        super.receiveMessage(simpleMessage, message);
//    }
}
