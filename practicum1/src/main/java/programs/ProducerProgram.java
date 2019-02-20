package programs;

import messaging.gateways.interfaces.ActiveMQGateway;
import messaging.helpers.ChannelProtocol;
import messaging.implementations.producers.SimpleProducer;
import messaging.implementations.receivers.SimpleMessageConsumer;
import messaging.listeners.received.AggregationMessageReceivedListener;
import messaging.listeners.sent.AggregationMessageSentListener;
import messaging.listeners.sent.StatefulMessageSentListener;
import messaging.listeners.received.StatefulMessageReceivedListener;
import messaging.models.AggregatedMessage;
import messaging.models.SimpleMessage;
import messaging.serializers.AggregationMessageSerializer;
import messaging.serializers.SimpleMessageSerializer;
import messaging.tracking.AggregationSession;
import messaging.tracking.StatefulSession;

import java.util.Scanner;

public class ProducerProgram {
    public static void main(String[] args) {
        aggregationProducer();
    }

    private static void correlationProducer() {
        StatefulSession session = new StatefulSession();
        ActiveMQGateway<SimpleMessage, SimpleMessage> gateway = new ActiveMQGateway<SimpleMessage, SimpleMessage>(ChannelProtocol.MessageToClient);
        gateway.setProducer(new SimpleProducer());
        gateway.setReceiver(new SimpleMessageConsumer(ChannelProtocol.MessageToServer));

        gateway.setSerialiser(new SimpleMessageSerializer());
        gateway.addMessageSentListener(new StatefulMessageSentListener(session));
        gateway.addMessageListener(new StatefulMessageReceivedListener(session));

        try {
            gateway.runReceiver();
        } catch (Exception e) {
            System.out.println("Unable to run receiver.");
        }

        while(true) {
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()){
                gateway.sendMessage(new SimpleMessage("Bart", scanner.nextLine()));
            }

        }
    }

    private static void aggregationProducer() {
        int idCounter = 1;
        AggregationSession session = new AggregationSession();
        ActiveMQGateway<AggregatedMessage, AggregatedMessage> gateway = new ActiveMQGateway<AggregatedMessage, AggregatedMessage>(ChannelProtocol.MessageToClient);
        gateway.setProducer(new SimpleProducer());
        gateway.setReceiver(new SimpleMessageConsumer(ChannelProtocol.MessageToServer));

        gateway.setSerialiser(new AggregationMessageSerializer());
        gateway.addMessageSentListener(new AggregationMessageSentListener(session));
        gateway.addMessageListener(new AggregationMessageReceivedListener(session));

        try {
            gateway.runReceiver();
        } catch (Exception e) {
            System.out.println("Unable to run receiver.");
        }

        while(true) {
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()){
                AggregatedMessage message = new AggregatedMessage("Bart", scanner.nextLine(), idCounter++);
                gateway.sendToQueue(message, ChannelProtocol.Aggregation1);
                gateway.sendToQueue(message, ChannelProtocol.Aggregation2);
            }

        }
    }
}
