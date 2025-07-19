import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.StringResult
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.targetAction
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.misc.IChallengeType
import heroes.journey.modlib.registries.Registries
import java.util.*

// Challenge Actions - included by basegame mod

// Choose Approach
action {
    id = Ids.CHOOSE_APPROACH
    inputDisplayNameFn = { input ->
        input["target"]!!
    }
    onSelectFn = { input ->
        val regionId = input.getRegion(input.entityId!!)
        val approach: IApproach = Registries.ApproachManager[input["target"]!!]!!
        val challengeEntityId = UUID.fromString(input["challenge"])
        val challenge: IChallenge = input.getChallenge(challengeEntityId)

        input.removeChallengeFromRegion(regionId, challengeEntityId)

        val stats = input.getStats(input.entityId!!)
        var primaryRenownCount = stats.get(approach.baseStatId)
        val primaryRenownStat: String = input.getRenownStatFromBase(approach.baseStatId)

        var secondaryRenownStat = ""
        var secondaryAward = 0

        if (approach.secondaryStatId != null) {
            primaryRenownCount = maxOf(0, primaryRenownCount - 1)
            val secondaryRenownCount = if (stats.get(approach.secondaryStatId!!) < 5) 1 else 2
            secondaryRenownStat = input.getRenownStatFromBase(approach.secondaryStatId!!)
            secondaryAward = input.getRealmAttention(secondaryRenownStat, secondaryRenownCount)
            stats.add(secondaryRenownStat, secondaryAward)
        }
        val primaryAward = input.getRealmAttention(primaryRenownStat, primaryRenownCount)
        stats.add(primaryRenownStat, primaryAward)

        // Build summary string
        val summary = StringBuilder()
        summary.append("You face the ${challenge.getName()} with ${approach.getName()}.\n")
        summary.append("Your ${approach.baseStatId} skill (${stats.get(approach.baseStatId)}) provides ${primaryAward} ${primaryRenownStat}.\n")
        if (approach.secondaryStatId != null) {
            summary.append("Your ${approach.secondaryStatId} skill (${stats.get(approach.secondaryStatId!!)}) provides ${secondaryAward} ${secondaryRenownStat}.\n")
        }

        StringResult(summary.toString())
    }
}.register()

// Face Challenge
targetAction<IApproach> {
    id = Ids.FACE_CHALLENGE
    inputDisplayNameFn = { input ->
        input.getName(UUID.fromString(input["target"]))
    }
    getTargets = { input ->
        input["challenge"] = input["target"]!!
        val challengeEntityId = UUID.fromString(input["target"])

        // Get the challenge to determine its type
        val challenge: IChallenge = input.getChallenge(challengeEntityId)
        val challengeType: IChallengeType = challenge.getChallengeType()

        // Get available approaches for this challenge type
        val availableApproaches: List<IApproach> = challengeType.getApproaches()

        availableApproaches
    }
    targetAction = heroes.journey.modlib.Ids.CHOOSE_APPROACH
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
