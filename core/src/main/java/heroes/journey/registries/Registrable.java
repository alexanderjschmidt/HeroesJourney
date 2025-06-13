package heroes.journey.registries;

import lombok.Getter;

@Getter
public class Registrable {

    private final String id;
    private final String name;

    public Registrable(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Registrable(String id) {
        this.id = id;
        this.name = id;
    }

    public String toString() {
        return name == null ? id : name;
    }
}
