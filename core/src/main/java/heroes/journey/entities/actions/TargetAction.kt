package heroes.journey.entities.actions

import heroes.journey.entities.actions.inputs.ActionInput
import heroes.journey.entities.actions.inputs.TargetInput
import heroes.journey.entities.actions.results.AIOnSelectNotFound
import heroes.journey.entities.actions.results.ActionListResult
import heroes.journey.entities.actions.results.ActionResult
import heroes.journey.registries.Registries

abstract class TargetAction<I>(id: String, name: String?, description: String, cost: Cost?) :
    Action(id, name, description, true, cost) {
    abstract fun getTargets(input: ActionInput?): List<I>

    open fun getTargetDisplayName(input: TargetInput<I>): String {
        return input.input.toString()
    }

    override fun requirementsMet(input: ActionInput): ShowAction? {
        val options = getTargets(input)
        if (options.isEmpty()) return ShowAction.GRAYED
        return super.requirementsMet(input)
    }

    override fun internalOnSelect(input: ActionInput): ActionResult {
        val actionOptions: MutableList<Action> = ArrayList()
        val options = getTargets(input)
        for (option in options) {
            val targetInput = TargetInput(input.gameState, input.entityId, option)
            actionOptions.add(getAction(targetInput))
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
        return object : Action(id + "," + option.toString(), displayName, "", false, null) {
            override fun internalRequirementsMet(input: ActionInput?): ShowAction {
                return requirementsMetTarget(targetInput)
            }

            override fun internalOnHover(input: ActionInput?) {
                onHoverTarget(targetInput)
            }

            override fun internalOnSelect(input: ActionInput): ActionResult? {
                return onSelectTarget(targetInput)
            }

            override fun internalOnSelectAI(input: ActionInput?): ActionResult {
                return onSelectAITarget(targetInput)
            }
        }
    }

    protected fun requirementsMetTarget(targetInput: TargetInput<I>?): ShowAction {
        return ShowAction.YES
    }

    protected open fun onSelectAITarget(targetInput: TargetInput<I>?): ActionResult {
        return AIOnSelectNotFound()
    }

    protected abstract fun onSelectTarget(targetInput: TargetInput<I>?): ActionResult?

    protected open fun onHoverTarget(targetInput: TargetInput<I>?) {
    }

    override fun register(): TargetAction<I> {
        Registries.ActionManager.register(this)
        return this
    }
}
