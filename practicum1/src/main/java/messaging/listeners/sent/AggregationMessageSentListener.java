package messaging.listeners.sent;

import messaging.listeners.interfaces.IOnMessageSendListener;
import messaging.models.AggregatedMessage;
import messaging.tracking.AggregationSession;

import java.io.Serializable;

public class AggregationMessageSentListener implements IOnMessageSendListener {
    private AggregationSession session;

    public AggregationMessageSentListener(AggregationSession session) {
        this.session = session;
    }

    public void onMessageSent(Serializable message) {
        AggregatedMessage aggregatedMessage = (AggregatedMessage)message;
        this.session.addKey(aggregatedMessage.getAggregationId());
    }
}
