import heroes.journey.entities.actions.booleanOptionAction
import heroes.journey.mods.gameMod
import heroes.journey.utils.input.Options

gameMod("Options Actions", 0) {
    // Debug Option
    booleanOptionAction {
        id = "debug"
        name = "Debug"
        isTrue = false
    }.register().also { Options.addOption(it) }

    // Background Music Option
    booleanOptionAction {
        id = "music"
        name = "Music"
        isTrue = false
    }.register().also { Options.addOption(it) }
} 