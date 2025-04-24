package heroes.journey.initializers.base.factories;

import com.artemis.EntityEdit;

import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.NamedComponent;
import heroes.journey.systems.GameWorld;

public class MonsterFactory {

    public static Integer goblin(GameWorld world) {
        //TODO replace with Archetypes
        EntityEdit goblin = world.createEntity().edit();
        goblin.create(NamedComponent.class).name("Goblin");
        goblin.create(StatsComponent.class);
        return goblin.getEntityId();
    }

    public static Integer hobGoblin(GameWorld world) {
        EntityEdit goblin = world.createEntity().edit();
        goblin.create(NamedComponent.class).name("Hob Goblin");
        goblin.create(StatsComponent.class);
        return goblin.getEntityId();
    }

}
