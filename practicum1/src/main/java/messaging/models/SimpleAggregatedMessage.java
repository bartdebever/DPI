package messaging.models;

public class SimpleAggregatedMessage implements IAggregatedMessage {
    private int aggregationId;

    public int getAggregationId() {
        return aggregationId;
    }

    public void setAggregationId(int id) {
        aggregationId = id;
    }
}
