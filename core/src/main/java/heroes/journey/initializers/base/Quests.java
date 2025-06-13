package heroes.journey.initializers.base;

import heroes.journey.entities.Quest;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.actions.DelveAction;
import heroes.journey.initializers.utils.Utils;

public class Quests implements InitializerInterface {

    public static Quest delve;

    @Override
    public void init() {
        delve = new Quest("Delve a dungeon", (input) -> Utils.addItem(input, Items.ironSword, 1), (input) -> Utils.justCompletedAction(input.getGameState(), input.getEntityId(),
            DelveAction.delve), 10);
    }

}
