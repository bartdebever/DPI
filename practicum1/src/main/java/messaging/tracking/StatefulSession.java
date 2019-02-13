package messaging.tracking;

import java.util.HashMap;
import java.util.Map;

public class StatefulSession {
    private Map<String, String> senders;

    public StatefulSession() {
        senders = new HashMap<String, String>();
    }

    public void sendMessage(String sender, String messageId) {
        if (!senders.containsKey(messageId)) {
            senders.put(messageId, sender);
        }
    }

    public String getSenderByMessage(String correlationId){
        return senders.get(correlationId);
    }

    public void removeState(String messageId) {
        senders.remove(messageId);
    }
}
