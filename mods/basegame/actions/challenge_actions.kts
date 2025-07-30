import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.StringResult
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.targetAction
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.registries.Registries
import java.util.*

// Challenge Actions - included by basegame mod

// Choose Approach
action {
    id = Ids.CHOOSE_APPROACH
    requirementsMetFn = { input ->
        val approach: IApproach = Registries.ApproachManager[input["target"]!!]!!
        val stats = input.getStats(input.entityId!!)

        // Check if player can afford the approach cost
        var show: ShowAction = ShowAction.YES
        if (approach.cost != null) {
            for ((stat, requiredAmount) in approach.cost!!) {
                val currentAmount = stats[stat] ?: 0
                if (currentAmount < requiredAmount) {
                    show = ShowAction.GRAYED
                    break
                }
            }
        }
        show
    }
    inputDisplayNameFn = { input ->
        input["target"]!!
    }
    inputDescriptionFn = { input ->
        val approach: IApproach = Registries.ApproachManager[input["target"]!!]!!
        val challengeEntityId = UUID.fromString(input["challenge"])
        val challenge: IChallenge = input.getChallenge(challengeEntityId)
        "Use " + approach.getName() + " to " + challenge.getName()
    }
    onSelectFn = { input ->
        val regionId = input.getRegion(input.entityId!!)
        val approach: IApproach = Registries.ApproachManager[input["target"]!!]!!
        val challengeEntityId = UUID.fromString(input["challenge"])
        val challenge: IChallenge = input.getChallenge(challengeEntityId)

        val stats = input.getStats(input.entityId!!)

        // Apply the approach cost
        if (approach.cost != null) {
            for ((stat, amount) in approach.cost!!) {
                stats.add(stat, -amount)
            }
        }

        input.adjustStat(challengeEntityId, Ids.STAT_CHALLENGE_HEALTH, -5)

        if (stats.get(Ids.STAT_CHALLENGE_HEALTH)!! <= 0)
            input.removeChallengeFromRegion(regionId, challengeEntityId)

        // Calculate rewards based on approach stats
        val summary = StringBuilder()
        summary.append("You face the ${challenge.getName()} with ${approach.getName()}.\n")

        StringResult(summary.toString())
    }
}.register()

// Face Challenge
targetAction<IApproach> {
    id = Ids.FACE_CHALLENGE
    inputDisplayNameFn = { input ->
        input.getName(UUID.fromString(input["target"]))
    }
    inputDescriptionFn = { input ->
        val challengeEntityId = UUID.fromString(input["target"])
        val challenge: IChallenge = input.getChallenge(challengeEntityId)
        challenge.getDescription()
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
