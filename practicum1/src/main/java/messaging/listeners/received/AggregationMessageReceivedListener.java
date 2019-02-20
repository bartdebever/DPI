package messaging.listeners.received;

import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.models.AggregatedMessage;
import messaging.tracking.AggregationSession;

import java.io.Serializable;
import java.util.List;

public class AggregationMessageReceivedListener implements IMessageReceivedListener {
    private AggregationSession session;

    public AggregationMessageReceivedListener(AggregationSession session) {
        this.session = session;
    }

    public void onMessageReceived(Serializable message) {
        AggregatedMessage aggregatedMessage = (AggregatedMessage)message;
        session.addReply(aggregatedMessage.getAggregationId(), aggregatedMessage);

        List<Serializable> replies = session.get(aggregatedMessage.getAggregationId());
        if (replies.size() == 2) {
            System.out.println("Replies received for aggregationId " + aggregatedMessage.getAggregationId());
            for (Serializable serializable : replies) {
                AggregatedMessage reply = (AggregatedMessage) serializable;

                System.out.println(String.format("%s: %s", reply.getSender(), reply.getContent()));
            }
        }
    }
}
