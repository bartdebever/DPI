package programs;

import messaging.gateways.interfaces.ActiveMQGateway;
import messaging.helpers.ChannelProtocol;
import messaging.implementations.producers.SimpleProducer;
import messaging.implementations.receivers.SimpleMessageConsumer;
import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.listeners.received.AggregationMessageReceivedListener;
import messaging.listeners.received.AggregationReplyListener;
import messaging.models.AggregatedMessage;
import messaging.models.SimpleMessage;
import messaging.serializers.AggregationMessageSerializer;
import messaging.serializers.SimpleMessageSerializer;

import java.io.Serializable;

public class ReceiverProgram implements IMessageReceivedListener {
    private static ActiveMQGateway gateway;
    public static void main(String[] args) throws Exception {
        aggregationConsumer(ChannelProtocol.Aggregation1, "Test 1", "Bart 1");
        aggregationConsumer(ChannelProtocol.Aggregation2, "Test 2", "Bart 2");
    }

    public void onMessageReceived(Serializable message) {
        SimpleMessage simpleMessage = (SimpleMessage)message;
        System.out.println(String.format("%s: %s", simpleMessage.getSender(), simpleMessage.getContent()));
        gateway.sendMessage(new SimpleMessage("Client", "Received"), simpleMessage.getMessageId());
    }

    private static void correlationConsumer() throws Exception {
        gateway = new ActiveMQGateway<SimpleMessage, SimpleMessage>(ChannelProtocol.MessageToServer);
        gateway.setProducer(new SimpleProducer());
        gateway.setReceiver(new SimpleMessageConsumer(ChannelProtocol.MessageToClient));
        gateway.setSerialiser(new SimpleMessageSerializer());
        gateway.addMessageListener(new ReceiverProgram());
        gateway.runReceiver();
    }

    private static void aggregationConsumer(String queue, String replyMessage, String sender) throws Exception {
        ActiveMQGateway gateway2 = new ActiveMQGateway<AggregatedMessage, AggregatedMessage>(ChannelProtocol.MessageToServer);
        gateway2.setProducer(new SimpleProducer());
        gateway2.setReceiver(new SimpleMessageConsumer(queue));
        gateway2.setSerialiser(new AggregationMessageSerializer());
        gateway2.addMessageListener(new AggregationReplyListener(gateway2, replyMessage, sender));
        gateway2.runReceiver();
    }
}
