package heroes.journey.entities.actions

import heroes.journey.entities.actions.inputs.ActionInput
import heroes.journey.entities.actions.inputs.TargetInput
import heroes.journey.entities.actions.results.AIOnSelectNotFound
import heroes.journey.entities.actions.results.ActionListResult
import heroes.journey.entities.actions.results.ActionResult
import heroes.journey.entities.actions.results.NullResult
import heroes.journey.registries.Registries

abstract class TargetAction<I>(
    id: String, name: String?,
    description: String = "",
    isReturnsActionList: Boolean = false,
    cost: Cost = Cost(),
    requirementsMetFn: (ActionInput?) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionInput?) -> Unit = {},
    val getTargets: (ActionInput) -> List<I>,
    val getTargetDisplayName: (TargetInput<I>) -> String = { input -> input.input.toString() },
    val onSelectTargetFn: (TargetInput<I>) -> ActionResult,
    val onSelectAITargetFn: (TargetInput<I>) -> ActionResult = { AIOnSelectNotFound() },
    val onHoverTargetFn: (TargetInput<I>) -> Unit = {},
    val requirementsMetTargetFn: (TargetInput<I>) -> ShowAction = { ShowAction.YES }
) : Action(
    id,
    name,
    description,
    isReturnsActionList,
    cost,
    requirementsMetFn,
    onHoverFn,
    { NullResult() },
    { AIOnSelectNotFound() }
) {
    override fun requirementsMet(input: ActionInput): ShowAction {
        val options = getTargets(input)
        if (options.isEmpty()) return ShowAction.GRAYED
        return super.requirementsMet(input)
    }

    override fun onSelect(input: ActionInput?, ai: Boolean): ActionResult? {
        if (input == null) return null
        val actionOptions = getTargets(input).map { option ->
            val targetInput = TargetInput(input.gameState, input.entityId, option)
            getAction(targetInput)
        }
        return ActionListResult(actionOptions)
    }

    fun getActionFromSelected(inputBase: ActionInput, selectedOption: String): Action {
        val options = getTargets(inputBase)
        for (option in options) {
            if (option.toString() == selectedOption) {
                val targetInput = TargetInput(
                    inputBase.gameState, inputBase.entityId,
                    option
                )
                cost.onUse(targetInput)
                return getAction(targetInput)
            }
        }
        throw RuntimeException("Selected Target option not found $selectedOption")
    }

    private fun getAction(targetInput: TargetInput<I>): Action {
        val displayName = getTargetDisplayName(targetInput)
        val option = targetInput.input
        return Action(
            "$id,$option", displayName, "", false, cost,
            { requirementsMetTargetFn(targetInput) },
            { onHoverTargetFn(targetInput) },
            { onSelectTargetFn(targetInput) },
            { onSelectAITargetFn(targetInput) }
        )
    }

    override fun register(): TargetAction<I> {
        Registries.ActionManager.register(this)
        return this
    }
}
