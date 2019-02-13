package messaging.serialisers;

import messaging.serialisers.interfaces.ISerialiser;

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
}
