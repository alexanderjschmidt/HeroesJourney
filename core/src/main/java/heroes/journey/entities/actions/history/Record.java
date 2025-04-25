package heroes.journey.entities.actions.history;

import lombok.Getter;

@Getter
public class Record {

    private final Integer entity;

    public Record(Integer entity) {
        this.entity = entity;
    }

}
