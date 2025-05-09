package heroes.journey.components.character;

import com.artemis.PooledComponent;
import com.artemis.annotations.Transient;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@ToString
@Transient
@Accessors(fluent = true, chain = true)
@Setter
@Getter
public class EventQueueComponent extends PooledComponent {

    Queue<Runnable> events = new LinkedList<>();

    public static EventQueueComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(EventQueueComponent.class, entityId);
    }

    @Override
    protected void reset() {
        events.clear();
    }
}
