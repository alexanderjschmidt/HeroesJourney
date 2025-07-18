package heroes.journey.modlib.actions

import heroes.journey.modlib.actions.results.ActionResult
import heroes.journey.modlib.registries.IRegistrable

interface IAction : IRegistrable {
    val isReturnsActionList: Boolean
    val requirementsMetFn: (IActionContext) -> ShowAction
    val onHoverFn: (IActionContext) -> Unit
    val onSelectFn: (IActionContext) -> ActionResult
    val inputDisplayNameFn: ((IActionContext) -> String)?
    val turnCooldown: Int
    val factionCooldown: Boolean
    override fun register(): IAction
}

interface IOptionAction : IAction {
    var value: Any
}

interface IBooleanOptionAction : IOptionAction {
    var isTrue: Boolean
}

interface IActionBuilder {
    var id: String
    var isReturnsActionList: Boolean
    var turnCooldown: Int
    var factionCooldown: Boolean
    var requirementsMetFn: (IActionContext) -> ShowAction
    var onHoverFn: (IActionContext) -> Unit
    var onSelectFn: (IActionContext) -> heroes.journey.modlib.actions.results.ActionResult
    var inputDisplayNameFn: ((IActionContext) -> String)?
}

interface IOptionActionBuilder : IActionBuilder {
    // OptionAction-specific properties can be added here if needed
}

interface IBooleanOptionActionBuilder : IOptionActionBuilder {
    var isTrue: Boolean
}

interface ITargetActionBuilder<I> {
    var id: String
    var getTargets: (IActionContext) -> List<I>
    var targetAction: String
    var requirementsMetFn: (IActionContext) -> ShowAction
    var onHoverFn: (IActionContext) -> Unit
    var inputDisplayNameFn: ((IActionContext) -> String)?
}

interface ActionDSL {
    fun action(init: IActionBuilder.() -> Unit): IAction
    fun optionAction(init: IOptionActionBuilder.() -> Unit): IOptionAction
    fun booleanOptionAction(init: IBooleanOptionActionBuilder.() -> Unit): IBooleanOptionAction
    fun <I> targetAction(init: ITargetActionBuilder<I>.() -> Unit): IAction
}

object ActionDSLProvider : ActionDSL {
    lateinit var instance: ActionDSL
    override fun action(init: IActionBuilder.() -> Unit) = instance.action(init)
    override fun optionAction(init: IOptionActionBuilder.() -> Unit) = instance.optionAction(init)
    override fun booleanOptionAction(init: IBooleanOptionActionBuilder.() -> Unit) =
        instance.booleanOptionAction(init)

    override fun <I> targetAction(init: ITargetActionBuilder<I>.() -> Unit) = instance.targetAction(init)
}

fun action(init: IActionBuilder.() -> Unit) = ActionDSLProvider.action(init)
fun optionAction(init: IOptionActionBuilder.() -> Unit) = ActionDSLProvider.optionAction(init)
fun booleanOptionAction(init: IBooleanOptionActionBuilder.() -> Unit) =
    ActionDSLProvider.booleanOptionAction(init)

fun <I> targetAction(init: ITargetActionBuilder<I>.() -> Unit) = ActionDSLProvider.targetAction(init)
