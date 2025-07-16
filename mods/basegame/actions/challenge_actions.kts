import heroes.journey.GameState
import heroes.journey.components.ChallengeComponent
import heroes.journey.components.NamedComponent
import heroes.journey.components.RegionComponent
import heroes.journey.components.StatsComponent
import heroes.journey.entities.Challenge
import heroes.journey.entities.actions.action
import heroes.journey.entities.actions.targetAction
import heroes.journey.entities.tagging.Attributes
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.results.EndTurnResult
import heroes.journey.utils.gamestate.Utils
import java.util.*

// Challenge Actions - included by basegame mod

// Choose Approach
action {
    id = "choose_approach"
    inputDisplayNameFn = { input ->
        input["target"]!!
    }
    onSelectFn = { input ->
        val regionId = Utils.getRegion(input)
        val regionComponent = RegionComponent.get(
            input.gameState.world,
            regionId
        )
        val approach: String = input["target"]!!
        val challengeEntityId = UUID.fromString(input["challenge"])
        if (regionComponent != null) {
            regionComponent.removeChallenge(challengeEntityId)
            input.gameState.world.delete(challengeEntityId)

            val stats: Attributes = StatsComponent.get(input.gameState.world, input.entityId)
            val realmAttention = input.gameState.getRealmsAttention()

            // Reward: 1 renown per base stat part in the chosen approach, limited by realm attention
            val baseToRenown = mapOf(
                Ids.STAT_BODY to Ids.STAT_VALOR,
                Ids.STAT_MIND to Ids.STAT_INSIGHT,
                Ids.STAT_MAGIC to Ids.STAT_ARCANUM,
                Ids.STAT_CHARISMA to Ids.STAT_INFLUENCE
            )
            val partCounts = mutableMapOf<String, Int>()
            // TODO fix
            for ((baseStat, count) in partCounts) {
                val renownStat = baseToRenown[baseStat] ?: continue
                val currentRealmAttention = realmAttention.get(renownStat)
                val actualReward = minOf(count, currentRealmAttention)
                if (actualReward > 0) {
                    stats.add(renownStat, actualReward)
                    realmAttention.put(renownStat, currentRealmAttention - actualReward)
                }
            }
        }
        EndTurnResult()
    }
}.register()

// Face Challenge
targetAction<Stat> {
    id = "face_challenge"
    inputDisplayNameFn = { input ->
        NamedComponent.get(GameState.global().world, UUID.fromString(input["target"]), "---")
    }
    getTargets = { input ->
        input["challenge"] = input["target"]!!
        val challengeEntityId = UUID.fromString(input["target"])
        val challengeComponent: ChallengeComponent =
            ChallengeComponent.get(input.gameState.world, challengeEntityId)
        val challenge: Challenge = challengeComponent.challenge()
        challenge.approaches().toList()
    }
    targetAction = Ids.CHOOSE_APPROACH
}.register()

// Face Challenges
targetAction<UUID> {
    id = "face_challenges"
    getTargets = { input ->
        val regionId = Utils.getRegion(input)
        RegionComponent.get(input.gameState.world, regionId).challenges
    }
    targetAction = Ids.FACE_CHALLENGE
}.register()
