import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.results.EndTurnResult
import heroes.journey.modlib.actions.targetAction
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.registries.Registries
import java.util.*

// Challenge Actions - included by basegame mod

// Choose Approach
action {
    id = "choose_approach"
    inputDisplayNameFn = { input ->
        input["target"]!!
    }
    onSelectFn = { input ->
        val regionId = input.getRegion(input.entityId!!)
        val approach: String = input["target"]!!
        val challengeEntityId = UUID.fromString(input["challenge"])

        input.removeChallengeFromRegion(regionId, challengeEntityId)

        val stats = input.getStats(input.entityId!!)
        val count = 2 // This would be calculated based on approach logic
        val renownStat = heroes.journey.modlib.Ids.STAT_ARCANUM // Example stat, replace as needed
        val actualReward = input.getRealmAttention(renownStat, count)
        stats.add(renownStat, actualReward)
        EndTurnResult()
    }
}.register()

// Face Challenge
targetAction<IStat> {
    id = "face_challenge"
    inputDisplayNameFn = { input ->
        input.getName(UUID.fromString(input["target"]))
    }
    getTargets = { input ->
        input["challenge"] = input["target"]!!
        val challengeEntityId = UUID.fromString(input["target"])
        listOf(Registries.StatManager[Ids.STAT_BODY]!!, Registries.StatManager[Ids.STAT_MIND]!!)
    }
    targetAction = heroes.journey.modlib.Ids.CHOOSE_APPROACH
}.register()

// Face Challenges
targetAction<UUID> {
    id = "face_challenges"
    getTargets = { input ->
        val regionId = input.getRegion(input.entityId!!)
        input.getChallenges(regionId)
    }
    targetAction = heroes.journey.modlib.Ids.FACE_CHALLENGE
}.register()
