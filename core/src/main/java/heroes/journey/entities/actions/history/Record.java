package heroes.journey.entities.actions.history;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Record {

    private final UUID entity;

    public Record(UUID entity) {
        this.entity = entity;
    }

}
