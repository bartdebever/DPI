package programs;

import messaging.gateways.interfaces.ActiveMQGateway;
import messaging.helpers.ChannelProtocol;
import messaging.implementations.producers.SimpleProducer;
import messaging.implementations.receivers.StatefullReceiver;
import messaging.listeners.StatefulMessageSentListener;
import messaging.models.SimpleMessage;
import messaging.serialisers.SimpleMessageSerialiser;
import messaging.tracking.StatefullSession;

import java.util.Scanner;

public class ProducerProgram {
    public static void main(String[] args) {
        StatefullSession session = new StatefullSession();
        ActiveMQGateway<SimpleMessage, SimpleMessage> gateway = new ActiveMQGateway<SimpleMessage, SimpleMessage>(ChannelProtocol.MessageToClient);
        gateway.setProducer(new SimpleProducer());
        gateway.setReceiver(new StatefullReceiver(ChannelProtocol.MessageToServer, session));

        gateway.setSerialiser(new SimpleMessageSerialiser());
        gateway.addMessageSentListener(new StatefulMessageSentListener(session));

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
