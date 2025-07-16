import heroes.journey.modlib.Ids.*
import heroes.journey.modlib.attributes
import heroes.journey.modlib.challenge

// Vermin Challenges - included by basegame mod
challenge(
    id = CHALLENGE_OUTSMART_GIANT_ANT,
    render = OUTSMART_GIANT_ANT,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_CRUSH_BEDBUG_SWARM,
    render = CRUSH_BEDBUG_SWARM,
    approaches = listOf(STAT_BRAVADO, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_HUNT_TUNNELING_MOLE,
    render = HUNT_TUNNELING_MOLE,
    approaches = listOf(STAT_MIGHT, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_BEFRIEND_DUNG_BEETLE,
    render = BEFRIEND_DUNG_BEETLE,
    approaches = listOf(STAT_BRAVADO, STAT_ENCHANTING, STAT_MIGHT),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_OUTWIT_PLAGUE_BAT,
    render = OUTWIT_PLAGUE_BAT,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
