package heroes.journey.entities;

import static heroes.journey.registries.Registries.BuffManager;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Buff {
    @NonNull private final String name;
    private final int turnsBuffLasts;
    private final int timesBuffCanBeUsed;

    public Buff(String name, int turnsBuffLasts, int timesBuffCanBeUsed) {
        this.name = name;
        this.turnsBuffLasts = turnsBuffLasts;
        this.timesBuffCanBeUsed = timesBuffCanBeUsed;
    }

    public Buff(String name) {
        this(name, -1, -1);
    }

    @Override
    public String toString() {
        return name;
    }

    public Buff register() {
        return BuffManager.register(this);
    }
}
