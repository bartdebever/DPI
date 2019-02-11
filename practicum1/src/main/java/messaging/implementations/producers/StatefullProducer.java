package messaging.implementations.producers;

import messaging.helpers.AMQConnectionFactory;
import messaging.implementations.ActiveMQMessageProducer;
import messaging.models.SimpleMessage;
import messaging.tracking.StatefullSession;

import javax.jms.*;

public class StatefullProducer extends ActiveMQMessageProducer {

    private static StatefullSession statefullSession;
    public StatefullProducer(StatefullSession session) {
        statefullSession = session;
    }

    @Override
    public void onMessageSent(ObjectMessage message, Object payload) {
        try {
            SimpleMessage simpleMessage = (SimpleMessage)payload;
            statefullSession.sendMessage(simpleMessage.getSender(), message.getJMSMessageID());
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
