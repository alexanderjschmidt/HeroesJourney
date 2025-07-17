package heroes.journey.entities.actions.options

import heroes.journey.entities.actions.ActionContext
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.results.ActionResult
import heroes.journey.modlib.actions.results.NullResult
import heroes.journey.registries.Registries

class BooleanOptionAction(
    id: String,
    requirementsMetFn: (ActionContext) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionContext) -> Unit = {},
    onSelectFn: (ActionContext) -> ActionResult,
    var isTrue: Boolean = true
) : OptionAction(
    id,
    requirementsMetFn,
    onHoverFn,
    onSelectFn,
    isTrue
) {
    override fun onSelect(input: ActionContext): ActionResult {
        isTrue = !isTrue
        value = isTrue
        return NullResult()
    }

    override fun register(): BooleanOptionAction {
        return Registries.ActionManager.register(this) as BooleanOptionAction
    }
}
