import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Demon Challenges - included by basegame mod
challenge {
    id = Ids.CHALLENGE_BANISH_ANTLERED_RASCAL
    render = Ids.ANTLERED_RASCAL
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_PERSUASION)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_SUBDUE_CLAWED_ABOMINATION
    render = Ids.CLAWED_ABOMINATION
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_ENCHANTING)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_TRICK_CRIMSON_IMP
    render = Ids.CRIMSON_IMP
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_CAPTURE_DEPRAVED_BLACKGUARD
    render = Ids.DEPRAVED_BLACKGUARD
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_REDEEM_FLEDGLING_DEMON
    render = Ids.FLEDGLING_DEMON
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DESTROY_FLOATING_EYE
    render = Ids.FLOATING_EYE
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_STOP_FOUL_GOUGER
    render = Ids.FOUL_GOUGER
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_EMPOWERMENT)
    approach(Ids.STAT_PERSUASION)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_OUTWIT_GRINNING_GREMLIN
    render = Ids.GRINNING_GREMLIN
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_CATCH_NEFARIOUS_SCAMP
    render = Ids.NEFARIOUS_SCAMP
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DUEL_PIT_BALOR
    render = Ids.PIT_BALOR
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 3) }
}.register()
challenge {
    id = Ids.CHALLENGE_BANISH_POINTED_DEMONSPAWN
    render = Ids.POINTED_DEMONSPAWN
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_PERSUASION)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DISCIPLINE_RASCALLY_DEMONLING
    render = Ids.RASCALLY_DEMONLING
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_HUNT_SKEWERING_STALKER
    render = Ids.SKEWERING_STALKER
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_REFORM_TAINTED_SCOUNDREL
    render = Ids.TAINTED_SCOUNDREL
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_SHATTER_WARP_SKULL
    render = Ids.WARP_SKULL
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_SORCERY)
    approach(Ids.STAT_BEWITCHING)
    reward { stat("arcanum", 2) }
}.register()
