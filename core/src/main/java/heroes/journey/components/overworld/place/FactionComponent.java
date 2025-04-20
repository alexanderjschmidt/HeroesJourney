package heroes.journey.components.overworld.place;

import java.util.ArrayList;
import java.util.List;

import com.artemis.PooledComponent;
import com.artemis.World;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = true)
@Getter
public class FactionComponent extends PooledComponent {

    @Setter private String name;

    private final List<Position> ownedLocations;

    public FactionComponent() {
        this.ownedLocations = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    public FactionComponent addOwnedLocation(GameState gameState, Integer faction, Position position) {
        this.ownedLocations.add(position);
        gameState.getEntities().addFaction(faction, position.getX(), position.getY());
        return this;
    }

    public static FactionComponent get(World world, int entityId) {
        return world.getMapper(FactionComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
        name = null;
        ownedLocations.clear();
    }
}
