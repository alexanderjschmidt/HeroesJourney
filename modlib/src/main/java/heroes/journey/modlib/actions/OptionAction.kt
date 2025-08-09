package heroes.journey.modlib.actions

import heroes.journey.modlib.attributes.Attributes

abstract class OptionAction(
    id: String,
    requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    onHoverFn: (IActionContext) -> Unit = {},
    onSelectFn: (IActionContext) -> ActionResult,
    var value: Any,
    cost: Attributes? = null
) : Action(
    id = id,
    requirementsMetFn = requirementsMetFn,
    onHoverFn = onHoverFn,
    onSelectFn = onSelectFn,
    cost = cost
)
