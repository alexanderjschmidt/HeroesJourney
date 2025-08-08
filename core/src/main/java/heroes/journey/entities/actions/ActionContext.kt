package heroes.journey.entities.actions

import heroes.journey.GameState
import heroes.journey.components.*
import heroes.journey.components.character.MovementComponent
import heroes.journey.entities.Approach
import heroes.journey.entities.Quest
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.utils.Position
import heroes.journey.mods.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.infoproviders.BasicInfoProvider
import heroes.journey.ui.infoproviders.LocationInfoProvider
import heroes.journey.ui.infoproviders.UIInfoProvider
import heroes.journey.utils.Utils
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing
import java.util.*

class ActionContext(
    private val innerGameState: GameState,
    entityId: UUID? = null,
    isSimulation: Boolean = false,
    input: Map<String, String>
) :
    IActionContext(innerGameState, entityId, isSimulation, input) {

    constructor(innerGameState: GameState, isSimulation: Boolean) : this(
        innerGameState,
        null,
        isSimulation,
        hashMapOf()
    )

    constructor(innerGameState: GameState, entityId: UUID, isSimulation: Boolean) : this(
        innerGameState,
        entityId,
        isSimulation,
        hashMapOf()
    )

    override val gameState: GameState
        get() = innerGameState

    override fun getInfoProvider(entityId: UUID): UIInfoProvider {
        val locationComponent: LocationComponent? = LocationComponent.get(gameState.world, entityId)
        return if (locationComponent != null) {
            LocationInfoProvider(this, entityId)
        } else {
            // TODO split this into player and challenge???
            BasicInfoProvider(this, entityId)
        }
    }

    override fun getStat(entityId: UUID, statId: String): Int {
        val stats = StatsComponent.get(gameState.world, entityId)
        return stats?.get(statId) ?: 0
    }

    override fun adjustStat(entityId: UUID, statId: String, delta: Int) {
        StatsComponent.adjustStat((gameState as GameState).world, entityId, statId, delta)
    }

    override fun addBuff(entityId: UUID, buffId: String) {
        val buffsComponent = BuffsComponent.get((gameState as GameState).world, entityId)
        val buff = Registries.BuffManager[buffId]
        buffsComponent.add(buff)
    }

    override fun getName(entityId: UUID): String {
        return NamedComponent.get((gameState as GameState).world, entityId, "Unknown")
    }

    override fun getPosition(entityId: UUID): Position {
        val pos = PositionComponent.get((gameState as GameState).world, entityId)
        return Position(pos.x, pos.y)
    }

    override fun travelTo(entityId: UUID, target: Position) {
        val gs = gameState
        val positionComponent = PositionComponent.get(gs.world, entityId)
        val path = EntityCursorPathing().getPath(
            gs.map, positionComponent.x,
            positionComponent.y, target.x, target.y, entityId
        )
        if (isSimulation) {
            val end = gs.entities.moveEntity(entityId, path)
            positionComponent.setPos(end.x, end.y)
            positionComponent.sync()
        } else {
            gs.world.edit(entityId).create(
                MovementComponent::class.java
            ).path(path.reverse(), .05f)
        }
    }

    override fun getRegion(entityId: UUID): UUID {
        return Utils.getRegion(gameState, entityId)
    }

    override fun getNeighbors(regionId: UUID): List<UUID> {
        val region: RegionComponent = RegionComponent.get(gameState.getWorld(), regionId)
        return region.neighborRegionIds.stream().toList()
    }

    override fun getQuests(entityId: UUID): List<Quest> {
        val questsComponent = QuestsComponent.get((gameState as GameState).world, entityId)
        return questsComponent?.quests ?: emptyList()
    }

    override fun addQuest(entityId: UUID, questId: String) {
        val questsComponent = QuestsComponent.get((gameState as GameState).world, entityId)
        val quest = Registries.QuestManager[questId]
        if (questsComponent != null && quest != null) {
            questsComponent.addQuest(quest)
        }
    }

    override fun removeQuest(entityId: UUID, questId: String) {
        val questsComponent = QuestsComponent.get((gameState as GameState).world, entityId)
        val quest = Registries.QuestManager[questId]
        if (questsComponent != null && quest != null) {
            questsComponent.remove(quest)
        }
    }

    override fun removeChallengeFromRegion(regionId: UUID, challengeId: UUID) {
        val regionComponent = RegionComponent.get((gameState as GameState).world, regionId)
        regionComponent?.removeChallenge(challengeId)
        (gameState as GameState).world.delete(challengeId)
    }

    override fun getApproachesFor(entityId: UUID, challengeEntityId: UUID): List<IApproach> {
        val possibleActionsComponent: PossibleActionsComponent =
            PossibleActionsComponent.get(gameState.world, entityId) ?: return listOf()
        val possibleApproaches: List<Approach> = possibleActionsComponent.possibleApproaches

        val challenge = getChallenge(challengeEntityId)
        val approaches: MutableList<IApproach> = mutableListOf()
        for (approach in possibleApproaches) {
            if (isValidTarget(challenge.stats, approach)) {
                approaches.add(approach)
            }
        }
        return approaches
    }

    private fun isValidTarget(targetTags: List<IStat>, approach: Approach): Boolean {
        // Must contain all requiredAllTags
        if (!targetTags.containsAll(approach.requiredAllTags)) return false

        // Must contain at least one of requiredAnyTags (if any are defined)
        if (approach.requiredAnyTags.isNotEmpty() &&
            targetTags.intersect(approach.requiredAnyTags.toSet()).isEmpty()
        ) return false

        // Must contain none of the forbiddenTags
        if (targetTags.any { it in approach.forbiddenTags }) return false

        return true
    }

    override fun getStats(entityId: UUID): Attributes {
        return StatsComponent.get((gameState as GameState).world, entityId)
    }

    override fun getChallenges(regionId: UUID): List<UUID> {
        val regionComponent = RegionComponent.get(gameState.world, regionId)
        return regionComponent.challenges
    }

    override fun setMapPointer(pos: Position) {
        HUD.get().cursor.setMapPointerLoc(pos)
    }

    override fun getChallenge(challengeEntityId: UUID): IChallenge {
        val challengeComponent = ChallengeComponent.get(gameState.world, challengeEntityId)
        return challengeComponent?.challenge()
            ?: throw IllegalArgumentException("No challenge found for entity $challengeEntityId")
    }

    // Turn configuration methods for dynamic game state modification
    override fun setMinChallengePowerTier(tier: Int) {
        gameState.setMinChallengePowerTier(tier)
    }

    override fun setMaxChallengePowerTier(tier: Int) {
        gameState.setMaxChallengePowerTier(tier)
    }

    override fun setAmbientLighting(lighting: Float) {
        gameState.setAmbientLighting(lighting)
    }

    override fun spawnDemonKing() {
        gameState.spawnDemonKing()
    }

    override fun getCurrentTurn(): Int {
        return gameState.getTurnNumber()
    }

    override fun setGameStateValue(key: String, value: Any) {
        gameState.setGameStateValue(key, value)
    }

    override fun getGameStateValue(key: String): Any? {
        return gameState.getGameStateValue(key)
    }
}
