package messaging.implementations.receivers;

import messaging.implementations.ActiveMQMessageProducer;
import messaging.implementations.ActiveMQMessageReceiver;
import messaging.models.SimpleMessage;

import javax.jms.JMSException;
import javax.jms.Message;

public class ReceiverWithReply extends ActiveMQMessageReceiver {

    private String replyQueue;
    public ReceiverWithReply(String receiveQueue, String replyQueue) {
        super(receiveQueue);
        this.replyQueue = replyQueue;
    }

    @Override
    public void receiveMessage(SimpleMessage simpleMessage, Message message) {
        try {
            ActiveMQMessageProducer.sendReply(new SimpleMessage("Client", "Accepted"), replyQueue, message.getJMSMessageID());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        super.receiveMessage(simpleMessage, message);
    }
}
