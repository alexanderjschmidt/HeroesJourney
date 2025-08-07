package heroes.journey.entities.actions

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.GameState
import heroes.journey.components.PossibleActionsComponent
import heroes.journey.components.StatsComponent
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.actions.ActionResult
import heroes.journey.modlib.actions.IAction
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.Operation
import heroes.journey.modlib.registries.InfoProvider
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.infoproviders.UIInfoProvider
import java.util.*

open class Action(
    id: String,
    override val isReturnsActionList: Boolean = false,
    override val requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    override val onHoverFn: (IActionContext) -> Unit = {},
    override val onSelectFn: (IActionContext) -> ActionResult,
    override val turnCooldown: Int = 0,
    override val factionCooldown: Boolean = false,
    override val cost: IAttributes? = null,
    override val customInfoProviderFn: ((IActionContext) -> InfoProvider)? = null
) : Registrable(id), UIInfoProvider, IAction {

    fun requirementsMet(input: IActionContext): ShowAction {
        val ctx = input as? ActionContext ?: error("Expected ActionContext")
        val cooldownComponent = getCooldownComponent(ctx)
        if (cooldownComponent != null && cooldownComponent.cooldowns.containsKey(id)) return ShowAction.GRAYED

        // Check if entity can afford the cost
        if (cost != null && ctx.entityId != null) {
            val entityStats: Attributes = StatsComponent.get(input.gameState.world, input.entityId)
            val costAttributes = cost as? Attributes
            if (costAttributes != null) {
                for ((stat, requiredAmount) in costAttributes) {
                    val availableAmount: Int? = entityStats[stat]
                    if (requiredAmount != null && (availableAmount == null || availableAmount < requiredAmount)) {
                        return ShowAction.GRAYED
                    }
                }
            }
        }

        return requirementsMetFn(ctx)
    }

    fun onHover(input: IActionContext) {
        val ctx = input as? ActionContext ?: error("Expected ActionContext")
        HUD.get().cursor.setMapPointerLoc(null)
        onHoverFn(ctx)
    }

    open fun onSelect(input: IActionContext): ActionResult {
        val ctx = input as? ActionContext ?: error("Expected ActionContext")
        val cooldownComponent = getCooldownComponent(ctx)
        cooldownComponent?.cooldowns?.set(id, turnCooldown)

        // Deduct the cost from the entity's stats
        if (cost != null && ctx.entityId != null) {
            val entityStats: Attributes = StatsComponent.get(input.gameState.world, input.entityId)
            val costAttributes = cost as? Attributes
            if (costAttributes != null) {
                for ((stat, amount) in costAttributes) {
                    entityStats.put(stat.id, amount, Operation.SUBTRACT)
                }
            }
        }

        val result: ActionResult = onSelectFn(ctx)
        return result
    }

    override fun getTitle(input: IActionContext): String {
        // Use custom InfoProvider if available
        val customProvider = customInfoProviderFn?.invoke(input)
        if (customProvider != null) {
            return customProvider.getTitle(input)
        }

        return getName()
    }

    override fun getDescription(input: Map<String, String>): String {
        // Use custom InfoProvider if available
        val actionContext = ActionContext(GameState.global(), null, false, input)
        val customProvider = customInfoProviderFn?.invoke(actionContext)
        if (customProvider != null) {
            return customProvider.getDescription(input)
        }

        return getDescription()
    }

    private var cooldown: Label? = null
    private var costLabel: Label? = null

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        // Use custom InfoProvider if available and it implements UIInfoProvider
        val actionContext = ActionContext(GameState.global(), null, false, input)
        val customProvider = customInfoProviderFn?.invoke(actionContext)
        if (customProvider is UIInfoProvider) {
            customProvider.fillCustomContent(table, skin, input)
            return
        }

        // Display cost if present
        if (cost != null) {
            if (costLabel == null) {
                costLabel = Label("", skin)
            }
            val costText = StringBuilder("Cost: ")
            var first = true
            for ((stat, amount) in cost!!) {
                if (!first) costText.append(", ")
                costText.append("${stat.getName()}: $amount")
                first = false
            }
            costLabel!!.setText(costText.toString())
            table.add(costLabel).fill().row()
        }

        // Default implementation for cooldown display
        if (turnCooldown != 0) {
            if (cooldown == null) {
                cooldown = Label("", skin)
            }
            val cooldownComponent = getCooldownComponent(actionContext)
            var cooldownVal = cooldownComponent?.cooldowns?.get(id)
            cooldownVal = if (cooldownVal == null) turnCooldown else (turnCooldown - cooldownVal - 1)
            cooldown!!.setText("Cooldown: $cooldownVal/$turnCooldown")
            table.add(cooldown).fill().row()
        }
    }

    private fun getCooldownComponent(input: ActionContext): PossibleActionsComponent? {
        val cooldownComponent: PossibleActionsComponent?
        if (factionCooldown) {
            val faction = UUID.fromString(input["owner"])
            cooldownComponent = PossibleActionsComponent.get(input.gameState.world, faction)
        } else {
            cooldownComponent = PossibleActionsComponent.get(
                input.gameState.world,
                input.entityId
            )
        }
        return cooldownComponent
    }

    override fun register(): Action {
        return Registries.ActionManager.register(this) as Action
    }
}
