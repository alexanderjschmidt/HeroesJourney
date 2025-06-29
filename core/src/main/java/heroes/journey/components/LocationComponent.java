package heroes.journey.components;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.artemis.utils.IntBag;

import heroes.journey.components.character.IdComponent;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.FeatureType;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true, chain = true)
public class LocationComponent extends PooledClonableComponent<LocationComponent> {

    @NonNull private String featureType;
    @NonNull private UUID region;

    public static LocationComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(LocationComponent.class, entityId);
    }

    @Override
    protected void reset() {
        featureType = "";
    }

    @Override
    public void copy(LocationComponent from) {
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

    public static List<UUID> get(GameWorld world, FeatureType type) {
        IntBag entities = world.getLocationSubscription().getEntities();
        int[] ids = entities.getData();

        List<UUID> idsList = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            int entityId = ids[i];
            UUID id = IdComponent.get(world, entityId);
            LocationComponent feature = world.getMapper(LocationComponent.class).get(entityId);

            if (type.getId().equals(feature.featureType)) {
                idsList.add(id);
            }
        }
        return idsList;
    }
}
