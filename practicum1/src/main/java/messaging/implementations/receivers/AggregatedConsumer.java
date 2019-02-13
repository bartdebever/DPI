package messaging.implementations.receivers;

import messaging.implementations.ActiveMQMessageReceiver;

import javax.jms.Message;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregatedConsumer extends ActiveMQMessageReceiver {

    private Map<Integer, List<Message>> correlatedMessages;

    public AggregatedConsumer(String queue) {
        super(queue);
        correlatedMessages = new HashMap<Integer, List<Message>>();
    }
}
