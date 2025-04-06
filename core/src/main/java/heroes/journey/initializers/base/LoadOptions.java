package heroes.journey.initializers.base;

import heroes.journey.entities.actions.options.BooleanOptionAction;
import heroes.journey.initializers.InitializerInterface;

public class LoadOptions implements InitializerInterface {

    public static final BooleanOptionAction debugOption, autoEndTurnOption;

    static {
        debugOption = new BooleanOptionAction.Builder().defaultToggle(false).name("Debug").build();
        autoEndTurnOption = new BooleanOptionAction.Builder().defaultToggle(false)
            .name("Auto End Turn")
            .build();
    }
}
