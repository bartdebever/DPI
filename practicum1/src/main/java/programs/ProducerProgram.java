package programs;

import messaging.gateways.interfaces.ActiveMQGateway;
import messaging.helpers.ChannelProtocol;
import messaging.implementations.producers.SimpleProducer;
import messaging.implementations.producers.StatefulProducer;
import messaging.implementations.receivers.StatefullReceiver;
import messaging.models.SimpleMessage;
import messaging.tracking.StatefullSession;

import java.util.Scanner;

public class ProducerProgram {
    public static void main(String[] args) {
        StatefullSession session = new StatefullSession();
        ActiveMQGateway<SimpleMessage, SimpleMessage> gateway = new ActiveMQGateway<SimpleMessage, SimpleMessage>(ChannelProtocol.MessageToClient);
        gateway.setProducer(new StatefulProducer(session));
        gateway.setReceiver(new StatefullReceiver(ChannelProtocol.MessageToServer, session));

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
