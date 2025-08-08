import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.StringResult
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.targetAction
import heroes.journey.modlib.misc.Approach
import heroes.journey.modlib.misc.Challenge
import heroes.journey.modlib.registries.Registries
import java.util.*

// Challenge Actions - included by basegame mod

// Choose Approach
action {
    id = Ids.CHOOSE_APPROACH
    requirementsMetFn = { input ->
        val approach: Approach = Registries.ApproachManager[input["target"]!!]!!
        val stats = input.getStats(input.entityId!!)

        // Check if player can afford the approach cost
        var show: ShowAction = ShowAction.YES
        if (approach.cost != null) {
            println(approach.cost)
            for ((stat, requiredAmount) in approach.cost!!) {
                val currentAmount = stats[stat] ?: 0
                if (currentAmount < requiredAmount) {
                    println("Not enough " + stat)
                    show = ShowAction.GRAYED
                    break
                }
            }
        }
        show
    }
    // Return the approach directly since it implements InfoProvider
    customInfoProviderFn = { input ->
        val approach: Approach = Registries.ApproachManager[input["target"]!!]!!
        approach
    }
    onSelectFn = { input ->
        val regionId = input.getRegion(input.entityId!!)
        val approach: Approach = Registries.ApproachManager[input["target"]!!]!!
        val challengeEntityId = UUID.fromString(input["challenge"])
        val challenge: Challenge = input.getChallenge(challengeEntityId)

        val stats = input.getStats(input.entityId!!)

        // Apply the approach cost
        if (approach.cost != null) {
            for ((stat, amount) in approach.cost!!) {
                stats.add(stat.id, -amount)
            }
        }

        input.adjustStat(challengeEntityId, Ids.STAT_CHALLENGE_HEALTH, -5)
        val challengeStats = input.getStats(challengeEntityId)

        if (challengeStats.get(Ids.STAT_CHALLENGE_HEALTH)!! <= 0)
            input.removeChallengeFromRegion(regionId, challengeEntityId)

        // Calculate rewards based on approach stats
        val summary = StringBuilder()
        summary.append("You face the ${challenge.getName()} with ${approach.getName()}.\n")

        StringResult(summary.toString())
    }
}.register()

// Face Challenge
targetAction<Approach> {
    id = Ids.FACE_CHALLENGE
    // Return the challenge directly since it implements InfoProvider
    customInfoProviderFn = { input ->
        val challengeEntityId = UUID.fromString(input["target"])
        val challenge: Challenge = input.getChallenge(challengeEntityId)
        challenge
    }
    getTargets = { input ->
        input["challenge"] = input["target"]!!
        val challengeEntityId = UUID.fromString(input["target"])
        input.getApproachesFor(input.entityId!!, challengeEntityId)
    }
    targetAction = Ids.CHOOSE_APPROACH
}.register()

// Face Challenges
targetAction<UUID> {
    id = Ids.FACE_CHALLENGES
    getTargets = { input ->
        val regionId = input.getRegion(input.entityId!!)
        input.getChallenges(regionId)
    }
    targetAction = heroes.journey.modlib.Ids.FACE_CHALLENGE
}.register()
