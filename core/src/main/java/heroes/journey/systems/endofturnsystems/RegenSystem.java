package heroes.journey.systems.endofturnsystems;

import com.artemis.World;
import com.artemis.annotations.All;
import com.badlogic.gdx.graphics.Color;

import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.systems.EndOfTurnSystem;
import heroes.journey.ui.EffectManager;

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

        PositionComponent positionComponent = PositionComponent.get(world, entityId);
        if (positionComponent != null) {
            EffectManager.addTextEffect("+" + (statsComponent.getBody()), Color.GREEN,
                positionComponent.getX(), positionComponent.getY(), -15, 0);
            EffectManager.addTextEffect("+" + (statsComponent.getMana() * 2), Color.GREEN,
                positionComponent.getX(), positionComponent.getY(), 0, 0);
            EffectManager.addTextEffect("+" + (statsComponent.getBody() * 2), Color.GREEN,
                positionComponent.getX(), positionComponent.getY(), 15, 0);
        }
    }
}
