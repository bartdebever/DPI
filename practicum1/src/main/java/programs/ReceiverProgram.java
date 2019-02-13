package programs;

import messaging.gateways.interfaces.ActiveMQGateway;
import messaging.helpers.ChannelProtocol;
import messaging.implementations.ActiveMQMessageReceiver;
import messaging.implementations.receivers.ReceiverWithReply;
import messaging.implementations.receivers.SimpleMessageReceiver;
import messaging.models.SimpleMessage;

public class ReceiverProgram {
    private static ActiveMQMessageReceiver receiver;

    public static void main(String[] args) throws Exception {
        ActiveMQGateway<SimpleMessage, SimpleMessage> gateway = new ActiveMQGateway<SimpleMessage, SimpleMessage>(ChannelProtocol.MessageToClient);
        gateway.setReceiver(new ReceiverWithReply(ChannelProtocol.MessageToClient, ChannelProtocol.MessageToServer));
        gateway.runReceiver();
    }
}
