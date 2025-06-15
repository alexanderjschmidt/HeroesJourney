package heroes.journey.utils.input

import heroes.journey.entities.actions.Action
import heroes.journey.entities.actions.TeamActions
import heroes.journey.entities.actions.action
import heroes.journey.entities.actions.options.BooleanOptionAction
import heroes.journey.entities.actions.options.OptionAction
import heroes.journey.entities.actions.results.ActionListResult
import heroes.journey.registries.Registries

object Options {
    private val optionsList: MutableList<Action> = ArrayList(2)

    fun addOption(option: OptionAction) {
        option.setDisplay("")
        optionsList.add(option)
    }

    fun isTrue(optionAction: String?): Boolean {
        val action = Registries.ActionManager[optionAction]
        if (action is BooleanOptionAction) {
            return action.isTrue
        }
        return false
    }

    fun toggle(optionAction: String?) {
        val action = Registries.ActionManager[optionAction]
        if (action is BooleanOptionAction) {
            action.onSelect(null, false)
        }
    }

    init {
        val optionsAction = action {
            id = "options"
            name = "Options"
            description = ""
            isReturnsActionList = true
            onSelectFn = { input ->
                ActionListResult(optionsList)
            }
        }
        TeamActions.addTeamAction(optionsAction)
    }
}
