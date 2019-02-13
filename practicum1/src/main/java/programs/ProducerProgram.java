package programs;

import messaging.gateways.interfaces.ActiveMQGateway;
import messaging.helpers.ChannelProtocol;
import messaging.implementations.producers.SimpleProducer;
import messaging.implementations.receivers.SimpleMessageReceiver;
import messaging.listeners.StatefulMessageSentListener;
import messaging.listeners.StatefulMessageReceivedListener;
import messaging.models.SimpleMessage;
import messaging.serialisers.SimpleMessageSerialiser;
import messaging.tracking.StatefulSession;

import java.util.Scanner;

public class ProducerProgram {
    public static void main(String[] args) {
        StatefulSession session = new StatefulSession();
        ActiveMQGateway<SimpleMessage, SimpleMessage> gateway = new ActiveMQGateway<SimpleMessage, SimpleMessage>(ChannelProtocol.MessageToClient);
        gateway.setProducer(new SimpleProducer());
        gateway.setReceiver(new SimpleMessageReceiver(ChannelProtocol.MessageToServer));

        gateway.setSerialiser(new SimpleMessageSerialiser());
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
