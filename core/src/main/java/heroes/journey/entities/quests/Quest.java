package heroes.journey.entities.quests;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;

public abstract class Quest {

    private final String name;

    public Quest(String name) {
        this.name = name;
    }

    public abstract void onComplete(GameState gameState, Entity completer);

    public abstract boolean isComplete(GameState gameState, Entity owner);

    public void onAccept() {
        // setup objectives to be watched
    }

    public void onDrop() {
        // apply penaltys
    }

    public boolean canAccept() {
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
