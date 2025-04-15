package heroes.journey.initializers.base;

import heroes.journey.entities.actions.options.BooleanOptionAction;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.input.Options;

public class LoadOptions implements InitializerInterface {

    public static final BooleanOptionAction debugOption, autoEndTurnOption;

    static {
        debugOption = BooleanOptionAction.builder().toggle(false).name("Debug").build();
        Options.addOption(debugOption);
        autoEndTurnOption = BooleanOptionAction.builder().toggle(false).name("Auto End Turn").build();
        Options.addOption(autoEndTurnOption);
    }
}
