package heroes.journey.entities.actions

import heroes.journey.components.PossibleActionsComponent
import heroes.journey.components.StatsComponent
import heroes.journey.modlib.actions.*
import heroes.journey.modlib.attributes.Attributes
import heroes.journey.modlib.attributes.Operation

object ActionUtils {

    @JvmStatic
    fun requirementsMet(action: Action, input: IActionContext): ShowAction {
        val ctx = input as? ActionContext ?: error("Expected ActionContext")
        val show = action.requirementsMetFn(ctx)
        val cooldownComponent = getCooldownComponent(ctx)
        if (cooldownComponent != null && cooldownComponent.cooldowns.containsKey(action.id)) return show.and(ShowAction.GRAYED)

        // Check if entity can afford the cost
        val cost = action.cost
        if (cost != null && ctx.entityId != null) {
            val entityStats: Attributes = StatsComponent.get(input.gameState.world, input.entityId)
            for ((stat, requiredAmount) in cost) {
                val availableAmount: Int? = entityStats[stat]
                if (requiredAmount != null && (availableAmount == null || availableAmount < requiredAmount)) {
                    return show.and(ShowAction.GRAYED)
                }
            }
        }

        return show
    }

    @JvmStatic
    fun onHover(action: Action, input: IActionContext) {
        val ctx = input as? ActionContext ?: error("Expected ActionContext")
        heroes.journey.ui.HUD.get().cursor.setMapPointerLoc(null)
        action.onHoverFn(ctx)
    }

    @JvmStatic
    fun onSelect(action: Action, input: IActionContext): ActionResult {
        if (action is BooleanOptionAction) {
            action.isTrue = !action.isTrue
            action.value = action.isTrue
            return NullResult()
        }
        val ctx = input as? ActionContext ?: error("Expected ActionContext")
        if (action.turnCooldown > -1) {
            val cooldownComponent = getCooldownComponent(ctx)
            cooldownComponent?.cooldowns?.set(action.id, action.turnCooldown)
        }

        // Deduct the cost from the entity's stats
        val cost = action.cost
        if (cost != null && ctx.entityId != null) {
            val entityStats: Attributes = StatsComponent.get(input.gameState.world, input.entityId)
            for ((stat, amount) in cost) {
                entityStats.put(stat.id, amount, Operation.SUBTRACT)
            }
        }

        return action.onSelectFn(ctx)
    }

    @JvmStatic
    fun getCooldownComponent(input: ActionContext): PossibleActionsComponent? {
        return PossibleActionsComponent.get(
            input.gameState.world,
            input.entityId
        )
    }
}
