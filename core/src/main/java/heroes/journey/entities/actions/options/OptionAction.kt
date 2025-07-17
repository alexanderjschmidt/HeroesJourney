package heroes.journey.entities.actions.options

import heroes.journey.entities.actions.Action
import heroes.journey.entities.actions.ActionContext
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.results.AIOnSelectNotFound
import heroes.journey.modlib.actions.results.ActionResult

abstract class OptionAction(
    id: String,
    requirementsMetFn: (ActionContext) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionContext) -> Unit = {},
    onSelectFn: (ActionContext) -> ActionResult,
    onSelectAIFn: (ActionContext) -> ActionResult = { AIOnSelectNotFound() },
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
