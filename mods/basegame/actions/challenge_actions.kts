import heroes.journey.GameState
import heroes.journey.Ids
import heroes.journey.components.ChallengeComponent
import heroes.journey.components.NamedComponent
import heroes.journey.components.RegionComponent
import heroes.journey.components.StatsComponent
import heroes.journey.entities.Challenge
import heroes.journey.entities.actions.action
import heroes.journey.entities.actions.results.EndTurnResult
import heroes.journey.entities.actions.targetAction
import heroes.journey.entities.tagging.Attributes
import heroes.journey.entities.tagging.Stat
import heroes.journey.utils.gamestate.Utils
import java.util.*

// Challenge Actions - included by basegame mod

// Choose Approach
action {
    id = "choose_approach"
    name = "Choose Approach"
    description = "Select an approach to handle the challenge."
    inputDisplayNameFn = { input ->
        Stat.valueOf(input["target"]!!).name
    }
    onSelectFn = { input ->
        val regionId = Utils.getRegion(input)
        val regionComponent = RegionComponent.get(
            input.gameState.world,
            regionId
        )
        val approach: Stat = Stat.valueOf(input["target"]!!)
        val challengeEntityId = UUID.fromString(input["challenge"])
        if (regionComponent != null) {
            regionComponent.removeChallenge(challengeEntityId)
            input.gameState.world.delete(challengeEntityId)

            val stats: Attributes = StatsComponent.get(input.gameState.world, input.entityId)
            val realmAttention = input.gameState.getRealmsAttention()

            // Reward: 1 renown per base stat part in the chosen approach, limited by realm attention
            val baseToRenown = mapOf(
                Stat.BODY to Stat.VALOR,
                Stat.MIND to Stat.INSIGHT,
                Stat.MAGIC to Stat.ARCANUM,
                Stat.CHARISMA to Stat.INFLUENCE
            )
            val partCounts = mutableMapOf<Stat, Int>()
            approach.getParts().forEach { (baseStat, count) ->
                partCounts[baseStat] = count
            }
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
    name = "Face Challenge"
    description = "Face down a challenge to prove your legend to the realm."
    inputDisplayNameFn = { input ->
        NamedComponent.get(GameState.global().world, UUID.fromString(input["target"]), "---")
    }
    getTargets = { input ->
        input["challenge"] = input["target"]!!
        val challengeEntityId = UUID.fromString(input["target"])
        val challengeComponent: ChallengeComponent =
            ChallengeComponent.get(input.gameState.world, challengeEntityId)
        val challenge: Challenge = challengeComponent.challenge()
        challenge.approaches.toList()
    }
    targetAction = Ids.CHOOSE_APPROACH
}.register()

// Face Challenges
targetAction<UUID> {
    id = "face_challenges"
    name = "Face Challenges"
    description = "See what challenges you can face"
    getTargets = { input ->
        val regionId = Utils.getRegion(input)
        RegionComponent.get(input.gameState.world, regionId).challenges
    }
    targetAction = Ids.FACE_CHALLENGE
}.register()
