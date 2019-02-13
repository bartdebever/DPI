package messaging.implementations;

import messaging.helpers.AMQConnectionFactory;
import messaging.listeners.interfaces.IOnMessageSendListener;
import messaging.serialisers.interfaces.ISerialiser;

import javax.jms.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ActiveMQMessageProducer {

    private List<IOnMessageSendListener> onMessageSendListeners;
    private ISerialiser serialiser;

    public ActiveMQMessageProducer() {
        this.onMessageSendListeners = new ArrayList<IOnMessageSendListener>();
    }

    public void setSerialiser(ISerialiser serialiser) {
        this.serialiser = serialiser;
    }

    public void addListener(IOnMessageSendListener listener) {
        onMessageSendListeners.add(listener);
    }

    public <T extends Serializable> void sendMessage(T payload, String queue) {
        sendMessage(payload, queue, null);
    }

    public <T extends Serializable> void sendMessage(T reply, String queue, String messageId) {
        try {
            Connection connection = AMQConnectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(queue);

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            ObjectMessage message = session.createObjectMessage();
            if (messageId != null) {
                message.setJMSCorrelationID(messageId);
            }

            message.setObject(reply);
            message = beforeMessageSent(message, reply);
            producer.send(message);

            for(IOnMessageSendListener listener : onMessageSendListeners) {
                listener.onMessageSent(this.serialiser.getObject(reply, message));
            }

            onMessageSent(message, reply);

            producer.close();
            session.close();

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public void onMessageSent(ObjectMessage message, Object payload) {

    }

    public ObjectMessage beforeMessageSent(ObjectMessage message, Object payload) {
        return message;
    }
}

