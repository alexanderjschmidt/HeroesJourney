package heroes.journey.entities

import heroes.journey.components.StatsComponent
import heroes.journey.entities.actions.ActionContext
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.IQuest
import heroes.journey.modlib.Operation
import heroes.journey.modlib.Registrable
import heroes.journey.registries.Registries
import java.util.*

class Quest(
    id: String,
    override val cost: Attributes = Attributes(),
    override val rewards: Attributes = Attributes(),
    override val fameReward: Int = 0
) : Registrable(id), IQuest {
    override fun register(): Quest {
        return Registries.QuestManager.register(this)
    }

    override fun canAfford(input: Any): Boolean {
        val actionContext = input as? ActionContext ?: return false
        val playerStats =
            StatsComponent.get(actionContext.gameState.getWorld(), actionContext.entityId) ?: return false
        for ((stat, requiredAmount) in cost) {
            if (playerStats[stat]!! < requiredAmount) {
                return false
            }
        }
        return true
    }

    override fun onComplete(input: Any): Boolean {
        val actionContext = input as? ActionContext ?: return false
        val playerStats = StatsComponent.get(actionContext.gameState.getWorld(), actionContext.entityId)
        if (playerStats == null) return false
        for ((stat, requiredAmount) in cost) {
            if (playerStats[stat]!! < requiredAmount) {
                return false
            }
        }
        for ((stat, amount) in cost) {
            playerStats.put(stat, -amount, Operation.ADD)
        }
        for ((stat, amount) in rewards) {
            playerStats.put(stat, amount, Operation.ADD)
        }
        if (fameReward > 0) {
            StatsComponent.addFame(actionContext.gameState.getWorld(), actionContext.entityId, fameReward)
        }
        return true
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Quest) return false
        return id == o.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}
