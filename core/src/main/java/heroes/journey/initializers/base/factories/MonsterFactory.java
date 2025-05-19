package heroes.journey.initializers.base.factories;

import java.util.UUID;

import com.artemis.EntityEdit;

import heroes.journey.components.NamedComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.systems.GameWorld;

public class MonsterFactory {

    public static UUID goblinId, hobgoblinId;

    public static void init(GameWorld world) {
        goblin(world);
        hobGoblin(world);
    }

    private static void goblin(GameWorld world) {
        //TODO replace with Archetypes
        EntityEdit goblin = world.createEntity().edit();
        goblin.create(NamedComponent.class).name("Goblin");
        goblin.create(StatsComponent.class);
        IdComponent id = goblin.create(IdComponent.class);
        goblinId = id.uuid();
    }

    private static void hobGoblin(GameWorld world) {
        EntityEdit goblin = world.createEntity().edit();
        goblin.create(NamedComponent.class).name("Hob Goblin");
        goblin.create(StatsComponent.class);
        IdComponent id = goblin.create(IdComponent.class);
        hobgoblinId = id.uuid();
    }

}
