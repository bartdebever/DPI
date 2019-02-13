package messaging.implementations;

import com.google.gson.Gson;
import messaging.helpers.AMQConnectionFactory;
import messaging.listeners.interfaces.IOnMessageSendListener;
import messaging.serialisers.interfaces.ISerializer;

import javax.jms.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ActiveMQMessageProducer {

    private List<IOnMessageSendListener> onMessageSendListeners;
    private ISerializer serialiser;

    public ActiveMQMessageProducer() {
        this.onMessageSendListeners = new ArrayList<IOnMessageSendListener>();
    }

    public void setSerialiser(ISerializer serialiser) {
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

            TextMessage message = session.createTextMessage();

            String data = new Gson().toJson(reply);

            message.setText(data);

            if (messageId != null) {
                message.setJMSCorrelationID(messageId);
            }

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

    public void onMessageSent(Message message, Object payload) {

    }

    public TextMessage beforeMessageSent(TextMessage message, Object payload) {
        return message;
    }
}

