package programs;

import messaging.implementations.ActiveMQMessageReceiver;
import messaging.implementations.receivers.SimpleMessageReceiver;

public class ReceiverProgram {
    private static ActiveMQMessageReceiver receiver;

    public static void main(String[] args) {
        receiver = new SimpleMessageReceiver("Test.Test");
        Thread thread = new Thread(receiver);
        thread.run();
    }
}
