package heroes.journey.initializers.base;

import heroes.journey.entities.quests.Quest;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.actions.DelveAction;
import heroes.journey.initializers.utils.Utils;

public class Quests implements InitializerInterface {

    public static Quest delve;

    static {
        delve = Quest.builder()
            .name("Delve a dungeon")
            .fameReward(10)
            .onComplete((input) -> Utils.addItem(input, Items.ironSword, 1))
            .isComplete((input) -> Utils.justCompletedAction(input.getGameState(), input.getEntityId(),
                DelveAction.delve))
            .build()
            .register();
    }

}
