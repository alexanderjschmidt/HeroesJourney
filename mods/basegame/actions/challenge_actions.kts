import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.StringResult
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.targetAction
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.IStat
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

        input.removeChallengeFromRegion(regionId, challengeEntityId)

        val stats = input.getStats(input.entityId!!)
        var primaryAward: Int = stats.get(approach.baseStatId)!!
        val primaryStat: IStat = Registries.StatManager[approach.baseStatId]!!
        val primaryResourceStat: IStat =
            input.statWith(listOf(primaryStat.groups.first().id, Ids.GROUP_RESOURCES))

        var secondaryResourceStat: IStat? = null
        var secondaryAward = 0

        if (approach.secondaryStatId != null) {
            primaryAward = maxOf(0, primaryAward - 1)
            secondaryAward = if (stats.get(approach.secondaryStatId!!)!! < 5) 1 else 2
            val secondaryStat: IStat = Registries.StatManager[approach.secondaryStatId]!!
            secondaryResourceStat =
                input.statWith(listOf(secondaryStat.groups.first().id, Ids.GROUP_RESOURCES))
            stats.add(secondaryResourceStat, secondaryAward)
        }
        stats.add(primaryResourceStat, primaryAward)

        // Build summary string
        val summary = StringBuilder()
        summary.append("You face the ${challenge.getName()} with ${approach.getName()}.\n")
        summary.append("Your ${approach.baseStatId} skill (${stats.get(approach.baseStatId)}) provides ${primaryAward} ${primaryResourceStat}.\n")
        if (approach.secondaryStatId != null) {
            summary.append("Your ${approach.secondaryStatId} skill (${stats.get(approach.secondaryStatId!!)}) provides ${secondaryAward} ${secondaryResourceStat}.")
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
    inputDescriptionFn = { input ->
        val challengeEntityId = UUID.fromString(input["target"])
        val challenge: IChallenge = input.getChallenge(challengeEntityId)
        challenge.getDescription()
    }
    getTargets = { input ->
        input["challenge"] = input["target"]!!
        val challengeEntityId = UUID.fromString(input["target"])

        // Get the challenge to determine its type
        val challenge: IChallenge = input.getChallenge(challengeEntityId)
        val challengeType: IChallengeType = challenge.getChallengeType()

        // Get available approaches for this challenge type
        val allApproaches: List<IApproach> = challengeType.getApproaches()
        val availableApproaches: MutableList<IApproach> = mutableListOf()

        val stats: IAttributes = input.getStats(input.entityId!!)

        for (approach: IApproach in allApproaches) {
            if (approach.secondaryStatId != null) {
                if (stats.get(approach.secondaryStatId!!)!! >= 3) {
                    availableApproaches.add(approach)
                }
            } else {
                availableApproaches.add(approach)
            }
        }

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
