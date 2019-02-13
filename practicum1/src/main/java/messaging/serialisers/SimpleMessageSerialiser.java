package messaging.serialisers;

import messaging.models.SimpleMessage;
import messaging.serialisers.interfaces.ISerialiser;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.Serializable;

public class SimpleMessageSerialiser implements ISerialiser {

    public Serializable getObject(Object payload, Message message) {
        SimpleMessage simpleMessage = (SimpleMessage)payload;

        try {
            simpleMessage.setMessageId(message.getJMSMessageID());
            simpleMessage.setCorrelationId(message.getJMSCorrelationID());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return simpleMessage;
    }
}
