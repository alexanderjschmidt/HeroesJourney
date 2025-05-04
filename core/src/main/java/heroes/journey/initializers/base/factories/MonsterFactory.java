package heroes.journey.initializers.base.factories;

import com.artemis.EntityEdit;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.systems.GameWorld;

import java.util.UUID;

public class MonsterFactory {

    public static UUID goblinId, hobgoblinId;

    public static Integer goblin(GameWorld world) {
        //TODO replace with Archetypes
        EntityEdit goblin = world.createEntity().edit();
        goblin.create(NamedComponent.class).name("Goblin");
        goblin.create(StatsComponent.class);
        IdComponent id = goblin.create(IdComponent.class);
        goblinId = id.uuid();
        return goblin.getEntityId();
    }

    public static Integer hobGoblin(GameWorld world) {
        EntityEdit goblin = world.createEntity().edit();
        goblin.create(NamedComponent.class).name("Hob Goblin");
        goblin.create(StatsComponent.class);
        IdComponent id = goblin.create(IdComponent.class);
        hobgoblinId = id.uuid();
        return goblin.getEntityId();
    }

}
