import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.targetAction
import heroes.journey.modlib.misc.Challenge
import heroes.journey.modlib.registries.Registries
import java.util.*

// Challenge Actions - included by basegame mod

// Face Challenge
action {
    id = Ids.FACE_CHALLENGE
    isReturnsActionList = true
    customInfoProviderFn = { input ->
        val challengeEntityId = UUID.fromString(input["target"])
        val challenge: Challenge = input.getChallenge(challengeEntityId)
        challenge
    }
    onSelectFn = { input ->
        val challengeEntityId = UUID.fromString(input["target"])
        input["challenge"] = input["target"]!!
        val optTag = Registries.StatManager[Ids.GROUP_APPROACHES]
        val actions = if (optTag != null) {
            input.findActionsByTags(requiredAllTags = listOf(optTag))
        } else emptyList()
        val options = actions.map { a -> heroes.journey.modlib.actions.ActionEntry(a.id, input.getHashMapCopy()) }
        heroes.journey.modlib.actions.ActionListResult(options)
    }
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
