package heroes.journey.modlib.actions

import heroes.journey.modlib.IGameState
import heroes.journey.modlib.Position
import java.util.*

// You may need to import or define a Position class in modlib if not already present

abstract class IActionContext(
    open val gameState: IGameState,
    val entityId: UUID? = null,
    val isSimulation: Boolean = false,
    input: Map<String, String>
) :
    HashMap<String, String>() {

    init {
        for ((key, value) in input) {
            this[key] = value
        }
    }

    fun getHashMapCopy(): HashMap<String, String> {
        val copy: HashMap<String, String> = HashMap()
        for ((key, value) in this) {
            copy[key] = value
        }
        return copy
    }

    abstract fun getStat(entityId: UUID, statId: String): Int
    abstract fun addStat(entityId: UUID, statId: String, delta: Int)
    abstract fun adjustStat(entityId: UUID, statId: String, delta: Int)
    abstract fun addBuff(entityId: UUID, buffId: String)
    abstract fun addItem(entityId: UUID, itemId: String, amount: Int)
    abstract fun addFame(entityId: UUID, amount: Int)
    abstract fun getName(entityId: UUID): String
    abstract fun getInventory(entityId: UUID): Map<String, Int>?
    abstract fun getPosition(entityId: UUID): Position
    abstract fun travelTo(entityId: UUID, target: Position)
    abstract fun getRegion(entityId: UUID): UUID
    abstract fun getNeighbors(regionId: UUID): List<UUID>
}
