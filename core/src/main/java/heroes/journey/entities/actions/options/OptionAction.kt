package heroes.journey.entities.actions.options

import heroes.journey.entities.actions.Action
import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.actions.ShowAction
import heroes.journey.entities.actions.results.AIOnSelectNotFound
import heroes.journey.entities.actions.results.ActionResult

abstract class OptionAction(
    id: String,
    name: String?,
    description: String = "",
    requirementsMetFn: (ActionInput) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionInput) -> Unit = {},
    onSelectFn: (ActionInput) -> ActionResult,
    onSelectAIFn: (ActionInput) -> ActionResult = { AIOnSelectNotFound() },
) : Action(
    id,
    name,
    description,
    false,
    requirementsMetFn,
    onHoverFn,
    onSelectFn,
    onSelectAIFn
) {
    private var display: String = ""

    override fun getName(): String {
        return display
    }

    fun setDisplay(value: String) {
        this.display = super.toString() + ": " + value
    }
}
