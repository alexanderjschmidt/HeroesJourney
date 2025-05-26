package heroes.journey.initializers.base.actions;

import heroes.journey.entities.actions.options.BooleanOptionAction;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.input.Options;

public class LoadOptions implements InitializerInterface {

    public static BooleanOptionAction debugOption, autoEndTurnOption, backgroundMusic;

    @Override
    public void init() {
        debugOption = BooleanOptionAction.builder().toggle(false).name("Debug").build().register();
        Options.addOption(debugOption);
        autoEndTurnOption = BooleanOptionAction.builder()
            .toggle(false)
            .name("Auto End Turn")
            .build()
            .register();
        Options.addOption(autoEndTurnOption);
        backgroundMusic = BooleanOptionAction.builder()
            .toggle(false)
            .name("Music")
            .build()
            .register();
        Options.addOption(backgroundMusic);
    }
}
