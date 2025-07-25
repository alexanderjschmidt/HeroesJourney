package heroes.journey.entities.actions.options

import heroes.journey.modlib.actions.*
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.mods.Registries
import heroes.journey.utils.input.Options

class BooleanOptionAction(
    id: String,
    requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    onHoverFn: (IActionContext) -> Unit = {},
    onSelectFn: (IActionContext) -> ActionResult,
    override var isTrue: Boolean = true,
    cost: IAttributes? = null
) : OptionAction(
    id,
    requirementsMetFn,
    onHoverFn,
    onSelectFn,
    isTrue,
    cost = cost
), IBooleanOptionAction {

    override fun onSelect(input: IActionContext): ActionResult {
        isTrue = !isTrue
        value = isTrue
        return NullResult()
    }

    override fun register(): BooleanOptionAction {
        Options.addOption(this)
        return Registries.ActionManager.register(this) as BooleanOptionAction
    }
}
