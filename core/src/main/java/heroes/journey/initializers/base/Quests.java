package heroes.journey.initializers.base;

import heroes.journey.components.character.PlayerComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.quests.Quest;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.actions.DelveAction;

public class Quests implements InitializerInterface {

    public static Quest delve;

    static {
        delve = Quest.builder()
            .name("Delve a dungeon")
            .onComplete((input) -> {
                Utils.addItem(input, Items.ironSword, 1);
                PlayerComponent playerComponent = PlayerComponent.get(input.getGameState().getWorld(), input.getEntityId());
                if (playerComponent != null) {
                    playerComponent.fame(playerComponent.fame() + 10);
                }
            })
            .isComplete((input) -> Utils.justCompletedAction(input.getGameState(), input.getEntityId(), DelveAction.delve))
            .build()
            .register();
    }

}
