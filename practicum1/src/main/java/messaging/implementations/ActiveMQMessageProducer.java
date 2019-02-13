package messaging.implementations;

import messaging.helpers.AMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;

public abstract class ActiveMQMessageProducer {

    public <T extends Serializable> void sendMessage(T payload, String queue) {
        sendReply(payload, queue, null);
    }

    public <T extends Serializable> void sendReply(T reply, String queue, String messageId) {
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

