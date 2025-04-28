package heroes.journey.systems.endofturnsystems;

import com.artemis.World;
import com.artemis.annotations.All;

import heroes.journey.components.StatsComponent;
import heroes.journey.systems.EndOfTurnSystem;

@All({StatsComponent.class})
public class RegenSystem extends EndOfTurnSystem {

    @Override
    protected void process(int entityId) {
        World world = getWorld();
        StatsComponent statsComponent = StatsComponent.get(world, entityId);
        // TODO fix stats ratios
        statsComponent.adjustHealth(statsComponent.getBody());
        statsComponent.adjustMana(statsComponent.getMind());
        statsComponent.adjustStamina(statsComponent.getBody() * 2);
    }
}
