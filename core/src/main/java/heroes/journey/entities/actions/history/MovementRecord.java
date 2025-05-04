package heroes.journey.entities.actions.history;

import heroes.journey.entities.Position;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MovementRecord extends Record {

    private final Position startPos, endPos;

    public MovementRecord(UUID entity, Position startPos, Position endPos) {
        super(entity);
        this.startPos = startPos;
        this.endPos = endPos;
    }

}
