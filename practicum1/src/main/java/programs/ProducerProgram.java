package programs;

import messaging.gateways.interfaces.ActiveMQGateway;
import messaging.helpers.ChannelProtocol;
import messaging.implementations.producers.SimpleProducer;
import messaging.implementations.receivers.SimpleMessageConsumer;
import messaging.listeners.sent.StatefulMessageSentListener;
import messaging.listeners.received.StatefulMessageReceivedListener;
import messaging.models.SimpleMessage;
import messaging.serialisers.SimpleMessageSerializer;
import messaging.tracking.StatefulSession;

import java.util.Scanner;

public class ProducerProgram {
    public static void main(String[] args) {
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
}
