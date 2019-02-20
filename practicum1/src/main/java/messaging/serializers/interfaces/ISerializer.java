package messaging.serializers.interfaces;

import javax.jms.Message;
import java.io.Serializable;

public interface ISerializer<T extends Serializable> {
    T getObject(Object payload, Message message);

    T getObject(String json);
}
