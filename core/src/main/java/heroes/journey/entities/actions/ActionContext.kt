package heroes.journey.entities.actions

import heroes.journey.GameState
import heroes.journey.components.*
import heroes.journey.components.character.MovementComponent
import heroes.journey.modlib.actions.Action
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.attributes.Attributes
import heroes.journey.modlib.attributes.Stat
import heroes.journey.modlib.misc.Challenge
import heroes.journey.modlib.misc.Quest
import heroes.journey.modlib.registries.InfoProvider
import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries
import heroes.journey.modlib.utils.Position
import heroes.journey.mods.Registries.ActionManager
import heroes.journey.ui.HUD
import heroes.journey.ui.infoproviders.BasicInfoProvider
import heroes.journey.ui.infoproviders.ChallengeInfoProvider
import heroes.journey.ui.infoproviders.LocationInfoProvider
import heroes.journey.ui.infoproviders.UIInfoProvider
import heroes.journey.utils.Utils
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing
import java.util.*
import heroes.journey.mods.Registries as CoreRegistries

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

    override fun getInfoProvider(registrable: Registrable): InfoProvider {
        return if (registrable is Challenge) {
            ChallengeInfoProvider(registrable)
        } else {
            registrable
        }
    }

    override fun getStat(entityId: UUID, statId: String): Int {
        val stats = StatsComponent.get(gameState.world, entityId)
        return stats?.get(statId) ?: 0
    }

    override fun adjustStat(entityId: UUID, statId: String, delta: Int) {
        StatsComponent.adjustStat(gameState.world, entityId, statId, delta)
    }

    override fun addBuff(entityId: UUID, buffId: String) {
        val buffsComponent = BuffsComponent.get(gameState.world, entityId)
        val buff = CoreRegistries.BuffManager[buffId]
        buffsComponent.add(buff)
    }

    override fun getName(entityId: UUID): String {
        return NamedComponent.get(gameState.world, entityId, "Unknown")
    }

    override fun getPosition(entityId: UUID): Position {
        val pos = PositionComponent.get(gameState.world, entityId)
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
        val questsComponent = QuestsComponent.get(gameState.world, entityId)
        return questsComponent?.quests ?: emptyList()
    }

    override fun addQuest(entityId: UUID, questId: String) {
        val questsComponent = QuestsComponent.get(gameState.world, entityId)
        val quest = CoreRegistries.QuestManager[questId]
        if (questsComponent != null && quest != null) {
            questsComponent.addQuest(quest)
        }
    }

    override fun removeQuest(entityId: UUID, questId: String) {
        val questsComponent = QuestsComponent.get(gameState.world, entityId)
        val quest = CoreRegistries.QuestManager[questId]
        if (questsComponent != null && quest != null) {
            questsComponent.remove(quest)
        }
    }

    override fun canAffordQuest(quest: Quest, entityId: UUID): Boolean {
        val playerStats = StatsComponent.get(gameState.world, entityId) ?: return false

        for ((stat, requiredAmount) in quest.cost) {
            val currentAmount = playerStats[stat] ?: 0
            if (currentAmount < requiredAmount) {
                return false
            }
        }
        return true
    }

    override fun completeQuest(quest: Quest, entityId: UUID): Boolean {
        val playerStats = StatsComponent.get(gameState.world, entityId)
        if (playerStats == null) return false

        // Check if player can afford the quest
        for ((stat, requiredAmount) in quest.cost) {
            val currentAmount = playerStats[stat] ?: 0
            if (currentAmount < requiredAmount) {
                return false
            }
        }

        // Apply costs
        for ((stat, amount) in quest.cost) {
            playerStats.put(stat.id, -amount, heroes.journey.modlib.attributes.Operation.ADD)
        }

        // Apply rewards
        for ((stat, amount) in quest.rewards) {
            playerStats.put(stat.id, amount, heroes.journey.modlib.attributes.Operation.ADD)
        }

        // Apply fame reward
        if (quest.fameReward > 0) {
            playerStats.put(
                heroes.journey.modlib.Ids.STAT_FAME,
                quest.fameReward,
                heroes.journey.modlib.attributes.Operation.ADD
            )
        }

        return true
    }

    override fun removeChallengeFromRegion(regionId: UUID, challengeId: UUID) {
        val regionComponent = RegionComponent.get(gameState.world, regionId)
        regionComponent?.removeChallenge(challengeId)
        gameState.world.delete(challengeId)
    }

    override fun getStats(entityId: UUID): Attributes {
        return StatsComponent.get(gameState.world, entityId)
    }

    override fun getChallenges(regionId: UUID): List<UUID> {
        val regionComponent = RegionComponent.get(gameState.world, regionId)
        return regionComponent.challenges
    }

    override fun setMapPointer(pos: Position) {
        HUD.get().cursor.setMapPointerLoc(pos)
    }

    override fun getChallenge(challengeEntityId: UUID): Challenge {
        val challengeComponent = ChallengeComponent.get(gameState.world, challengeEntityId)
        return challengeComponent?.challenge()
            ?: throw IllegalArgumentException("No challenge found for entity $challengeEntityId")
    }

    override fun findActionsByTags(
        requiredAllTags: List<Stat>,
        requiredAnyTags: List<Stat>,
        forbiddenTags: List<Stat>
    ): List<Action> {
        val actions = Registries.ActionManager.values
        return actions.filter { action ->
            if (!action.tags.containsAll(requiredAllTags)) return@filter false
            if (requiredAnyTags.isNotEmpty() && action.tags.intersect(requiredAnyTags.toSet())
                    .isEmpty()
            ) return@filter false
            if (action.tags.any { it in forbiddenTags }) return@filter false
            true
        }
    }

    override fun findActionsByTags(
        entityId: UUID,
        requiredAllTags: List<Stat>,
        requiredAnyTags: List<Stat>,
        forbiddenTags: List<Stat>
    ): List<Action> {
        val actions = PossibleActionsComponent.get(gameState.world, entityId).possibleActions
        return actions.filter { action ->
            if (!action.tags.containsAll(requiredAllTags)) return@filter false
            if (requiredAnyTags.isNotEmpty() && action.tags.intersect(requiredAnyTags.toSet())
                    .isEmpty()
            ) return@filter false
            if (action.tags.any { it in forbiddenTags }) return@filter false
            true
        }
    }

    override fun isValidTarget(actionId: String, tags: List<Stat>): Boolean {
        val action: Action = ActionManager[actionId]!!
        // Must contain all requiredAllTags
        if (!tags.containsAll(action.requiredAllTags)) return false

        // Must contain at least one of requiredAnyTags (if any are defined)
        if (action.requiredAnyTags.isNotEmpty() &&
            tags.intersect(action.requiredAnyTags.toSet()).isEmpty()
        ) return false

        // Must contain none of the forbiddenTags
        if (tags.any { it in action.forbiddenTags }) return false

        return true
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
