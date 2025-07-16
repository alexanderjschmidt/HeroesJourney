package heroes.journey.entities

import heroes.journey.components.StatsComponent
import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.IQuest
import heroes.journey.registries.Registrable
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
        val actionInput = input as? ActionInput ?: return false
        val playerStats =
            StatsComponent.get(actionInput.gameState.getWorld(), actionInput.entityId) ?: return false
        for ((stat, requiredAmount) in cost) {
            if (playerStats[stat]!! < requiredAmount) {
                return false
            }
        }
        return true
    }

    override fun onComplete(input: Any): Boolean {
        val actionInput = input as? ActionInput ?: return false
        val playerStats = StatsComponent.get(actionInput.gameState.getWorld(), actionInput.entityId)
        if (playerStats == null) return false
        for ((stat, requiredAmount) in cost) {
            if (playerStats[stat]!! < requiredAmount) {
                return false
            }
        }
        for ((stat, amount) in cost) {
            playerStats.put(stat, -amount, heroes.journey.entities.tagging.Operation.ADD)
        }
        for ((stat, amount) in rewards) {
            playerStats.put(stat, amount, heroes.journey.entities.tagging.Operation.ADD)
        }
        if (fameReward > 0) {
            StatsComponent.addFame(actionInput.gameState.getWorld(), actionInput.entityId, fameReward)
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
