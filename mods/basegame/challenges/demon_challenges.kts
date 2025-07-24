import heroes.journey.modlib.Ids
import heroes.journey.modlib.misc.challenge

// Demon Challenges - included by basegame mod
// Organized by stat requirements (BODY, MIND, MAGIC, CHARISMA)

// BODY-based demon challenges
challenge {
    id = Ids.CHALLENGE_CAPTURE_DEPRAVED_BLACKGUARD
    render = Ids.DEPRAVED_BLACKGUARD
    stat(Ids.STAT_BODY)
}.register()

challenge {
    id = Ids.CHALLENGE_DUEL_PIT_BALOR
    render = Ids.PIT_BALOR
    stat(Ids.STAT_BODY)
}.register()

// MIND-based demon challenges
challenge {
    id = Ids.CHALLENGE_BANISH_ANTLERED_RASCAL
    render = Ids.ANTLERED_RASCAL
    stat(Ids.STAT_MIND)
}.register()

challenge {
    id = Ids.CHALLENGE_TRICK_CRIMSON_IMP
    render = Ids.CRIMSON_IMP
    stat(Ids.STAT_MIND)
}.register()

challenge {
    id = Ids.CHALLENGE_OUTWIT_GRINNING_GREMLIN
    render = Ids.GRINNING_GREMLIN
    stat(Ids.STAT_MIND)
}.register()

challenge {
    id = Ids.CHALLENGE_CATCH_NEFARIOUS_SCAMP
    render = Ids.NEFARIOUS_SCAMP
    stat(Ids.STAT_MIND)
}.register()

challenge {
    id = Ids.CHALLENGE_REFORM_TAINTED_SCOUNDREL
    render = Ids.TAINTED_SCOUNDREL
    stat(Ids.STAT_MIND)
}.register()

// MAGIC-based demon challenges
challenge {
    id = Ids.CHALLENGE_SUBDUE_CLAWED_ABOMINATION
    render = Ids.CLAWED_ABOMINATION
    stat(Ids.STAT_MAGIC)
}.register()

challenge {
    id = Ids.CHALLENGE_DESTROY_FLOATING_EYE
    render = Ids.FLOATING_EYE
    stat(Ids.STAT_MAGIC)
}.register()

challenge {
    id = Ids.CHALLENGE_REDEEM_FLEDGLING_DEMON
    render = Ids.FLEDGLING_DEMON
    stat(Ids.STAT_MAGIC)
}.register()

challenge {
    id = Ids.CHALLENGE_STOP_FOUL_GOUGER
    render = Ids.FOUL_GOUGER
    stat(Ids.STAT_MAGIC)
}.register()

// CHARISMA-based demon challenges
challenge {
    id = Ids.CHALLENGE_DISCIPLINE_RASCALLY_DEMONLING
    render = Ids.RASCALLY_DEMONLING
    stat(Ids.STAT_CHARISMA)
}.register()

challenge {
    id = Ids.CHALLENGE_HUNT_SKEWERING_STALKER
    render = Ids.SKEWERING_STALKER
    stat(Ids.STAT_CHARISMA)
}.register()

challenge {
    id = Ids.CHALLENGE_SHATTER_WARP_SKULL
    render = Ids.WARP_SKULL
    stat(Ids.STAT_CHARISMA)
}.register()

challenge {
    id = Ids.CHALLENGE_BANISH_POINTED_DEMONSPAWN
    render = Ids.POINTED_DEMONSPAWN
    stat(Ids.STAT_CHARISMA)
}.register()

