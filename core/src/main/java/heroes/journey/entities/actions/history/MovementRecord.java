package heroes.journey.entities.actions.history;

import heroes.journey.entities.Position;
import lombok.Getter;

@Getter
public class MovementRecord extends Record {

    private final Position startPos, endPos;

    public MovementRecord(Integer entity, Position startPos, Position endPos) {
        super(entity);
        this.startPos = startPos;
        this.endPos = endPos;
    }

}
