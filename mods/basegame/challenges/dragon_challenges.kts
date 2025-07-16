import heroes.journey.modlib.Ids.*
import heroes.journey.modlib.attributes
import heroes.journey.modlib.challenge

// Dragon Challenges - included by basegame mod
challenge(
    id = CHALLENGE_BEFRIEND_AQUA_DRAKE,
    render = AQUA_DRAKE,
    approaches = listOf(STAT_BEWITCHING, STAT_CHARM, STAT_ENCHANTING),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_RESCUE_BABY_BRASS_DRAGON,
    render = BABY_BRASS_DRAGON,
    approaches = listOf(STAT_BRAVADO, STAT_CHIVALRY, STAT_LOGIC),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_HEAL_BABY_COPPER_DRAGON,
    render = BABY_COPPER_DRAGON,
    approaches = listOf(STAT_CONCENTRATION, STAT_ILLUSION, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_GUIDE_BABY_GREEN_DRAGON,
    render = BABY_GREEN_DRAGON,
    approaches = listOf(STAT_ENCHANTING, STAT_MESMERISM, STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_RESCUE_BABY_WHITE_DRAGON,
    render = BABY_WHITE_DRAGON,
    approaches = listOf(STAT_CHARM, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_TRAIN_JUVENILE_BRONZE_DRAGON,
    render = JUVENILE_BRONZE_DRAGON,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_CHALLENGE_MATURE_BRONZE_DRAGON,
    render = MATURE_BRONZE_DRAGON,
    approaches = listOf(STAT_CHARM, STAT_CHIVALRY, STAT_MIGHT),
    reward = attributes("valor" to 3)
).register()
challenge(
    id = CHALLENGE_DEFEAT_MUD_WYVERN,
    render = MUD_WYVERN,
    approaches = listOf(STAT_ILLUSION, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_OUTSMART_POISON_DRAKE,
    render = POISON_DRAKE,
    approaches = listOf(STAT_BEWITCHING, STAT_CUNNING, STAT_LOGIC),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_BEFRIEND_PYGMY_WYVERN,
    render = PYGMY_WYVERN,
    approaches = listOf(STAT_CHARM, STAT_MESMERISM, STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_GUIDE_VIRIDIAN_DRAKE,
    render = VIRIDIAN_DRAKE,
    approaches = listOf(STAT_BEWITCHING, STAT_ENCHANTING, STAT_LOGIC),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_CHALLENGE_YOUNG_BRASS_DRAGON,
    render = YOUNG_BRASS_DRAGON,
    approaches = listOf(STAT_BRAVADO, STAT_CHARM, STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_YOUNG_RED_DRAGON,
    render = YOUNG_RED_DRAGON,
    approaches = listOf(STAT_BRAVADO, STAT_CHARM, STAT_EMPOWERMENT),
    reward = attributes("valor" to 3)
).register()
challenge(
    id = CHALLENGE_SURVIVE_ADULT_WHITE_DRAGON,
    render = ADULT_WHITE_DRAGON,
    approaches = listOf(STAT_EMPOWERMENT, STAT_LOGIC, STAT_MIGHT),
    reward = attributes("valor" to 3)
).register()
challenge(
    id = CHALLENGE_NEGOTIATE_ADULT_GREEN_DRAGON,
    render = ADULT_GREEN_DRAGON,
    approaches = listOf(STAT_CHARM, STAT_MESMERISM, STAT_PERSUASION),
    reward = attributes("influence" to 3)
).register()
