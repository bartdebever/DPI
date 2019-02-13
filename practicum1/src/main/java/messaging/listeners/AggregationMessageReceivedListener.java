package messaging.listeners;

import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.models.AggregatedMessage;
import messaging.tracking.AggregationSession;

import java.io.Serializable;

public class AggregationMessageReceivedListener implements IMessageReceivedListener {
    private AggregationSession session;

    public AggregationMessageReceivedListener(AggregationSession session) {
        this.session = session;
    }

    public void onMessageReceived(Serializable message) {
        AggregatedMessage aggregatedMessage = (AggregatedMessage)message;
        session.addReply(aggregatedMessage.getAggregationId(), aggregatedMessage);
    }
}
