package messaging.implementations;

import messaging.helpers.AMQConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;


public class ActiveMQMessageProducer {
    public static <T extends Serializable> void sendReply(T reply, String queue) {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Trusts all packages so the messages can be received.
            // This ideally should be done on a package basis but this is not well maintainable imo.
            // For testing purposes and ease of use, I'll do it like this.
            //connectionFactory.setTrustAllPackages(true);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();

            connection.start();
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
}

