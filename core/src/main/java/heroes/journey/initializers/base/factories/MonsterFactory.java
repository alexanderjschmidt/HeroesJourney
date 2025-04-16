package heroes.journey.initializers.base.factories;

import com.badlogic.ashley.core.Entity;
import heroes.journey.components.StatsComponent;

public class MonsterFactory {

    public static Entity monster() {
        Entity goblin = new Entity();

        goblin.add(new StatsComponent());

        return goblin;
    }

    public static Entity goblin() {
        Entity goblin = monster();

        return goblin;
    }

    public static Entity hobGoblin() {
        Entity hobGoblin = monster();

        return hobGoblin;
    }

}
