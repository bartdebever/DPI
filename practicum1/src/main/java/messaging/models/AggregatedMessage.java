package messaging.models;

import messaging.models.interfaces.IAggregatedMessage;

public class AggregatedMessage extends SimpleMessage implements IAggregatedMessage {
    private int aggregationId;

    public AggregatedMessage(String sender, String content, int aggregationId) {
        this.setSender(sender);
        this.setContent(content);
        this.aggregationId = aggregationId;
    }

    public int getAggregationId() {
        return aggregationId;
    }

    public void setAggregationId(int id) {
        this.aggregationId = id;
    }
}
