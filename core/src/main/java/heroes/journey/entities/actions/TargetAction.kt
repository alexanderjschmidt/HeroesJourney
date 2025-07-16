package heroes.journey.entities.actions

import heroes.journey.modlib.actions.ActionEntry
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.results.AIOnSelectNotFound
import heroes.journey.modlib.actions.results.ActionListResult
import heroes.journey.modlib.actions.results.ActionResult
import heroes.journey.modlib.actions.results.NullResult
import heroes.journey.registries.Registries

class TargetAction<I>(
    id: String,
    requirementsMetFn: (ActionInput) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionInput) -> Unit = {},
    inputDisplayNameFn: ((Map<String, String>) -> String)? = null,
    val getTargets: (ActionInput) -> List<I>,
    private val targetAction: String
) : Action(
    id,
    true,
    requirementsMetFn,
    onHoverFn,
    { NullResult() },
    { AIOnSelectNotFound() },
    inputDisplayNameFn
) {
    override fun requirementsMet(input: ActionInput): ShowAction {
        var targetShowAction: ShowAction = ShowAction.YES
        if (getTargets(input).isEmpty()) targetShowAction = ShowAction.GRAYED
        return super.requirementsMet(input).and(targetShowAction)
    }

    override fun onSelect(input: ActionInput, ai: Boolean): ActionResult {
        val actionOptions: List<ActionEntry> = getTargets(input).map { option ->
            val copyInput: HashMap<String, String> = input.getHashMapCopy()
            copyInput["target"] = option.toString()
            ActionEntry(targetAction, copyInput)
        }
        return ActionListResult(actionOptions)
    }

    override fun register(): TargetAction<I> {
        Registries.ActionManager.register(this)
        return this
    }
}
