package heroes.journey.entities.actions.options

import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.actions.ShowAction
import heroes.journey.entities.actions.results.AIOnSelectNotFound
import heroes.journey.entities.actions.results.ActionResult
import heroes.journey.registries.Registries

class BooleanOptionAction(
    id: String,
    name: String?,
    description: String = "",
    requirementsMetFn: (ActionInput) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionInput) -> Unit = {},
    onSelectFn: (ActionInput) -> ActionResult,
    onSelectAIFn: (ActionInput) -> ActionResult = { AIOnSelectNotFound() },
    var isTrue: Boolean = true,
) : OptionAction(
    id,
    name,
    description,
    requirementsMetFn,
    onHoverFn,
    onSelectFn,
    onSelectAIFn
) {
    override fun onSelect(input: ActionInput, ai: Boolean): ActionResult? {
        isTrue = !isTrue
        setDisplay(isTrue.toString() + "")
        return null
    }

    override fun register(): BooleanOptionAction {
        return Registries.ActionManager.register(this) as BooleanOptionAction
    }
}
