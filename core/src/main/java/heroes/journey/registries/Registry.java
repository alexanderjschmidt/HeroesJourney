package heroes.journey.registries;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Registry<T extends Registrable> extends HashMap<String,T> {

    public List<T> get(List<String> entryStrings) {
        return entryStrings.stream().map(this::get) // get the Action for each string
            .filter(Objects::nonNull) // optionally skip nulls
            .collect(Collectors.toList());
    }

    public T register(T entry) {
        if (containsKey(entry.getId())) {
            throw new RuntimeException(this.getClass() + " cannot register with id " + entry.getId() +
                " because that id is already registered");
        }
        put(entry.getId(), entry);
        return entry;
    }
}
