package messaging.implementations.receivers;

import messaging.implementations.ActiveMQMessageProducer;
import messaging.implementations.ActiveMQMessageReceiver;
import messaging.models.SimpleMessage;

public class ReceiverWithReply extends ActiveMQMessageReceiver {

    private String replyQueue;
    public ReceiverWithReply(String receiveQueue, String replyQueue) {
        super(receiveQueue);
        this.replyQueue = replyQueue;
    }

    @Override
    public void receiveMessage(SimpleMessage message) {
        ActiveMQMessageProducer.sendReply(new SimpleMessage("Client", "Accepted"), replyQueue);
        super.receiveMessage(message);
    }
}
