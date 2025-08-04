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
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.windows.InfoProvider
import java.util.*

open class Action(
    id: String,
    override val isReturnsActionList: Boolean = false,
    override val requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    override val onHoverFn: (IActionContext) -> Unit = {},
    override val onSelectFn: (IActionContext) -> ActionResult,
    override val inputDisplayNameFn: ((IActionContext) -> String)? = null,
    override val inputDescriptionFn: ((IActionContext) -> String)? = null,
    override val turnCooldown: Int = 0,
    override val factionCooldown: Boolean = false,
    override val cost: IAttributes? = null
) : Registrable(id), InfoProvider, IAction {

    val hasInput: Boolean
        get() = inputDisplayNameFn != null

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

    override fun getTitle(input: ActionContext): String {
        if (inputDisplayNameFn != null) {
            return inputDisplayNameFn!!.invoke(input)
        }
        return getName()
    }

    override fun getDescription(input: Map<String, String>): String {
        if (inputDescriptionFn != null) {
            val ctx = ActionContext(GameState.global(), null, false, input)
            return inputDescriptionFn!!.invoke(ctx)
        }
        return getDescription()
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

    private var cooldown: Label? = null

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        if (turnCooldown != 0) {
            if (cooldown == null) {
                cooldown = Label("", skin)
            }
            val actionContext: ActionContext =
                ActionContext(GameState.global(), GameState.global().currentEntity, false)
            actionContext.putAll(input)
            val cooldownComponent = getCooldownComponent(
                actionContext
            )
            var cooldownVal = cooldownComponent?.cooldowns?.get(id)
            cooldownVal = if (cooldownVal == null) turnCooldown else (turnCooldown - cooldownVal - 1)
            cooldown!!.setText("Cooldown: $cooldownVal/$turnCooldown")
            table.add(cooldown).fill().row()
        }
    }

    override fun register(): Action {
        return Registries.ActionManager.register(this) as Action
    }
}
