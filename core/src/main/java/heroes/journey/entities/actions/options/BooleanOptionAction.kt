package heroes.journey.entities.actions.options

import heroes.journey.modlib.actions.ActionResult
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.actions.IBooleanOptionAction
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.attributes.Attributes
import heroes.journey.mods.Registries
import heroes.journey.utils.input.Options

class BooleanOptionAction(
    id: String,
    requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    onHoverFn: (IActionContext) -> Unit = {},
    onSelectFn: (IActionContext) -> ActionResult,
    override var isTrue: Boolean = true,
    cost: Attributes? = null
) : OptionAction(
    id,
    requirementsMetFn,
    onHoverFn,
    onSelectFn,
    isTrue,
    cost = cost
), IBooleanOptionAction {

    override fun register(): BooleanOptionAction {
        Options.addOption(this)
        return Registries.ActionManager.register(this) as BooleanOptionAction
    }
}
