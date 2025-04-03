package heroes.journey.initializers.base;

import heroes.journey.entities.actions.options.BooleanOptionAction;
import heroes.journey.initializers.InitializerInterface;

public class LoadOptions implements InitializerInterface {

    public static final BooleanOptionAction debugOption, autoEndTurnOption;

    static {
        debugOption = new BooleanOptionAction("Debug", false);
        autoEndTurnOption = new BooleanOptionAction("Auto End Turn", false);
    }
}
