package messaging.serializers;

import com.google.gson.Gson;
import messaging.models.AggregatedMessage;
import messaging.models.SimpleMessage;
import messaging.serializers.interfaces.ISerializer;

import javax.jms.JMSException;
import javax.jms.Message;

public class AggregationMessageSerializer implements ISerializer<AggregatedMessage> {
    public AggregatedMessage getObject(Object payload, Message message) {
        AggregatedMessage simpleMessage = (AggregatedMessage)payload;

        try {
            simpleMessage.setMessageId(message.getJMSMessageID());
            simpleMessage.setCorrelationId(message.getJMSCorrelationID());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return simpleMessage;
    }

    public AggregatedMessage getObject(String json) {
        return new Gson().fromJson(json, AggregatedMessage.class);
    }
}
