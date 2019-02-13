package messaging.listeners;

import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.tracking.AggregationSession;

import java.io.Serializable;

public class AggregationMessageReceivedListener implements IMessageReceivedListener {
    private AggregationSession session;

    public AggregationMessageReceivedListener(AggregationSession session) {
        this.session = session;
    }

    public void onMessageReceived(Serializable message) {

    }
}
