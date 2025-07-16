package heroes.journey.entities.actions.options

import heroes.journey.entities.actions.Action
import heroes.journey.entities.actions.ActionInput
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.results.AIOnSelectNotFound
import heroes.journey.modlib.actions.results.ActionResult

abstract class OptionAction(
    id: String,
    requirementsMetFn: (ActionInput) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionInput) -> Unit = {},
    onSelectFn: (ActionInput) -> ActionResult,
    onSelectAIFn: (ActionInput) -> ActionResult = { AIOnSelectNotFound() },
    open var value: Any
) : Action(
    id,
    false,
    requirementsMetFn,
    onHoverFn,
    onSelectFn,
    onSelectAIFn
) {
    override fun getName(): String {
        return super.getName() + ": " + value
    }

}
