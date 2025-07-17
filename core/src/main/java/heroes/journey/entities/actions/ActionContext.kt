package heroes.journey.entities.actions

import heroes.journey.GameState
import heroes.journey.components.BuffsComponent
import heroes.journey.components.InventoryComponent
import heroes.journey.components.NamedComponent
import heroes.journey.components.StatsComponent
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.registries.Registries
import heroes.journey.registries.Registries.ItemManager
import heroes.journey.utils.gamestate.Utils
import java.util.*

class ActionContext(
    private val innerGameState: GameState,
    entityId: UUID? = null,
    input: Map<String, String>
) :
    IActionContext(innerGameState, entityId, input) {

    constructor(innerGameState: GameState) : this(innerGameState, null, hashMapOf())
    constructor(innerGameState: GameState, entityId: UUID) : this(innerGameState, entityId, hashMapOf())

    override val gameState: GameState
        get() = innerGameState

    override fun getStat(entityId: UUID, statId: String): Int {
        val stats = StatsComponent.get((gameState as GameState).world, entityId)
        return stats?.get(statId) ?: 0
    }

    override fun addStat(entityId: UUID, statId: String, delta: Int) {
        val stats = StatsComponent.get((gameState as GameState).world, entityId)
        stats?.add(statId, delta)
    }

    override fun adjustStat(entityId: UUID, statId: String, delta: Int) {
        StatsComponent.adjustStat((gameState as GameState).world, entityId, statId, delta)
    }

    override fun addBuff(entityId: UUID, buffId: String) {
        val buffsComponent = BuffsComponent.get((gameState as GameState).world, entityId)
        val buff = Registries.BuffManager.get(buffId)
        buffsComponent.add(buff)
    }

    override fun addItem(entityId: UUID, itemId: String, amount: Int) {
        Utils.addItem(this, ItemManager[itemId], amount)
    }

    override fun addFame(entityId: UUID, amount: Int) {
        StatsComponent.addFame((gameState as GameState).world, entityId, amount)
    }

    override fun getName(entityId: UUID): String {
        return NamedComponent.get((gameState as GameState).world, entityId, "Unknown")
    }

    override fun getInventory(entityId: UUID): Map<String, Int>? {
        val inventoryComponent = InventoryComponent.get((gameState as GameState).world, entityId)
        return inventoryComponent?.inventory?.mapKeys { it.key.id } ?: emptyMap()
    }
}
