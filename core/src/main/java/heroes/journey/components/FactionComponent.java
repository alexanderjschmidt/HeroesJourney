package heroes.journey.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionComponent implements ClonableComponent<FactionComponent> {

    private final String name;
    private final List<UUID> members;

    private final List<Position> ownedLocations;

    public FactionComponent(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        this.ownedLocations = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Position> getOwnedLocations() {
        return ownedLocations;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public FactionComponent clone() {
        return new FactionComponent(name);
    }

    private static final ComponentMapper<FactionComponent> mapper = ComponentMapper.getFor(
        FactionComponent.class);

    public static FactionComponent get(Entity entity) {
        return mapper.get(entity);
    }

    public Component addOwnedLocation(GameState gameState, Entity faction, Position position) {
        this.ownedLocations.add(position);
        gameState.getEntities().addFaction(faction, position.getX(), position.getY());
        return this;
    }
}
