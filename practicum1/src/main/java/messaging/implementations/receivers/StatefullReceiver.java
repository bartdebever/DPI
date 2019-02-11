package messaging.implementations.receivers;

import messaging.implementations.ActiveMQMessageReceiver;
import messaging.models.SimpleMessage;
import messaging.tracking.StatefullSession;

import javax.jms.JMSException;
import javax.jms.Message;

public class StatefullReceiver extends ActiveMQMessageReceiver {
    private StatefullSession session;

    public StatefullReceiver(String queue, StatefullSession session) {
        super(queue);
        this.session = session;
    }

    @Override
    public void receiveMessage(SimpleMessage simpleMessage, Message message) {
        try {
            if (message.getJMSCorrelationID() == null) {
                super.receiveMessage(simpleMessage, message);
                return;
            }

            String replyTo = session.getSenderByMessage(message.getJMSCorrelationID());
            System.out.println(String.format("From: %s To: %s Content: %s", simpleMessage.getSender(), replyTo, simpleMessage.getContent()));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}