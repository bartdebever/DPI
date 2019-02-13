package messaging.serialisers;

import messaging.serialisers.interfaces.ISerialiser;

import javax.jms.Message;
import java.io.Serializable;

public class CastSerialiser <T extends Serializable>
        implements ISerialiser
{

    public T getObject(Object payload) {
        try {
            return (T)payload;
        }
        catch(Exception _) {
            return null;
        }
    }

    public Serializable getObject(Object payload, Message message) {
        return null;
    }
}
