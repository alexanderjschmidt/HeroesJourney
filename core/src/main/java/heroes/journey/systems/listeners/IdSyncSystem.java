package heroes.journey.systems.listeners;

import java.util.UUID;

import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;

import heroes.journey.components.character.IdComponent;
import heroes.journey.systems.GameWorld;

@All({IdComponent.class})
public class IdSyncSystem extends BaseEntitySystem {

    @Override
    public void inserted(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);
        world.register(entityId, id);
    }

    @Override
    public void removed(int entityId) {
    }

    @Override
    protected void processSystem() {

    }
}
