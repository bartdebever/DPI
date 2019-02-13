package messaging.implementations;

import messaging.helpers.AMQConnectionFactory;
import messaging.models.SimpleMessage;

import javax.jms.*;
import java.awt.peer.SystemTrayPeer;
import java.io.IOException;

public abstract class ActiveMQMessageReceiver implements Runnable {

    private String queue;
    private boolean interrupted = false;

    public ActiveMQMessageReceiver(String queue) {
        this.queue = queue;
    }

    public void receiveMessage(SimpleMessage simpleMessage, Message message) {
        try {
            if (message.getJMSCorrelationID() == null) {
                System.out.println(String.format("%s: %s", simpleMessage.getSender(), simpleMessage.getContent()));
                return;
            }

            System.out.println(String.format("%s: %s with correlationId %s", simpleMessage.getSender(), simpleMessage.getContent(), message.getJMSCorrelationID()));
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        interrupted = true;
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
                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    SimpleMessage simpleMessage = (SimpleMessage) objectMessage.getObject();
                    this.receiveMessage(simpleMessage, message);
                }
            }

            consumer.close();

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
