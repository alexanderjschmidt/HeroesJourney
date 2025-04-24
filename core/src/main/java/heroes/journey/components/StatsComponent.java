package heroes.journey.components;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.tagging.Attributes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class StatsComponent extends PooledClonableComponent<StatsComponent> {

    public static final int MAX_HEALTH = 10, MAX_MANA = 10;

    @Setter private int body = 1, mind = 1;

    @Setter private int handicapMult = 10;

    @Setter private int health = MAX_HEALTH;

    @Setter(AccessLevel.NONE) private int mana = MAX_MANA;

    private final Attributes attributes = new Attributes();

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

    public static StatsComponent get(World world, int entityId) {
        return world.getMapper(StatsComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
        attributes.clear();
    }

    @Override
    public void copy(StatsComponent from) {
        body = from.body;
        mind = from.mind;
        handicapMult = from.getHandicapMult();
        health = from.health;
        mana = from.mana;
        attributes.putAll(from.attributes);
    }
}
