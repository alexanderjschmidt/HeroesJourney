package heroes.journey.entities.actions.options

import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.actions.IBooleanOptionAction
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.results.ActionResult
import heroes.journey.modlib.actions.results.NullResult
import heroes.journey.registries.Registries

class BooleanOptionAction(
    id: String,
    requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    onHoverFn: (IActionContext) -> Unit = {},
    onSelectFn: (IActionContext) -> ActionResult,
    override var isTrue: Boolean = true
) : OptionAction(
    id,
    requirementsMetFn,
    onHoverFn,
    onSelectFn,
    isTrue
), IBooleanOptionAction {
    
    override fun onSelect(input: IActionContext): ActionResult {
        isTrue = !isTrue
        value = isTrue
        return NullResult()
    }

    override fun register(): BooleanOptionAction {
        return Registries.ActionManager.register(this) as BooleanOptionAction
    }
}
