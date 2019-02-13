package messaging.implementations.receivers;

import messaging.implementations.ActiveMQMessageConsumer;

public class SimpleMessageConsumer extends ActiveMQMessageConsumer {
    public SimpleMessageConsumer(String queue) {
        super(queue);
    }
}
