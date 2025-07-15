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

    @Override
    public T get(Object id) {
        T item = super.get(id);
        if (item == null) {
            System.out.println("Failed to get item with id " + id.toString());
        }
        return item;
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
