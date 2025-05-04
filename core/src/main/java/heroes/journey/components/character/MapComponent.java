package heroes.journey.components.character;

import heroes.journey.GameState;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Fog;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class MapComponent extends PooledClonableComponent<MapComponent> {

    private Fog[][] fog;
    private Set<UUID> knownLocations;

    public MapComponent() {
        fog = new Fog[GameState.global().getWidth()][GameState.global().getHeight()];
        for (Fog[] fogs : fog) {
            Arrays.fill(fogs, Fog.DENSE);
        }
        knownLocations = new HashSet<>();
    }

    public static MapComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(MapComponent.class, entityId);
    }

    @Override
    public void copy(MapComponent from) {
        this.fog = from.fog;
        this.knownLocations = from.knownLocations;
    }
}
