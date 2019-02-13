package messaging.tracking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AggregationSession {
    private Map<Integer, List<Serializable>> state;

    public void addReply(int key, Serializable reply) {
        if (!state.containsKey(key)) {
            return;
        }

        state.get(key).add(reply);
    }

    public List<Serializable> get(int key){
        return state.get(key);
    }

    public void addKey(int key) {
        state.put(key, new ArrayList<Serializable>());
    }

    public List<Serializable> getAndDelete(int key) {
        List<Serializable> values = state.get(key);
        state.remove(key);
        return values;
    }
}
