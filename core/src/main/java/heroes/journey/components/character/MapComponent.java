package heroes.journey.components.character;

import heroes.journey.GameState;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Fog;
import lombok.Getter;

import java.util.*;

@Getter
public class MapComponent extends PooledClonableComponent<MapComponent> {

    private Fog[][] fog;
    private final Set<UUID> knownLocations;

    public MapComponent() {
        fog = new Fog[GameState.global().getWidth()][GameState.global().getHeight()];
        for (Fog[] fogs : fog) {
            Arrays.fill(fogs, Fog.DENSE);
        }
        knownLocations = Collections.synchronizedSet(new HashSet<>());
    }

    public static MapComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(MapComponent.class, entityId);
    }

    @Override
    public void copy(MapComponent from) {
        this.fog = new Fog[from.fog.length][from.fog.length];
        for (int x = 0; x < from.fog.length / 2; x++) {
            System.arraycopy(from.fog[x], 0, fog[x], 0, from.fog[x].length);
        }
        this.knownLocations.addAll(from.knownLocations);
    }

    @Override
    protected void reset() {
        this.fog = new Fog[fog.length][fog.length];
        knownLocations.clear();
    }
}
