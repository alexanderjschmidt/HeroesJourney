import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Vermin Challenges - included by basegame mod
challenge {
    id = Ids.CHALLENGE_OUTSMART_GIANT_ANT
    render = Ids.OUTSMART_GIANT_ANT
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_CRUSH_BEDBUG_SWARM
    render = Ids.CRUSH_BEDBUG_SWARM
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_HUNT_TUNNELING_MOLE
    render = Ids.HUNT_TUNNELING_MOLE
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BEFRIEND_DUNG_BEETLE
    render = Ids.BEFRIEND_DUNG_BEETLE
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_MIGHT)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_OUTWIT_PLAGUE_BAT
    render = Ids.OUTWIT_PLAGUE_BAT
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("insight", 2) }
}.register()
