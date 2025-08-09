package heroes.journey.modlib.actions

import heroes.journey.modlib.attributes.Attributes
import heroes.journey.modlib.registries.Registries

class BooleanOptionAction(
    id: String,
    requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    onHoverFn: (IActionContext) -> Unit = {},
    onSelectFn: (IActionContext) -> ActionResult,
    var isTrue: Boolean,
    cost: Attributes? = null
) : OptionAction(
    id = id,
    requirementsMetFn = requirementsMetFn,
    onHoverFn = onHoverFn,
    onSelectFn = onSelectFn,
    value = isTrue,
    cost = cost
) {
    override fun register(): Action {
        Registries.ActionManager.register(this)
        return this
    }
}
