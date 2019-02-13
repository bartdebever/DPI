package messaging.serialisers;

import com.google.gson.Gson;
import messaging.models.SimpleMessage;
import messaging.serialisers.interfaces.ISerialiser;

import javax.jms.JMSException;
import javax.jms.Message;

public class SimpleMessageSerialiser implements ISerialiser<SimpleMessage> {

    public SimpleMessage getObject(Object payload, Message message) {
        SimpleMessage simpleMessage = (SimpleMessage)payload;

        try {
            simpleMessage.setMessageId(message.getJMSMessageID());
            simpleMessage.setCorrelationId(message.getJMSCorrelationID());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return simpleMessage;
    }

    public SimpleMessage getObject(String json) {
        return new Gson().fromJson(json, SimpleMessage.class);
    }
}
