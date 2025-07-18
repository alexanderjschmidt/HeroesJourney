package heroes.journey.entities.actions.options

import heroes.journey.entities.actions.Action
import heroes.journey.modlib.actions.ActionResult
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.actions.IOptionAction
import heroes.journey.modlib.actions.ShowAction

abstract class OptionAction(
    id: String,
    requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    onHoverFn: (IActionContext) -> Unit = {},
    onSelectFn: (IActionContext) -> ActionResult,
    override var value: Any
) : Action(
    id,
    false,
    requirementsMetFn,
    onHoverFn,
    onSelectFn
), IOptionAction {
    override fun getName(): String {
        return super.getName() + ": " + value
    }
}
