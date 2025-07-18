import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Vermin Challenges - included by basegame mod
challenge(
    id = Ids.CHALLENGE_OUTSMART_GIANT_ANT,
    render = Ids.OUTSMART_GIANT_ANT,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_CRUSH_BEDBUG_SWARM,
    render = Ids.CRUSH_BEDBUG_SWARM,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_HUNT_TUNNELING_MOLE,
    render = Ids.HUNT_TUNNELING_MOLE,
    approaches = listOf(Ids.STAT_MIGHT, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BEFRIEND_DUNG_BEETLE,
    render = Ids.BEFRIEND_DUNG_BEETLE,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_ENCHANTING, Ids.STAT_MIGHT),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_OUTWIT_PLAGUE_BAT,
    render = Ids.OUTWIT_PLAGUE_BAT,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
