package heroes.journey.systems.triggerable;

import com.artemis.World;
import com.artemis.annotations.All;
import com.badlogic.gdx.graphics.Color;

import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.systems.TriggerableSystem;
import heroes.journey.ui.WorldEffectManager;

@All({StatsComponent.class})
public class RegenSystem extends TriggerableSystem {

    @Override
    protected void process(int entityId) {
        World world = getWorld();
        StatsComponent statsComponent = StatsComponent.get(world, entityId);
        // TODO fix stats ratios
        statsComponent.adjustHealth(statsComponent.getBody());
        statsComponent.adjustMana(statsComponent.getMind());
        statsComponent.adjustStamina(statsComponent.getBody() * 2);

        PositionComponent positionComponent = PositionComponent.get(world, entityId);
        if (positionComponent != null) {
            WorldEffectManager.addTextEffect("+" + (statsComponent.getBody()), Color.GREEN,
                positionComponent.getX(), positionComponent.getY(), -15, 0);
            WorldEffectManager.addTextEffect("+" + (statsComponent.getMana() * 2), Color.GREEN,
                positionComponent.getX(), positionComponent.getY(), 0, 0);
            WorldEffectManager.addTextEffect("+" + (statsComponent.getBody() * 2), Color.GREEN,
                positionComponent.getX(), positionComponent.getY(), 15, 0);
        }
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
