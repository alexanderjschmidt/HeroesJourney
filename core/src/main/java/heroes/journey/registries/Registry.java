package heroes.journey.registries;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Registry<T> extends HashMap<String,T> {

    public List<T> get(List<String> entryStrings) {
        return entryStrings.stream().map(this::get) // get the Action for each string
            .filter(Objects::nonNull) // optionally skip nulls
            .collect(Collectors.toList());
    }

    public T register(T entry) {
        if (containsKey(entry.toString())) {
            throw new RuntimeException(this.getClass() + " cannot register with name " + entry.toString() +
                " because that name is already registered");
        }
        put(entry.toString(), entry);
        return entry;
    }
}
