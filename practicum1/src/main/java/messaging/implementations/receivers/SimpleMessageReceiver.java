package messaging.implementations.receivers;

import messaging.implementations.ActiveMQMessageReceiver;

public class SimpleMessageReceiver extends ActiveMQMessageReceiver {
    public SimpleMessageReceiver(String queue) {
        super(queue);
    }
}
