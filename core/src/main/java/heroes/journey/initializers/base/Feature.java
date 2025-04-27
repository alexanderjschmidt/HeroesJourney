package heroes.journey.initializers.base;

import heroes.journey.entities.Position;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
}
