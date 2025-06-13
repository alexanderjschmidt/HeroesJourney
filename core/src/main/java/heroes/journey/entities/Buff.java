package heroes.journey.entities;

import static heroes.journey.registries.Registries.BuffManager;

import heroes.journey.registries.Registrable;
import lombok.Getter;

@Getter
public class Buff extends Registrable {

    private final int turnsBuffLasts;
    private final int timesBuffCanBeUsed;

    public Buff(String id, String name, int turnsBuffLasts, int timesBuffCanBeUsed) {
        super(id, name);
        this.turnsBuffLasts = turnsBuffLasts;
        this.timesBuffCanBeUsed = timesBuffCanBeUsed;
    }

    public Buff(String id, String name) {
        this(id, name, -1, -1);
    }

    public Buff register() {
        return BuffManager.register(this);
    }
}
