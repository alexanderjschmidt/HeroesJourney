package heroes.journey.entities;

import static heroes.journey.registries.Registries.BuffManager;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class Buff {

    @NonNull private String name;
    @Builder.Default private int turnsBuffLasts = -1;
    @Builder.Default private int timesBuffCanBeUsed = -1;

    @Override
    public String toString() {
        return name;
    }

    public Buff register() {
        return BuffManager.register(this);
    }
}
