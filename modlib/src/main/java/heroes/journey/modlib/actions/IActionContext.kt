package heroes.journey.modlib.actions

import heroes.journey.modlib.IAttributes
import heroes.journey.modlib.IGameState
import heroes.journey.modlib.IQuest
import heroes.journey.modlib.Position
import java.util.*

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
    abstract fun getQuests(entityId: UUID): List<IQuest>
    abstract fun addQuest(entityId: UUID, questId: String)
    abstract fun removeQuest(entityId: UUID, questId: String)
    abstract fun removeChallengeFromRegion(regionId: UUID, challengeId: UUID)
    abstract fun getStats(entityId: UUID): IAttributes
    abstract fun getRealmAttention(statId: String, requested: Int): Int
    abstract fun getChallenges(regionId: UUID): List<UUID>
}
