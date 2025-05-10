package heroes.journey.entities.buffs;

import heroes.journey.entities.tagging.Attributes;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class Buff {

    @NonNull
    private String name;
    @Builder.Default
    private int turnsBuffLasts = -1;
    @Builder.Default
    private int timesBuffCanBeUsed = -1;
    private final Attributes attributes = new Attributes();

    @Override
    public String toString() {
        return name;
    }

    public Buff register() {
        return BuffManager.register(this);
    }
}
