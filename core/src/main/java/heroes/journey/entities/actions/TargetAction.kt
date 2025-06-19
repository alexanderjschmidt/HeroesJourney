package heroes.journey.entities.actions

import heroes.journey.entities.actions.results.AIOnSelectNotFound
import heroes.journey.entities.actions.results.ActionListResult
import heroes.journey.entities.actions.results.ActionResult
import heroes.journey.entities.actions.results.NullResult
import heroes.journey.registries.Registries

class TargetAction<I>(
    id: String, name: String?,
    description: String = "",
    cost: Cost = Cost(),
    requirementsMetFn: (ActionInput) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionInput) -> Unit = {},
    inputDisplayNameFn: ((String) -> String)? = null,
    val getTargets: (ActionInput) -> List<I>,
    private val targetAction: String
) : Action(
    id,
    name,
    description,
    true,
    cost,
    requirementsMetFn,
    onHoverFn,
    { NullResult() },
    { AIOnSelectNotFound() },
    inputDisplayNameFn
) {
    override fun requirementsMet(input: ActionInput): ShowAction {
        val options = getTargets(input)
        if (options.isEmpty()) return ShowAction.GRAYED
        return super.requirementsMet(input)
    }

    override fun onSelect(input: ActionInput, ai: Boolean): ActionResult {
        val actionOptions: List<ActionEntry> = getTargets(input).map { option ->
            ActionEntry(targetAction, option.toString())
        }
        return ActionListResult(actionOptions)
    }

    override fun register(): TargetAction<I> {
        Registries.ActionManager.register(this)
        return this
    }
}
