package heroes.journey.systems.triggerable;

import com.artemis.annotations.All;
import com.badlogic.gdx.graphics.Color;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;
import heroes.journey.ui.WorldEffectManager;

import java.util.UUID;

@All({StatsComponent.class, IdComponent.class})
public class RegenSystem extends TriggerableSystem {

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        StatsComponent statsComponent = StatsComponent.get(world, id);
        // TODO fix stats ratios
        statsComponent.adjustHealth(statsComponent.getBody());
        statsComponent.adjustMana(statsComponent.getMind());
        statsComponent.adjustStamina(statsComponent.getBody() * 2);

        PositionComponent positionComponent = PositionComponent.get(world, id);
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
