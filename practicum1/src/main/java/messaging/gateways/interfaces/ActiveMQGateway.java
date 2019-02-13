package messaging.gateways.interfaces;

import messaging.implementations.ActiveMQMessageProducer;
import messaging.implementations.ActiveMQMessageConsumer;
import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.listeners.interfaces.IOnMessageSendListener;
import messaging.serialisers.interfaces.ISerializer;

import java.io.Serializable;
import java.util.List;

public class ActiveMQGateway <SendObject extends Serializable, ReceiveObject extends Serializable> {
    private ISerializer<ReceiveObject> serialiser;
    private List<IMessageReceivedListener> listeners;
    private ActiveMQMessageConsumer receiver;
    private ActiveMQMessageProducer producer;
    private String queue;
    private Thread receiverThread;

    public ActiveMQGateway(String queue) {
        this.queue = queue;
    }

    public void setReceiver(ActiveMQMessageConsumer receiver) {
        this.receiver = receiver;
    }

    public void setProducer(ActiveMQMessageProducer producer) {
        this.producer = producer;
    }

    public void setSerialiser(ISerializer<ReceiveObject> serialiser) {
        this.receiver.setSerialiser(serialiser);
        this.producer.setSerialiser(serialiser);
    }

    public void sendMessage(SendObject message) {
        this.sendMessage(message, null);
    }

    public void sendMessage(SendObject message, String messageId) {
        this.producer.sendMessage(message, queue, messageId);
    }

    public void runReceiver() throws Exception {
        if (receiverThread != null) {
            throw new Exception("Tried to start Receiver thread while running.");
        }

        receiverThread = new Thread(receiver);
        receiverThread.start();
    }

    public void stopReceiver() {
        receiverThread.interrupt();
        receiver.stop();
    }

    public void addMessageListener(IMessageReceivedListener listener) {
        this.receiver.addListener(listener);
    }

    public void removeMessageListener(IMessageReceivedListener listener) {
        this.receiver.removeListener(listener);
    }

    public void addMessageSentListener(IOnMessageSendListener listener) {this.producer.addListener(listener);}
}
