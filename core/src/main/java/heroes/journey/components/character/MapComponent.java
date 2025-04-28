package heroes.journey.components.character;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.artemis.World;

import heroes.journey.GameState;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.tilemap.Fog;
import lombok.Getter;

@Getter
public class MapComponent extends PooledClonableComponent<MapComponent> {

    private Fog[][] fog;
    private Set<Integer> knownLocations;

    public MapComponent() {
        fog = new Fog[GameState.global().getWidth()][GameState.global().getHeight()];
        for (Fog[] fogs : fog) {
            Arrays.fill(fogs, Fog.DENSE);
        }
        knownLocations = new HashSet<>();
    }

    public static MapComponent get(World world, int entityId) {
        return world.getMapper(MapComponent.class).get(entityId);
    }

    @Override
    public void copy(MapComponent from) {
        this.fog = from.fog;
        this.knownLocations = from.knownLocations;
    }
}
