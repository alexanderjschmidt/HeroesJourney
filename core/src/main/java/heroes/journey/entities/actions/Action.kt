package heroes.journey.entities.actions

import heroes.journey.GameState
import heroes.journey.modlib.actions.ActionResult
import heroes.journey.modlib.actions.IAction
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.attributes.Attributes
import heroes.journey.modlib.attributes.Stat
import heroes.journey.modlib.registries.InfoProvider
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries

open class Action(
    id: String,
    override val isReturnsActionList: Boolean = false,
    override val requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    override val onHoverFn: (IActionContext) -> Unit = {},
    override val onSelectFn: (IActionContext) -> ActionResult,
    override val turnCooldown: Int = 0,
    override val factionCooldown: Boolean = false,
    override val cost: Attributes? = null,
    override val customInfoProviderFn: ((IActionContext) -> InfoProvider)? = null,
    override val requiredAllTags: List<Stat> = emptyList(),
    override val requiredAnyTags: List<Stat> = emptyList(),
    override val forbiddenTags: List<Stat> = emptyList()
) : Registrable(id), IAction {

    override fun getTitle(input: IActionContext): String {
        val customProvider = customInfoProviderFn?.invoke(input)
        if (customProvider != null) {
            return customProvider.getTitle(input)
        }
        return super.getTitle(input)
    }

    override fun getDescription(input: Map<String, String>): String {
        val actionContext = ActionContext(GameState.global(), null, false, input)
        val customProvider = customInfoProviderFn?.invoke(actionContext)
        if (customProvider != null) {
            return customProvider.getDescription(input)
        }
        return super.getDescription(input)
    }

    override fun register(): Action {
        return Registries.ActionManager.register(this) as Action
    }
}
