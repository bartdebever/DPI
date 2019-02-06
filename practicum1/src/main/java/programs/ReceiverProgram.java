package programs;

import messaging.models.SimpleMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.awt.*;

public class ReceiverProgram {
    public static void main(String[] args) {
        try {

            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connectionFactory.setTrustAllPackages(true);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("Test.Test");

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message

            while (true) {
                Message message = consumer.receive();
                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    final SimpleMessage simpleMessage = (SimpleMessage) objectMessage.getObject();

                            if (simpleMessage.getContent().equals("quit")) {
                               break;
                            }
                            System.out.println(String.format("%s: %s", simpleMessage.getSender(), simpleMessage.getContent()));
                        }
                }

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
