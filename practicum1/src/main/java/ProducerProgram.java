import messaging.ActiveMQMessageProducer;
import messaging.models.SimpleMessage;

import java.util.Scanner;

public class ProducerProgram {
    public static void main(String[] args){
        while(true) {
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()){
                ActiveMQMessageProducer.SendReply(new SimpleMessage("Bart", scanner.nextLine()), "Test.Test");
            }

        }
    }
}
