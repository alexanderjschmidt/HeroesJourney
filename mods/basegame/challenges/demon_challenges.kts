import heroes.journey.modlib.Ids
import heroes.journey.modlib.IdsC
import heroes.journey.modlib.misc.challenge

// Demon Challenges - included by basegame mod
// Organized by stat requirements (BODY, MIND, MAGIC, CHARISMA)

// BODY-based demon challenges
challenge {
    id = IdsC.CHALLENGE_DEPRAVED_BLACKGUARD
    render = IdsC.RENDER_DEPRAVED_BLACKGUARD
    stat(Ids.STAT_BODY)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_PIT_BALOR
    render = IdsC.RENDER_PIT_BALOR
    stat(Ids.STAT_BODY)
    stat(Ids.STAT_DEMON_RACE)
}.register()

// MIND-based demon challenges
challenge {
    id = IdsC.CHALLENGE_ANTLERED_RASCAL
    render = IdsC.RENDER_ANTLERED_RASCAL
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_CRIMSON_IMP
    render = IdsC.RENDER_CRIMSON_IMP
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_GRINNING_GREMLIN
    render = IdsC.RENDER_GRINNING_GREMLIN
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_NEFARIOUS_SCAMP
    render = IdsC.RENDER_NEFARIOUS_SCAMP
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_TAINTED_SCOUNDREL
    render = IdsC.RENDER_TAINTED_SCOUNDREL
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_DEMON_RACE)
}.register()

// MAGIC-based demon challenges
challenge {
    id = IdsC.CHALLENGE_CLAWED_ABOMINATION
    render = IdsC.RENDER_CLAWED_ABOMINATION
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_FLOATING_EYE
    render = IdsC.RENDER_FLOATING_EYE
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_FLEDGLING_DEMON
    render = IdsC.RENDER_FLEDGLING_DEMON
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_FOUL_GOUGER
    render = IdsC.RENDER_FOUL_GOUGER
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_DEMON_RACE)
}.register()

// CHARISMA-based demon challenges
challenge {
    id = IdsC.CHALLENGE_RASCALLY_DEMONLING
    render = IdsC.RENDER_RASCALLY_DEMONLING
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_SKEWERING_STALKER
    render = IdsC.RENDER_SKEWERING_STALKER
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_WARP_SKULL
    render = IdsC.RENDER_WARP_SKULL
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_DEMON_RACE)
}.register()

challenge {
    id = IdsC.CHALLENGE_POINTED_DEMONSPAWN
    render = IdsC.RENDER_POINTED_DEMONSPAWN
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_DEMON_RACE)
}.register()

