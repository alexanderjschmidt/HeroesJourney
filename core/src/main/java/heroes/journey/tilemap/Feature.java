package heroes.journey.tilemap;

import java.util.Objects;
import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.entities.Position;
import heroes.journey.registries.FeatureManager;

public class Feature {

    public UUID entityId;
    public FeatureType type;
    public Position location;

    public Feature(UUID entityId, FeatureType type, Position location) {
        this.entityId = entityId;
        this.type = type;
        this.location = location;
        FeatureManager.get().put(entityId, this);
    }

    @Override
    public String toString() {
        return NamedComponent.get(GameState.global().getWorld(), entityId, "Unnamed Feature");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Feature feature = (Feature)o;

        return Objects.equals(location, feature.location);
    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }

    public UUID getEntityId() {
        return this.entityId;
    }

    public FeatureType getType() {
        return this.type;
    }

    public Position getLocation() {
        return this.location;
    }
}
