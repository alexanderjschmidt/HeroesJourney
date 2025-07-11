import heroes.journey.entities.actions.booleanOptionAction
import heroes.journey.utils.input.Options

// Options Actions - included by basegame mod

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