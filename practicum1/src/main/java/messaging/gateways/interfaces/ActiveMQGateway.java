package messaging.gateways.interfaces;

import messaging.implementations.ActiveMQMessageProducer;
import messaging.implementations.ActiveMQMessageReceiver;

import java.io.Serializable;

public class ActiveMQGateway <SendObject extends Serializable, ReceiveObject extends Serializable> {
    private ActiveMQMessageReceiver receiver;
    private ActiveMQMessageProducer producer;
    private String queue;

    public ActiveMQGateway(String queue) {
        this.queue = queue;
    }

    public void setReceiver(ActiveMQMessageReceiver receiver) {
        this.receiver = receiver;
    }

    public void setProducer(ActiveMQMessageProducer producer) {
        this.producer = producer;
    }

    public void sendMessage(SendObject message) {
        this.sendMessage(message, null);
    }

    public void sendMessage(SendObject message, String messageId) {
        producer.sendMessage(message, queue, messageId);
    }
}
