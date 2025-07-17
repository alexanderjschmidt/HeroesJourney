package heroes.journey.entities.actions

import heroes.journey.entities.actions.options.BooleanOptionAction
import heroes.journey.entities.actions.options.OptionAction
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.results.AIOnSelectNotFound
import heroes.journey.modlib.actions.results.ActionResult

// Base Action DSL
open class ActionBuilder {
    var id: String = ""
    open var isReturnsActionList: Boolean = false
    var requirementsMetFn: (ActionContext) -> ShowAction = { ShowAction.YES }
    var onHoverFn: (ActionContext) -> Unit = {}
    var onSelectFn: (ActionContext) -> ActionResult = { AIOnSelectNotFound() }
    var onSelectAIFn: (ActionContext) -> ActionResult = { AIOnSelectNotFound() }
    var inputDisplayNameFn: ((Map<String, String>) -> String)? = null
    var turnCooldown: Int = 0
    var factionCooldown: Boolean = false

    open fun build(): Action = Action(
        id = id,
        isReturnsActionList = isReturnsActionList,
        requirementsMetFn = requirementsMetFn,
        onHoverFn = onHoverFn,
        onSelectFn = onSelectFn,
        inputDisplayNameFn = inputDisplayNameFn,
        turnCooldown = turnCooldown,
        factionCooldown = factionCooldown
    )
}

// OptionAction DSL
open class OptionActionBuilder : ActionBuilder() {

    // Override to make isReturnsActionList inaccessible
    override var isReturnsActionList: Boolean
        get() = false
        set(_) {} // Ignore any attempts to set the value

    override fun build(): OptionAction = object : OptionAction(
        id = id,
        requirementsMetFn = requirementsMetFn,
        onHoverFn = onHoverFn,
        onSelectFn = onSelectFn,
        1
    ) {}
}

// BooleanOptionAction DSL
class BooleanOptionActionBuilder : OptionActionBuilder() {
    var isTrue: Boolean = true

    // Override to make isReturnsActionList inaccessible
    override var isReturnsActionList: Boolean
        get() = false
        set(_) {} // Ignore any attempts to set the value

    override fun build(): BooleanOptionAction = BooleanOptionAction(
        id = id,
        requirementsMetFn = requirementsMetFn,
        onHoverFn = onHoverFn,
        onSelectFn = onSelectFn,
        isTrue = isTrue
    )
}

// DSL Functions
fun action(init: ActionBuilder.() -> Unit): Action {
    return ActionBuilder().apply(init).build()
}

/**
 * Builder for targetAction DSL, which creates a normal Action with TargetAction-like behavior.
 */
class TargetActionBuilder<I> {
    var id: String = ""
    var getTargets: (ActionContext) -> List<I> = { emptyList() }
    var targetAction: String = ""
    var requirementsMetFn: (ActionContext) -> ShowAction = { ShowAction.YES }
    var onHoverFn: (ActionContext) -> Unit = {}
    var inputDisplayNameFn: ((Map<String, String>) -> String)? = null

    fun build(): Action {
        return Action(
            id = id,
            isReturnsActionList = true,
            requirementsMetFn = { input ->
                val targets = getTargets(input)
                val targetShowAction = if (targets.isEmpty()) ShowAction.GRAYED else ShowAction.YES
                requirementsMetFn(input).and(targetShowAction)
            },
            onHoverFn = onHoverFn,
            onSelectFn = { input ->
                val actionOptions = getTargets(input).map { option ->
                    val copyInput = input.getHashMapCopy()
                    copyInput["target"] = option.toString()
                    heroes.journey.modlib.actions.ActionEntry(targetAction, copyInput)
                }
                heroes.journey.modlib.actions.results.ActionListResult(actionOptions)
            },
            inputDisplayNameFn = inputDisplayNameFn
        )
    }
}

/**
 * DSL function for building a TargetAction-like Action using a builder pattern.
 */
fun <I> targetAction(init: TargetActionBuilder<I>.() -> Unit): Action {
    return TargetActionBuilder<I>().apply(init).build()
}

fun optionAction(init: OptionActionBuilder.() -> Unit): OptionAction {
    return OptionActionBuilder().apply(init).build()
}

fun booleanOptionAction(init: BooleanOptionActionBuilder.() -> Unit): BooleanOptionAction {
    return BooleanOptionActionBuilder().apply(init).build()
}
