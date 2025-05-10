package heroes.journey.components.character;

import heroes.journey.GameState;
import heroes.journey.components.utils.FogMap;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Fog;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MapComponent extends PooledClonableComponent<MapComponent> {

    private final FogMap fogMap;
    @Getter
    private final Set<UUID> knownLocations;

    public MapComponent() {
        fogMap = new FogMap(new Fog[GameState.global().getWidth()][GameState.global().getHeight()]);
        knownLocations = new HashSet<>();
    }

    public static MapComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(MapComponent.class, entityId);
    }

    public Fog[][] getFog() {
        return fogMap.getFog();
    }

    @Override
    public void copy(MapComponent from) {
        this.fogMap.copy(from.fogMap);
        this.knownLocations.addAll(from.knownLocations);
    }

    @Override
    protected void reset() {
        this.fogMap.clear();
        knownLocations.clear();
    }

}
