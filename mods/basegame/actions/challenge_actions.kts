import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.ActionEntry
import heroes.journey.modlib.actions.ActionListResult
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
        input["challenge"] = input["target"]!!
        val optTag = Registries.StatManager[Ids.GROUP_APPROACHES]
        val actions = if (optTag != null) {
            input.findActionsByTags(input.entityId!!, requiredAllTags = listOf(optTag))
        } else emptyList()
        val options = actions.map { a -> ActionEntry(a.id, input.getHashMapCopy()) }
        ActionListResult(options)
    }
}.register()

// Face Challenges
targetAction<UUID> {
    id = Ids.FACE_CHALLENGES
    tag(Ids.GROUP_MAIN_ACTION)
    getTargets = { input ->
        val regionId = input.getRegion(input.entityId!!)
        input.getChallenges(regionId)
    }
    targetAction = Ids.FACE_CHALLENGE
}.register()
