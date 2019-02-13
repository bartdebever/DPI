package messaging.serialisers.interfaces;

import java.io.Serializable;

public interface ISerialiser<T extends Serializable> {
    T getObject(Object payload);
}
