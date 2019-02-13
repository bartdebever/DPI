package messaging.models;

import messaging.models.interfaces.IAggregatedMessage;

public class AggregatedMessage extends SimpleMessage implements IAggregatedMessage {
    private int aggregationId;

    public int getAggregationId() {
        return aggregationId;
    }

    public void setAggregationId(int id) {
        this.aggregationId = id;
    }
}
