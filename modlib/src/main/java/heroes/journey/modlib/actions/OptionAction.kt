package heroes.journey.modlib.actions

import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.Attributes
import heroes.journey.modlib.attributes.Stat
import heroes.journey.modlib.registries.Registries

abstract class OptionAction(
    id: String,
    requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    onHoverFn: (IActionContext) -> Unit = {},
    onSelectFn: (IActionContext) -> ActionResult,
    open var value: Any,
    cost: Attributes? = null,
    tags: List<Stat> = emptyList()
) : Action(
    id = id,
    requirementsMetFn = requirementsMetFn,
    onHoverFn = onHoverFn,
    onSelectFn = onSelectFn,
    cost = cost,
    tags = tags.ifEmpty { listOfNotNull(Registries.StatManager[Ids.GROUP_OPTION]) }
)
