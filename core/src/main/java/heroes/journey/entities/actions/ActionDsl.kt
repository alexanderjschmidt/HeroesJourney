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
    var requirementsMetFn: (ActionInput) -> ShowAction = { ShowAction.YES }
    var onHoverFn: (ActionInput) -> Unit = {}
    var onSelectFn: (ActionInput) -> ActionResult = { AIOnSelectNotFound() }
    var onSelectAIFn: (ActionInput) -> ActionResult = { AIOnSelectNotFound() }
    var inputDisplayNameFn: ((Map<String, String>) -> String)? = null
    var turnCooldown: Int = 0
    var factionCooldown: Boolean = false

    open fun build(): Action = Action(
        id = id,
        isReturnsActionList = isReturnsActionList,
        requirementsMetFn = requirementsMetFn,
        onHoverFn = onHoverFn,
        onSelectFn = onSelectFn,
        onSelectAIFn = onSelectAIFn,
        inputDisplayNameFn = inputDisplayNameFn,
        turnCooldown = turnCooldown,
        factionCooldown = factionCooldown
    )
}

// TargetAction DSL
class TargetActionBuilder<I> : ActionBuilder() {
    var getTargets: (ActionInput) -> List<I> = { emptyList() }
    var targetAction: String = ""

    override fun build(): TargetAction<I> = TargetAction(
        id = id,
        requirementsMetFn = requirementsMetFn,
        onHoverFn = onHoverFn,
        inputDisplayNameFn = inputDisplayNameFn,
        getTargets = getTargets,
        targetAction = targetAction,
    )
}

// OptionAction DSL
open class OptionActionBuilder : ActionBuilder() {
    var display: String = ""

    // Override to make isReturnsActionList inaccessible
    override var isReturnsActionList: Boolean
        get() = false
        set(_) {} // Ignore any attempts to set the value

    override fun build(): OptionAction = object : OptionAction(
        id = id,
        requirementsMetFn = requirementsMetFn,
        onHoverFn = onHoverFn,
        onSelectFn = onSelectFn,
        onSelectAIFn = onSelectAIFn
    ) {
        init {
            setDisplay(display)
        }
    }
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
        onSelectAIFn = onSelectAIFn,
        isTrue = isTrue
    )
}

// DSL Functions
fun action(init: ActionBuilder.() -> Unit): Action {
    return ActionBuilder().apply(init).build()
}

fun <I> targetAction(init: TargetActionBuilder<I>.() -> Unit): TargetAction<I> {
    return TargetActionBuilder<I>().apply(init).build()
}

fun optionAction(init: OptionActionBuilder.() -> Unit): OptionAction {
    return OptionActionBuilder().apply(init).build()
}

fun booleanOptionAction(init: BooleanOptionActionBuilder.() -> Unit): BooleanOptionAction {
    return BooleanOptionActionBuilder().apply(init).build()
}
