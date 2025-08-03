package heroes.journey.modlib.actions

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.misc.IQuest
import heroes.journey.modlib.utils.IGameState
import heroes.journey.modlib.utils.Position
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
    abstract fun adjustStat(entityId: UUID, statId: String, delta: Int)
    abstract fun addBuff(entityId: UUID, buffId: String)
    abstract fun getName(entityId: UUID): String
    abstract fun getPosition(entityId: UUID): Position
    abstract fun travelTo(entityId: UUID, target: Position)
    abstract fun getRegion(entityId: UUID): UUID
    abstract fun getNeighbors(regionId: UUID): List<UUID>
    abstract fun getQuests(entityId: UUID): List<IQuest>
    abstract fun addQuest(entityId: UUID, questId: String)
    abstract fun removeQuest(entityId: UUID, questId: String)
    abstract fun removeChallengeFromRegion(regionId: UUID, challengeId: UUID)
    abstract fun getApproachesFor(entityId: UUID, challengeEntityId: UUID): List<IApproach>
    abstract fun getStats(entityId: UUID): IAttributes
    abstract fun getChallenges(regionId: UUID): List<UUID>
    abstract fun setMapPointer(pos: Position)

    /**
     * Get the challenge object by its entity ID.
     */
    abstract fun getChallenge(challengeEntityId: UUID): IChallenge
}
