package messaging.helpers;

/**
 * Class inteded to be used to store static values of what channel should be used for what purpose.
 */
public class ChannelProtocol {
    /**
     * Used to send simple messages to the server.
     */
    public static final String MessageToServer = "Test.ToServer";

    /**
     * Used to send simple messages to the client.
     */
    public static final String MessageToClient = "Test.ToClient";

    public static final String Aggregation1 = "Test.Agg1";

    public static final String Aggregation2 =  "Test.Agg2";
}
