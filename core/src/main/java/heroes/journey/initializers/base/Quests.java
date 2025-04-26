package heroes.journey.initializers.base;

import heroes.journey.components.utils.Utils;
import heroes.journey.entities.quests.Quest;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.actions.BaseActions;

public class Quests implements InitializerInterface {

    public static Quest delve;

    static {
        delve = Quest.builder()
            .name("Delve a dungeon")
            .onComplete((gs, e) -> Utils.addItem(gs, e, Items.ironSword, 1))
            .isComplete((gs, e) -> Utils.justCompletedAction(gs, e, BaseActions.delve))
            .build()
            .register();
    }

}
