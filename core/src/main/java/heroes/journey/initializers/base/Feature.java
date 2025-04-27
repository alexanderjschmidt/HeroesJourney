package heroes.journey.initializers.base;

import heroes.journey.entities.Position;
import heroes.journey.utils.Direction;

import java.util.*;

import static heroes.journey.utils.Direction.approximateDirection;

public class Feature {
    public String type;
    public Position location;
    public Set<Feature> connections;

    public Feature(String type, Position location) {
        this.type = type;
        this.location = location;
        connections = new HashSet<>();
    }

    public void add(Feature feature) {
        connections.add(feature);
        feature.connections.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feature feature = (Feature) o;

        return Objects.equals(location, feature.location);
    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }

    public void makeConnections(List<Feature> features, int maxFeatures) {
        // Step 1: Sort all features by distance from the start
        List<Feature> sorted = features.stream()
            .filter(f -> !f.location.equals(location)) // skip yourself
            .sorted(Comparator.comparingDouble(f -> f.location.distanceTo(location)))
            .toList();

        // Step 2: Assign closest features to directions
        EnumMap<Direction, Feature> result = new EnumMap<>(Direction.class);
        int featuresAdded = 0;

        for (Feature info : sorted) {
            if (featuresAdded >= maxFeatures) break;

            Direction dir = approximateDirection(location, info.location);

            if (!result.containsKey(dir)) {
                result.put(dir, info);
                this.add(info);
                featuresAdded++;
            }
        }
    }
}
