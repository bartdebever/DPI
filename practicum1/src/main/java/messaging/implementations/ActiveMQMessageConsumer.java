package messaging.implementations;

import messaging.helpers.AMQConnectionFactory;
import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.serialisers.interfaces.ISerialiser;

import javax.jms.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ActiveMQMessageConsumer implements Runnable {

    private String queue;
    private boolean interrupted = false;
    private List<IMessageReceivedListener> listeners;
    private ISerialiser serialiser;

    public ActiveMQMessageConsumer(String queue) {
        this.queue = queue;
        this.listeners = new ArrayList<IMessageReceivedListener>();
    }

    public void addListener(IMessageReceivedListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(IMessageReceivedListener listener) {
        this.listeners.remove(listener);
    }

    public void setSerialiser(ISerialiser serialiser) {
        this.serialiser = serialiser;
    }

    public void stop() {
        interrupted = true;
    }

    public void receiveMessage(Serializable payload, Message message) {
        payload = serialiser.getObject(payload, message);
        for (IMessageReceivedListener listener : listeners) {
            listener.onMessageReceived(payload);
        }
    }

    public void run() {
        try {
            // Create a ConnectionFactory
            Session session = AMQConnectionFactory.createsSession();

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(queue);

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            while (!interrupted) {
                Message message = consumer.receive();
                if (message instanceof TextMessage) {
                    TextMessage objectMessage = (TextMessage) message;
                    receiveMessage(serialiser.getObject(objectMessage.getText()), objectMessage);
                }
            }

            consumer.close();

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
