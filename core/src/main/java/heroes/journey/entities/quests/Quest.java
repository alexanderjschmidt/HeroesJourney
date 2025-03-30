package heroes.journey.entities.quests;

import heroes.journey.GameState;

public class Quest {

    private final String name;

    public Quest(String name) {
        this.name = name;
    }

    public void onComplete() {
        // give rewards
    }

    public boolean isComplete(GameState gameState) {
        return false;
    }

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
