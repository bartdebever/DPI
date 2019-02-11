package messaging.implementations;

import messaging.helpers.AMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;


public class ActiveMQMessageProducer {

    private static int idCounter;
    public static <T extends Serializable> void sendMessage(T reply, String queue) {
        try {
            Connection connection = AMQConnectionFactory.createConnection();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(queue);

            // Create a MessageProducer from the Session to the Topic or Queue
            javax.jms.MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(reply);

            producer.send(objectMessage);

            // Clean up
            producer.close();
            session.close();
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public static <T extends Serializable> void sendReply(T reply, String queue, String messageId) {
        try {
            Connection connection = AMQConnectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(queue);

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            ObjectMessage message = session.createObjectMessage();
            message.setJMSCorrelationID(messageId);
            message.setObject(reply);

            producer.send(message);

            producer.close();
            session.close();

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }

    }
}

