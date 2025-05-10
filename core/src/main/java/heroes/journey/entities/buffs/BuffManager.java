package heroes.journey.entities.buffs;

import java.util.HashMap;

public class BuffManager extends HashMap<String, Buff> {

    private static BuffManager buffManager;

    public static BuffManager get() {
        if (buffManager == null)
            buffManager = new BuffManager();
        return buffManager;
    }

    private BuffManager() {
    }

    public static Buff register(Buff buff) {
        get().put(buff.getName(), buff);
        return buff;
    }

}
