package heroes.journey.mods.misc

import heroes.journey.entities.actions.Action
import heroes.journey.entities.actions.ActionContext
import heroes.journey.entities.actions.options.BooleanOptionAction
import heroes.journey.entities.actions.options.OptionAction
import heroes.journey.modlib.actions.*
import heroes.journey.modlib.attributes.Attributes
import heroes.journey.modlib.registries.InfoProvider

// --- Builder Classes ---
open class ActionBuilder : IActionBuilder {
    override var id: String = ""
    override var isReturnsActionList: Boolean = false
    override var requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES }
    override var onHoverFn: (IActionContext) -> Unit = {}
    override var onSelectFn: (IActionContext) -> ActionResult = { NullResult() }
    override var turnCooldown: Int = 0
    override var factionCooldown: Boolean = false
    override var cost: Attributes? = null
    override var customInfoProviderFn: ((IActionContext) -> InfoProvider)? = null

    open fun build(): Action = Action(
        id = id,
        isReturnsActionList = isReturnsActionList,
        requirementsMetFn = { ctx -> requirementsMetFn(ctx) },
        onHoverFn = { ctx -> onHoverFn(ctx) },
        onSelectFn = { ctx -> onSelectFn(ctx) },
        turnCooldown = turnCooldown,
        factionCooldown = factionCooldown,
        cost = cost,
        customInfoProviderFn = customInfoProviderFn
    )
}

open class OptionActionBuilder : ActionBuilder(), IOptionActionBuilder {
    // OptionAction-specific properties can be added here if needed
    override var isReturnsActionList: Boolean
        get() = false
        set(_) {} // Ignore any attempts to set the value

    override var cost: Attributes?
        get() = null
        set(_) {} // Ignore any attempts to set the value

    override fun build(): OptionAction = object : OptionAction(
        id = id,
        requirementsMetFn = { ctx -> requirementsMetFn(ctx) },
        onHoverFn = { ctx -> onHoverFn(ctx) },
        onSelectFn = { ctx -> onSelectFn(ctx) },
        1,
        cost = null
    ) {}
}

class BooleanOptionActionBuilder : OptionActionBuilder(), IBooleanOptionActionBuilder {
    override var isTrue: Boolean = true
    override var isReturnsActionList: Boolean
        get() = false
        set(_) {} // Ignore any attempts to set the value

    override var cost: Attributes?
        get() = null
        set(_) {} // Ignore any attempts to set the value

    override fun build(): BooleanOptionAction = BooleanOptionAction(
        id = id,
        requirementsMetFn = { ctx -> requirementsMetFn(ctx) },
        onHoverFn = { ctx -> onHoverFn(ctx) },
        onSelectFn = { ctx -> onSelectFn(ctx) },
        isTrue = isTrue,
        cost = null
    )
}

class TargetActionBuilder<I> : ITargetActionBuilder<I> {
    override var id: String = ""
    override var getTargets: (IActionContext) -> List<I> = { emptyList() }
    override var targetAction: String = ""
    override var requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES }
    override var onHoverFn: (IActionContext) -> Unit = {}
    override var cost: Attributes? = null
    override var customInfoProviderFn: ((IActionContext) -> InfoProvider)? = null

    fun build(): Action {
        return Action(
            id = id,
            isReturnsActionList = true,
            requirementsMetFn = { input ->
                val targets = getTargets(input)
                val targetShowAction = if (targets.isEmpty()) ShowAction.GRAYED else ShowAction.YES
                requirementsMetFn(input).and(targetShowAction)
            },
            onHoverFn = { ctx -> onHoverFn(ctx) },
            onSelectFn = { input ->
                val actionOptions = getTargets(input).map { option ->
                    val copyInput = (input as? ActionContext)?.getHashMapCopy() ?: hashMapOf<String, String>()
                    copyInput["target"] = option.toString()
                    ActionEntry(targetAction, copyInput)
                }
                ActionListResult(actionOptions)
            },
            cost = cost,
            customInfoProviderFn = customInfoProviderFn
        )
    }
}

// --- ActionDSL Implementation ---
class ActionDSLImpl : ActionDSL {
    override fun action(init: IActionBuilder.() -> Unit): Action {
        val builder = ActionBuilder()
        builder.apply(init)
        return builder.build()
    }

    override fun optionAction(init: IOptionActionBuilder.() -> Unit): OptionAction {
        val builder = OptionActionBuilder()
        builder.apply(init)
        return builder.build()
    }

    override fun booleanOptionAction(init: IBooleanOptionActionBuilder.() -> Unit): BooleanOptionAction {
        val builder = BooleanOptionActionBuilder()
        builder.apply(init)
        return builder.build()
    }

    override fun <I> targetAction(init: ITargetActionBuilder<I>.() -> Unit): Action {
        val builder = TargetActionBuilder<I>()
        builder.apply(init)
        return builder.build()
    }
}
