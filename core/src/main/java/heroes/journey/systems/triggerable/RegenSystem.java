package heroes.journey.systems.triggerable;

import static heroes.journey.initializers.utils.Utils.useBuff;

import java.util.UUID;

import com.artemis.annotations.All;

import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.initializers.base.Buffs;
import heroes.journey.initializers.utils.Utils;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;

@All({StatsComponent.class, IdComponent.class})
public class RegenSystem extends TriggerableSystem {

    private final GameState gameState;

    public RegenSystem(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);
        StatsComponent statsComponent = StatsComponent.get(world, id);

        if (useBuff(gameState, id, Buffs.rested)) {
            Utils.adjustHealth(gameState, id, statsComponent.getBody() * 2);
            Utils.adjustMana(gameState, id, statsComponent.getMind() * 2);
            Utils.adjustStamina(gameState, id, statsComponent.getBody() * 4);
        } else {
            // TODO fix stats ratios
            Utils.adjustHealth(gameState, id, statsComponent.getBody());
            Utils.adjustMana(gameState, id, statsComponent.getMind());
            Utils.adjustStamina(gameState, id, statsComponent.getBody() * 2);
        }
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
