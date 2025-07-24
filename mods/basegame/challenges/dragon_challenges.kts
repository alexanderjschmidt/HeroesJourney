import heroes.journey.modlib.Ids
import heroes.journey.modlib.misc.challenge

// Dragon Challenges - included by basegame mod
// Organized by stat requirements (BODY, MIND, MAGIC, CHARISMA)

// BODY-based dragon challenges
challenge {
    id = Ids.CHALLENGE_BEFRIEND_AQUA_DRAKE
    render = Ids.AQUA_DRAKE
    stat(Ids.STAT_BODY)
}.register()

challenge {
    id = Ids.CHALLENGE_RESCUE_BABY_BRASS_DRAGON
    render = Ids.BABY_BRASS_DRAGON
    stat(Ids.STAT_BODY)
}.register()

challenge {
    id = Ids.CHALLENGE_HEAL_BABY_COPPER_DRAGON
    render = Ids.BABY_COPPER_DRAGON
    stat(Ids.STAT_BODY)
}.register()

// MIND-based dragon challenges
challenge {
    id = Ids.CHALLENGE_GUIDE_BABY_GREEN_DRAGON
    render = Ids.BABY_GREEN_DRAGON
    stat(Ids.STAT_MIND)
}.register()

// MAGIC-based dragon challenges
challenge {
    id = Ids.CHALLENGE_RESCUE_BABY_WHITE_DRAGON
    render = Ids.BABY_WHITE_DRAGON
    stat(Ids.STAT_MAGIC)
}.register()

challenge {
    id = Ids.CHALLENGE_TRAIN_JUVENILE_BRONZE_DRAGON
    render = Ids.JUVENILE_BRONZE_DRAGON
    stat(Ids.STAT_MAGIC)
}.register()

challenge {
    id = Ids.CHALLENGE_CHALLENGE_MATURE_BRONZE_DRAGON
    render = Ids.MATURE_BRONZE_DRAGON
    stat(Ids.STAT_MAGIC)
}.register()

challenge {
    id = Ids.CHALLENGE_DEFEAT_MUD_WYVERN
    render = Ids.MUD_WYVERN
    stat(Ids.STAT_MAGIC)
}.register()

challenge {
    id = Ids.CHALLENGE_OUTSMART_POISON_DRAKE
    render = Ids.POISON_DRAKE
    stat(Ids.STAT_MAGIC)
}.register()

// CHARISMA-based dragon challenges
challenge {
    id = Ids.CHALLENGE_BEFRIEND_PYGMY_WYVERN
    render = Ids.PYGMY_WYVERN
    stat(Ids.STAT_CHARISMA)
}.register()

challenge {
    id = Ids.CHALLENGE_GUIDE_VIRIDIAN_DRAKE
    render = Ids.VIRIDIAN_DRAKE
    stat(Ids.STAT_CHARISMA)
}.register()

challenge {
    id = Ids.CHALLENGE_CHALLENGE_YOUNG_BRASS_DRAGON
    render = Ids.YOUNG_BRASS_DRAGON
    stat(Ids.STAT_CHARISMA)
}.register()

challenge {
    id = Ids.CHALLENGE_DEFEAT_YOUNG_RED_DRAGON
    render = Ids.YOUNG_RED_DRAGON
    stat(Ids.STAT_CHARISMA)
}.register()

challenge {
    id = Ids.CHALLENGE_SURVIVE_ADULT_WHITE_DRAGON
    render = Ids.ADULT_WHITE_DRAGON
    stat(Ids.STAT_CHARISMA)
}.register()

challenge {
    id = Ids.CHALLENGE_NEGOTIATE_ADULT_GREEN_DRAGON
    render = Ids.ADULT_GREEN_DRAGON
    stat(Ids.STAT_CHARISMA)
}.register() 