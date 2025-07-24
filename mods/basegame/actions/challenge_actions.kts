import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.StringResult
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.targetAction
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.IChallenge
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

        // Calculate rewards based on approach stats
        var totalAward = 0
        val summary = StringBuilder()
        summary.append("You face the ${challenge.getName()} with ${approach.getName()}.\n")

        for (stat in approach.stats) {
            val statValue = stats.get(stat) ?: 0
            val award = if (statValue < 5) 1 else 2
            totalAward += award

            val resourceStat = input.statWith(listOf(stat.groups.first().id, Ids.GROUP_RESOURCES))
            stats.add(resourceStat, award)

            summary.append("Your ${stat.id} skill (${statValue}) provides ${award} ${resourceStat}.\n")
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

        // Get the challenge to determine its stats
        val challenge: IChallenge = input.getChallenge(challengeEntityId)

        // Get available approaches that match the challenge's stats
        val availableApproaches: MutableList<IApproach> = mutableListOf()
        val playerStats: IAttributes = input.getStats(input.entityId!!)

        // Get all approaches from registry
        val allApproaches = Registries.ApproachManager.values.toList()

        for (approach: IApproach in allApproaches) {
            // Check if approach has any stats that match the challenge's stats
            val hasMatchingStat = approach.stats.any { approachStat ->
                challenge.stats.any { challengeStat ->
                    approachStat.id == challengeStat.id
                }
            }

            if (hasMatchingStat) {
                // Check if player has sufficient stats for this approach
                val hasSufficientStats = approach.stats.all { stat ->
                    playerStats.get(stat.id) ?: 0 >= 1
                }

                if (hasSufficientStats) {
                    availableApproaches.add(approach)
                }
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
