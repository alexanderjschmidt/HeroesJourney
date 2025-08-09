package challenges

import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.StringResult
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.Challenge
import heroes.journey.modlib.registries.Registries
import java.util.*

// Approach Actions - independent actions tagged with GROUP_APPROACHES

// Fight Approach Action
action {
    id = Ids.APPROACH_FIGHT
    tag(Ids.GROUP_APPROACHES)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 30)
    }
    onSelectFn = { input ->
        val regionId = input.getRegion(input.entityId!!)
        val challengeEntityId = UUID.fromString(input["challenge"])
        val challenge: Challenge = input.getChallenge(challengeEntityId)

        // Damage the challenge
        input.adjustStat(challengeEntityId, Ids.STAT_CHALLENGE_HEALTH, -5)
        val challengeStats = input.getStats(challengeEntityId)
        if (challengeStats.get(Ids.STAT_CHALLENGE_HEALTH)!! <= 0)
            input.removeChallengeFromRegion(regionId, challengeEntityId)

        StringResult("You face the ${challenge.getName()} in battle.")
    }
}.register()

// Trick Approach Action
action {
    id = Ids.APPROACH_TRICK
    tag(Ids.GROUP_APPROACHES)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 10)
        stat(Ids.STAT_FOCUS, 10)
    }
    onSelectFn = { input ->
        val regionId = input.getRegion(input.entityId!!)
        val challengeEntityId = UUID.fromString(input["challenge"])
        val challenge: Challenge = input.getChallenge(challengeEntityId)

        input.adjustStat(challengeEntityId, Ids.STAT_CHALLENGE_HEALTH, -5)
        val challengeStats = input.getStats(challengeEntityId)
        if (challengeStats.get(Ids.STAT_CHALLENGE_HEALTH)!! <= 0)
            input.removeChallengeFromRegion(regionId, challengeEntityId)

        StringResult("You outwit the ${challenge.getName()} cleverly.")
    }
}.register()

// Magic Missile Approach Action
action {
    id = Ids.APPROACH_MAGIC_MISSILE
    tag(Ids.GROUP_APPROACHES)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 5)
        stat(Ids.STAT_MANA, 20)
    }
    onSelectFn = { input ->
        val regionId = input.getRegion(input.entityId!!)
        val challengeEntityId = UUID.fromString(input["challenge"])
        val challenge: Challenge = input.getChallenge(challengeEntityId)

        input.adjustStat(challengeEntityId, Ids.STAT_CHALLENGE_HEALTH, -5)
        val challengeStats = input.getStats(challengeEntityId)
        if (challengeStats.get(Ids.STAT_CHALLENGE_HEALTH)!! <= 0)
            input.removeChallengeFromRegion(regionId, challengeEntityId)

        StringResult("You blast the ${challenge.getName()} with magic.")
    }
}.register()

// Negotiate Approach Action
action {
    id = Ids.APPROACH_NEGOTIATE
    tag(Ids.GROUP_APPROACHES)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 10)
        stat(Ids.STAT_MOXIE, 10)
    }
    onSelectFn = { input ->
        val regionId = input.getRegion(input.entityId!!)
        val challengeEntityId = UUID.fromString(input["challenge"])
        val challenge: Challenge = input.getChallenge(challengeEntityId)

        input.adjustStat(challengeEntityId, Ids.STAT_CHALLENGE_HEALTH, -5)
        val challengeStats = input.getStats(challengeEntityId)
        if (challengeStats.get(Ids.STAT_CHALLENGE_HEALTH)!! <= 0)
            input.removeChallengeFromRegion(regionId, challengeEntityId)

        StringResult("You negotiate with the ${challenge.getName()} confidently.")
    }
}.register()
