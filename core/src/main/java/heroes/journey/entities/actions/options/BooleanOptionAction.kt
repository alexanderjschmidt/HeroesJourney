package heroes.journey.entities.actions.options

import heroes.journey.entities.actions.ActionInput
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.results.AIOnSelectNotFound
import heroes.journey.modlib.actions.results.ActionResult
import heroes.journey.modlib.actions.results.NullResult
import heroes.journey.registries.Registries

class BooleanOptionAction(
    id: String,
    requirementsMetFn: (ActionInput) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionInput) -> Unit = {},
    onSelectFn: (ActionInput) -> ActionResult,
    onSelectAIFn: (ActionInput) -> ActionResult = { AIOnSelectNotFound() },
    var isTrue: Boolean = true
) : OptionAction(
    id,
    requirementsMetFn,
    onHoverFn,
    onSelectFn,
    onSelectAIFn,
    isTrue
) {
    override fun onSelect(input: ActionInput, ai: Boolean): ActionResult {
        isTrue = !isTrue
        value = isTrue
        return NullResult()
    }

    override fun register(): BooleanOptionAction {
        return Registries.ActionManager.register(this) as BooleanOptionAction
    }
}
