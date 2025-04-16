package heroes.journey.components.overworld.place;

import com.badlogic.ashley.core.Entity;
import heroes.journey.entities.items.ItemInterface;

import java.util.List;

public class DungeonComponent {

    private int length; // Base length of dungeon
    private int complexity; // how likely you are to get lost
    private float percentOfMonsters;
    private List<Entity> possibleMonsters;
    private List<ItemInterface> completionRewards;
    private Entity boss;

    // Secrets/Bonus Rooms
    // private float percentOfTraps; // The left over after these two percentages is empty rooms
    // private List<Entity> possibleTraps;
    // private float environmentalHazardLevel; // Theme of dungeon

}
