package heroes.journey.initializers.base.actions;

import heroes.journey.entities.actions.options.BooleanOptionAction;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.input.Options;

public class LoadOptions implements InitializerInterface {

    public static BooleanOptionAction debugOption, autoEndTurnOption, backgroundMusic;

    @Override
    public void init() {
        debugOption = new BooleanOptionAction("debug", "Debug", false).register();
        Options.addOption(debugOption);

        autoEndTurnOption = new BooleanOptionAction("auto_end_turn", "Auto End Turn", false).register();
        Options.addOption(autoEndTurnOption);

        backgroundMusic = new BooleanOptionAction("music", "Music", false).register();
        Options.addOption(backgroundMusic);
    }
}
