package messaging.implementations.producers;

import messaging.implementations.ActiveMQMessageProducer;
import messaging.models.IAggregatedMessage;

import javax.jms.ObjectMessage;

public class AggregatedProducer extends ActiveMQMessageProducer {
    private int aggregationIdCounter = 1;
    @Override
    public ObjectMessage beforeMessageSent(ObjectMessage message, Object payload) {

        if (!(payload instanceof IAggregatedMessage)) {
            return super.beforeMessageSent(message, payload);
        }

        IAggregatedMessage aggregatedMessage = (IAggregatedMessage)payload;
        aggregatedMessage.setAggregationId(aggregationIdCounter++);
        return super.beforeMessageSent(message, aggregatedMessage);
    }
}
