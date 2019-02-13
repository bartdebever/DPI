package programs;

import messaging.gateways.interfaces.ActiveMQGateway;
import messaging.helpers.ChannelProtocol;
import messaging.implementations.producers.SimpleProducer;
import messaging.implementations.receivers.SimpleMessageConsumer;
import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.models.SimpleMessage;
import messaging.serialisers.SimpleMessageSerialiser;

import java.io.Serializable;

public class ReceiverProgram implements IMessageReceivedListener {
    private static ActiveMQGateway gateway;
    public static void main(String[] args) throws Exception {
        gateway = new ActiveMQGateway<SimpleMessage, SimpleMessage>(ChannelProtocol.MessageToServer);
        gateway.setProducer(new SimpleProducer());
        gateway.setReceiver(new SimpleMessageConsumer(ChannelProtocol.MessageToClient));
        gateway.setSerialiser(new SimpleMessageSerialiser());
        gateway.addMessageListener(new ReceiverProgram());
        gateway.runReceiver();
    }

    public void onMessageReceived(Serializable message) {
        SimpleMessage simpleMessage = (SimpleMessage)message;
        System.out.println(String.format("%s: %s", simpleMessage.getSender(), simpleMessage.getContent()));
        gateway.sendMessage(new SimpleMessage("Client", "Received"), simpleMessage.getMessageId());
    }
}
