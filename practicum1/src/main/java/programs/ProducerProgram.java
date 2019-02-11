package programs;

import messaging.helpers.ChannelProtocol;
import messaging.implementations.ActiveMQMessageProducer;
import messaging.implementations.receivers.SimpleMessageReceiver;
import messaging.models.SimpleMessage;

import java.util.Scanner;

public class ProducerProgram {
    public static void main(String[] args){
        Thread receiverThread = new Thread(new SimpleMessageReceiver(ChannelProtocol.MessageToServer));
        receiverThread.start();

        while(true) {
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()){
                ActiveMQMessageProducer.sendMessage(new SimpleMessage("Bart", scanner.nextLine()), ChannelProtocol.MessageToClient);
            }

        }
    }
}
