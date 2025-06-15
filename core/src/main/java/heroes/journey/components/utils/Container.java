package heroes.journey.components.utils;

import java.util.HashMap;

public class Container<I, C extends Container<I,C>> extends HashMap<I,Integer> {

    public static final int ENTRY_LENGTH = 20;

    public String toString(I item, String displayName) {
        return String.format("%-" + (ENTRY_LENGTH - 3) + "s x%d", displayName, get(item));
    }

    public C add(I item) {
        this.add(item, 1);
        return (C)this;
    }

    public C add(I item, int count) {
        if (this.containsKey(item))
            this.put(item, this.get(item) + count);
        else
            this.put(item, count);
        return (C)this;
    }

    public void remove(I item, int count) {
        if (this.containsKey(item)) {
            if (this.get(item) <= count) {
                this.remove(item);
            } else {
                this.put(item, this.get(item) - count);
            }
        }
    }

    public C clone() {
        return (C)super.clone();
    }

}
