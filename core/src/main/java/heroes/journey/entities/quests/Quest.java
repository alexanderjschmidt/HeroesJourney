package heroes.journey.entities.quests;

public class Quest {

    private final String name;

    public Quest(String name) {
        this.name = name;
    }

    public void onComplete() {

    }

    public void onAccept() {

    }

    public boolean canAccept() {
        return true;
    }

    public void onDrop() {

    }

    @Override
    public String toString() {
        return name;
    }
}
