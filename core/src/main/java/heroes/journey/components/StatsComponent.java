package heroes.journey.components;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.tagging.Attributes;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(toBuilder = true)
public class StatsComponent implements ClonableComponent<StatsComponent> {

    public static final int MAX_HEALTH = 10, MAX_MANA = 10;

    @Builder.Default @Setter private int body = 1, mind = 1;

    @Builder.Default private final int handicapMult = 10;

    @Setter @Builder.Default private int health = MAX_HEALTH;

    @Builder.Default @Setter(AccessLevel.NONE) private int mana = MAX_MANA;

    @Builder.Default private final Attributes attributes = new Attributes();

    // Returns if they are Alive
    public boolean adjustHealth(int health) {
        this.health = (int)Math.min(MAX_HEALTH, Math.max(0, this.health + health));
        return (this.health + health > 0);
    }

    // Returns if they can use the spell
    public boolean adjustMana(int mana) {
        if (this.mana + mana < 0) {
            return false;
        }
        this.mana = (int)Math.min(MAX_MANA, Math.max(0, this.mana + mana));
        return true;
    }

    public int getMoveDistance() {
        return body + 4;
    }

    public int getSpeed() {
        return body;
    }

    private static final ComponentMapper<StatsComponent> mapper = ComponentMapper.getFor(
        StatsComponent.class);

    public static StatsComponent get(Entity entity) {
        return mapper.get(entity);
    }

    @Override
    public StatsComponent clone() {
        return this.toBuilder().build();
    }
}
