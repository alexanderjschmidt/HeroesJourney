import heroes.journey.entities.actions.TeamActions
import heroes.journey.entities.actions.action
import heroes.journey.entities.actions.booleanOptionAction
import heroes.journey.modlib.actions.results.ActionListResult
import heroes.journey.utils.input.Options

// Options Actions - included by basegame mod

// Debug Option
booleanOptionAction {
    id = "debug"
    isTrue = false
}.register().also { Options.addOption(it) }

// Background Music Option
booleanOptionAction {
    id = "music"
    isTrue = false
}.register().also { Options.addOption(it) }

action {
    id = "options"
    isReturnsActionList = true
    onSelectFn = { input ->
        ActionListResult(Options.optionsList)
    }
}.register().also { TeamActions.addTeamAction(it) }
