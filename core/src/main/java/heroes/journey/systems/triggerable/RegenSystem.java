package heroes.journey.systems.triggerable;

import static heroes.journey.initializers.utils.Utils.useBuff;
import static heroes.journey.registries.Registries.BuffManager;

import java.util.UUID;

import com.artemis.annotations.All;

import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.initializers.base.tags.Stats;
import heroes.journey.initializers.utils.StatsUtils;
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
        Attributes statsComponent = StatsComponent.get(world, id);

        if (useBuff(gameState, id, BuffManager.get("rested"))) {
            StatsUtils.adjustHealth(gameState, id, statsComponent.get(Stats.BODY) * 2);
            StatsUtils.adjustMana(gameState, id, statsComponent.get(Stats.MIND) * 2);
            StatsUtils.adjustStamina(gameState, id, statsComponent.get(Stats.BODY) * 4);
        } else {
            // TODO fix stats ratios
            StatsUtils.adjustHealth(gameState, id, statsComponent.get(Stats.BODY));
            StatsUtils.adjustMana(gameState, id, statsComponent.get(Stats.MIND));
            StatsUtils.adjustStamina(gameState, id, statsComponent.get(Stats.BODY) * 2);
        }
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
