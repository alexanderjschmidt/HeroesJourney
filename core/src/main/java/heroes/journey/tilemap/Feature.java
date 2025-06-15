package heroes.journey.tilemap;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.entities.Position;
import heroes.journey.registries.FeatureManager;
import heroes.journey.utils.Direction;

import java.util.*;

import static heroes.journey.utils.Direction.approximateDirection;

public class Feature {

    public UUID entityId;
    public FeatureType type;
    public Position location;
    public Set<UUID> connections;

    public Feature(UUID entityId, FeatureType type, Position location) {
        this.entityId = entityId;
        this.type = type;
        this.location = location;
        connections = new HashSet<>();
        FeatureManager.get().put(entityId, this);
    }

    public void add(Feature feature) {
        connections.add(feature.entityId);
        feature.connections.add(entityId);
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

        Feature feature = (Feature) o;

        return Objects.equals(location, feature.location);
    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }

    public void makeConnections(int maxFeatures) {
        // Step 1: Sort all features by distance from the start
        List<Feature> sorted = FeatureManager.get()
            .values()
            .stream()
            .filter(f -> !f.location.equals(location)) // skip yourself
            .sorted(Comparator.comparingDouble(f -> f.location.distanceTo(location)))
            .toList();

        // Step 2: Assign closest features to directions
        EnumMap<Direction, Feature> result = new EnumMap<>(Direction.class);
        int featuresAdded = 0;

        for (Feature info : sorted) {
            if (featuresAdded >= maxFeatures)
                break;

            Direction dir = approximateDirection(location, info.location);

            if (!result.containsKey(dir)) {
                result.put(dir, info);
                this.add(info);
                featuresAdded++;
            }
        }
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

    public Set<UUID> getConnections() {
        return this.connections;
    }
}
