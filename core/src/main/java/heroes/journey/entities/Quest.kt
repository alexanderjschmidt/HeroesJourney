package heroes.journey.entities

import heroes.journey.components.StatsComponent
import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.tagging.Attributes
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import java.util.*

class Quest(
    id: String,
    name: String?,
    private val cost: Attributes = Attributes(),
    private val rewards: Attributes = Attributes(),
    private val fameReward: Int = 0
) : Registrable(id, name) {
    
    fun onComplete(input: ActionInput): Boolean {
        // Check if player can afford the cost
        val playerStats = StatsComponent.get(input.gameState.getWorld(), input.entityId)
        if (playerStats == null) return false
        
        // Check if player has enough of each required attribute
        for ((stat, requiredAmount) in cost) {
            if (playerStats.get(stat) < requiredAmount) {
                return false // Player can't afford this quest
            }
        }
        
        // Deduct costs
        for ((stat, amount) in cost) {
            playerStats.put(stat, -amount, heroes.journey.entities.tagging.Operation.ADD)
        }
        
        // Add rewards
        for ((stat, amount) in rewards) {
            playerStats.put(stat, amount, heroes.journey.entities.tagging.Operation.ADD)
        }
        
        // Add fame reward
        if (fameReward > 0) {
            StatsComponent.addFame(input.gameState.getWorld(), input.entityId, fameReward)
        }
        
        return true
    }
    
    fun canAfford(input: ActionInput): Boolean {
        val playerStats = StatsComponent.get(input.gameState.getWorld(), input.entityId)
        if (playerStats == null) return false
        
        for ((stat, requiredAmount) in cost) {
            if (playerStats.get(stat) < requiredAmount) {
                return false
            }
        }
        return true
    }
    
    fun getCost(): Attributes = cost
    fun getRewards(): Attributes = rewards
    fun getFameReward(): Int = fameReward

    override fun register(): Quest {
        return Registries.QuestManager.register(this)
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
