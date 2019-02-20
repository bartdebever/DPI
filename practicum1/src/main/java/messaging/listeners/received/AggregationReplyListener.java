package messaging.listeners.received;

import messaging.gateways.interfaces.ActiveMQGateway;
import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.models.AggregatedMessage;

import java.io.Serializable;

public class AggregationReplyListener implements IMessageReceivedListener {
    private ActiveMQGateway gateway;
    private String replyText;
    private String sender;

    public AggregationReplyListener(ActiveMQGateway gateway, String replyText, String sender) {
        this.gateway = gateway;
        this.replyText = replyText;
        this.sender = sender;
    }

    public void onMessageReceived(Serializable message) {
        AggregatedMessage aggregatedMessage = (AggregatedMessage) message;
        AggregatedMessage newMessage = new AggregatedMessage(sender, replyText, aggregatedMessage.getAggregationId());

        gateway.sendMessage(newMessage);
    }
}
