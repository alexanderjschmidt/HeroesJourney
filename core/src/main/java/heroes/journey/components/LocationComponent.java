package heroes.journey.components;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.artemis.utils.IntBag;

import heroes.journey.components.character.IdComponent;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true, chain = true)
public class LocationComponent extends PooledClonableComponent<LocationComponent> {

    @NonNull private UUID region;

    public static LocationComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(LocationComponent.class, entityId);
    }

    @Override
    protected void reset() {
    }

    @Override
    public void copy(LocationComponent from) {
        region = from.region;
    }

    public static List<UUID> get(GameWorld world) {
        IntBag entities = world.getLocationSubscription().getEntities();
        int[] ids = entities.getData();

        List<UUID> idsList = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            int entityId = ids[i];
            UUID id = IdComponent.get(world, entityId);
            idsList.add(id);
        }
        return idsList;
    }
}
