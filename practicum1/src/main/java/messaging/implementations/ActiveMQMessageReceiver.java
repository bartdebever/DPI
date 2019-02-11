package messaging.implementations;

import messaging.helpers.AMQConnectionFactory;
import messaging.models.SimpleMessage;

import javax.jms.*;

public abstract class ActiveMQMessageReceiver implements Runnable {

    private String queue;
    private boolean interrupted;

    public ActiveMQMessageReceiver(String queue) {
        this.queue = queue;
    }

    public void receiveMessage(SimpleMessage message) {
        System.out.println(String.format("%s: %s", message.getSender(), message.getContent()));
    }

    public void stopReceiving() {
        interrupted = true;
    }

    public void run() {
        try {
            // Create a ConnectionFactory
            Connection connection = AMQConnectionFactory.createConnection(true);

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(queue);

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            while (!interrupted) {
                Message message = consumer.receive();
                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    SimpleMessage simpleMessage = (SimpleMessage) objectMessage.getObject();
                    this.receiveMessage(simpleMessage);
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
