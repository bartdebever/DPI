package programs;

import messaging.helpers.ChannelProtocol;
import messaging.implementations.ActiveMQMessageReceiver;
import messaging.implementations.receivers.ReceiverWithReply;
import messaging.implementations.receivers.SimpleMessageReceiver;

public class ReceiverProgram {
    private static ActiveMQMessageReceiver receiver;

    public static void main(String[] args) {
        receiver = new ReceiverWithReply(ChannelProtocol.MessageToClient, ChannelProtocol.MessageToServer);
        receiver.run();
    }
}
