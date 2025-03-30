package heroes.journey.components.quests;

import com.badlogic.ashley.core.Entity;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;

public class ObjectiveComponent {

    private Position location;
    private Action action;
    private Entity entity;
    // Examples
    // Delve at specific dungeon
    // Kill specific entity
    // Kill X of a type of entity
    // Gather specific items
    // Transport item to town
    // Discover something in fog of war
    // Use item at location
    // And objective <X> and <Y> done at the same turn
    // IE I am located at X, and did action Y

}
