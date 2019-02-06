package messaging.tracking;

import java.util.Map;

public abstract class StatefulProducer {
    private int idCounter;
    private Map<Integer, String> state;

    public int getIdCounter() {
        return idCounter;
    }

    public int getNextId() {
        return idCounter++;
    }

    public boolean saveState(int id, String sender){
        if (state.containsKey(id)) {
            return false;
        }

        state.put(id, sender);
        return true;
    }

    public String recieveState(int id){
        if (!state.containsKey(id)){
            return null;
        }

        return state.get(id);
    }
}
