package heroes.journey.utils.input

import heroes.journey.GameState
import heroes.journey.entities.actions.ActionContext
import heroes.journey.entities.actions.options.BooleanOptionAction
import heroes.journey.modlib.actions.ActionEntry
import heroes.journey.modlib.actions.IAction
import heroes.journey.registries.Registries

object Options {
    val optionsList: MutableList<ActionEntry> = ArrayList(2)

    fun addOption(option: IAction) {
        optionsList.add(ActionEntry(option.id, emptyMap()))
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
            action.onSelect(
                ActionContext(
                    GameState.global(), false
                )
            )
        }
    }
}
