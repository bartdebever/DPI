package messaging.serialisers.interfaces;

import javax.jms.Message;
import java.io.Serializable;

public interface ISerialiser<T extends Serializable> {
    T getObject(Object payload, Message message);
}
