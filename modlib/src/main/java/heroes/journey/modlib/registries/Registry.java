package heroes.journey.modlib.registries;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Registry<T extends IRegistrable> extends HashMap<String, T> {

    public List<T> get(List<String> entryStrings) {
        return entryStrings.stream().map(this::get) // get the Action for each string
            .filter(Objects::nonNull) // optionally skip nulls
            .collect(Collectors.toList());
    }

    @Override
    public T get(Object id) {
        T item = super.get(id);
        if (item == null) {
            System.out.println("Failed to get item with id " + id + " in registry for " +
                this.values().stream().findFirst().get().getClass());
        }
        return item;
    }

    public T register(T entry) {
        if (entry == null || entry.getId() == null) {
            throw new IllegalArgumentException("Entry cannot be null or have a null Id");
        }
        if (containsKey(entry.getId())) {
            throw new IllegalArgumentException(
                this.values().stream().findFirst().get().getClass() + " cannot register with id " +
                    entry.getId() + " because that id is already registered");
        }
        put(entry.getId(), entry);
        return entry;
    }
}
