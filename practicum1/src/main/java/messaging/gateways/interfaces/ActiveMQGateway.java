package messaging.gateways.interfaces;

import messaging.implementations.ActiveMQMessageProducer;
import messaging.implementations.ActiveMQMessageReceiver;
import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.serialisers.interfaces.ISerialiser;

import java.io.Serializable;
import java.util.List;

public class ActiveMQGateway <SendObject extends Serializable, ReceiveObject extends Serializable> {
    private ISerialiser<ReceiveObject> serialiser;
    private List<IMessageReceivedListener> listeners;
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

    public void setSerialiser(ISerialiser<ReceiveObject> serialiser) {
        this.serialiser = serialiser;
    }

    public void sendMessage(SendObject message) {
        this.sendMessage(message, null);
    }

    public void sendMessage(SendObject message, String messageId) {
        this.producer.sendMessage(message, queue, messageId);
    }

    public void runReceiver() {
        receiver.run();
    }

    public void stopReceiver() {
        receiver.stop();
    }

    public void addMessageListener(IMessageReceivedListener listener) {
        this.listeners.add(listener);
    }

    public void removeMessageListener(IMessageReceivedListener listener) {
        this.listeners.remove(listener);
    }
}
