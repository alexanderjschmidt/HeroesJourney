package heroes.journey.modlib.actions

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.Approach
import heroes.journey.modlib.misc.Challenge
import heroes.journey.modlib.misc.Quest
import heroes.journey.modlib.registries.InfoProvider
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

    abstract fun getInfoProvider(entityId: UUID): InfoProvider

    abstract fun getStat(entityId: UUID, statId: String): Int
    abstract fun adjustStat(entityId: UUID, statId: String, delta: Int)
    abstract fun getStats(entityId: UUID): IAttributes

    abstract fun addBuff(entityId: UUID, buffId: String)
    abstract fun getName(entityId: UUID): String

    abstract fun getPosition(entityId: UUID): Position
    abstract fun travelTo(entityId: UUID, target: Position)
    abstract fun getRegion(entityId: UUID): UUID
    abstract fun getNeighbors(regionId: UUID): List<UUID>
    abstract fun setMapPointer(pos: Position)

    abstract fun getQuests(entityId: UUID): List<Quest>
    abstract fun addQuest(entityId: UUID, questId: String)
    abstract fun removeQuest(entityId: UUID, questId: String)
    
    /**
     * Check if a quest can be afforded by the given entity.
     */
    abstract fun canAffordQuest(quest: Quest, entityId: UUID): Boolean
    
    /**
     * Complete a quest for the given entity, applying costs and rewards.
     * Returns true if successful.
     */
    abstract fun completeQuest(quest: Quest, entityId: UUID): Boolean

    abstract fun removeChallengeFromRegion(regionId: UUID, challengeId: UUID)
    abstract fun getApproachesFor(entityId: UUID, challengeEntityId: UUID): List<Approach>
    abstract fun getChallenge(challengeEntityId: UUID): Challenge
    abstract fun getChallenges(regionId: UUID): List<UUID>

    // Turn configuration methods for dynamic game state modification
    abstract fun setMinChallengePowerTier(tier: Int)
    abstract fun setMaxChallengePowerTier(tier: Int)
    abstract fun setAmbientLighting(lighting: Float)
    abstract fun spawnDemonKing()
    abstract fun getCurrentTurn(): Int
    abstract fun setGameStateValue(key: String, value: Any)
    abstract fun getGameStateValue(key: String): Any?
}
