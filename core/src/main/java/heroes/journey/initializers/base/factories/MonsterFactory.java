package heroes.journey.initializers.base.factories;

import com.badlogic.ashley.core.Entity;

import heroes.journey.components.StatsComponent;
import heroes.journey.components.overworld.character.NamedComponent;

public class MonsterFactory {

    public static Entity goblin() {
        Entity goblin = new Entity();

        goblin.add(new NamedComponent("Goblin")).add(StatsComponent.builder().handicapMult(3).build().init());

        return goblin;
    }

    public static Entity hobGoblin() {
        Entity hobGoblin = new Entity();

        hobGoblin.add(new NamedComponent("Hob Goblin"))
            .add(StatsComponent.builder().handicapMult(5).build().init());

        return hobGoblin;
    }

}
