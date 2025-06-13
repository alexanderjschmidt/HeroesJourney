package heroes.journey.initializers.base.actions;

import heroes.journey.entities.actions.options.BooleanOptionAction;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.input.Options;

public class LoadOptions implements InitializerInterface {

    public static BooleanOptionAction debugOption, autoEndTurnOption, backgroundMusic;

    @Override
    public void init() {
        debugOption = new BooleanOptionAction("Debug", false).register();
        Options.addOption(debugOption);
        
        autoEndTurnOption = new BooleanOptionAction("Auto End Turn", false).register();
        Options.addOption(autoEndTurnOption);
        
        backgroundMusic = new BooleanOptionAction("Music", false).register();
        Options.addOption(backgroundMusic);
    }
}
