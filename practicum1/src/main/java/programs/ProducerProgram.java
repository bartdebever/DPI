package programs;

import messaging.helpers.ChannelProtocol;
import messaging.implementations.producers.StatefulProducer;
import messaging.implementations.receivers.StatefullReceiver;
import messaging.models.SimpleMessage;
import messaging.tracking.StatefullSession;

import java.util.Scanner;

public class ProducerProgram {
    public static void main(String[] args) {
        StatefullSession session = new StatefullSession();
        StatefulProducer producer = new StatefulProducer(session);
        Thread receiverThread = new Thread(new StatefullReceiver(ChannelProtocol.MessageToServer, session));
        receiverThread.start();

        while(true) {
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()){
                producer.sendMessage(new SimpleMessage("Bart", scanner.nextLine()), ChannelProtocol.MessageToClient);
            }

        }
    }
}
