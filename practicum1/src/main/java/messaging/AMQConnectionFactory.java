package messaging;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;

public class AMQConnectionFactory {
    private static final String BROKERURL = "tcp://localhost:61616";
    public static Connection createConnection() throws JMSException {
        return createConnection(true);
    }

    public static Connection createConnection(boolean trustAll) throws JMSException {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKERURL);

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection;
    }
}
