import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Dragon Challenges - included by basegame mod
challenge(
    id = Ids.CHALLENGE_BEFRIEND_AQUA_DRAKE,
    render = Ids.AQUA_DRAKE,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_ENCHANTING),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_RESCUE_BABY_BRASS_DRAGON,
    render = Ids.BABY_BRASS_DRAGON,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_CHIVALRY, Ids.STAT_LOGIC),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_HEAL_BABY_COPPER_DRAGON,
    render = Ids.BABY_COPPER_DRAGON,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_ILLUSION, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_GUIDE_BABY_GREEN_DRAGON,
    render = Ids.BABY_GREEN_DRAGON,
    approaches = listOf(Ids.STAT_ENCHANTING, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_RESCUE_BABY_WHITE_DRAGON,
    render = Ids.BABY_WHITE_DRAGON,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_TRAIN_JUVENILE_BRONZE_DRAGON,
    render = Ids.JUVENILE_BRONZE_DRAGON,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_CHALLENGE_MATURE_BRONZE_DRAGON,
    render = Ids.MATURE_BRONZE_DRAGON,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CHIVALRY, Ids.STAT_MIGHT),
    reward = attributes("valor" to 3)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_MUD_WYVERN,
    render = Ids.MUD_WYVERN,
    approaches = listOf(Ids.STAT_ILLUSION, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_OUTSMART_POISON_DRAKE,
    render = Ids.POISON_DRAKE,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_CUNNING, Ids.STAT_LOGIC),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BEFRIEND_PYGMY_WYVERN,
    render = Ids.PYGMY_WYVERN,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_GUIDE_VIRIDIAN_DRAKE,
    render = Ids.VIRIDIAN_DRAKE,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_ENCHANTING, Ids.STAT_LOGIC),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_CHALLENGE_YOUNG_BRASS_DRAGON,
    render = Ids.YOUNG_BRASS_DRAGON,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_CHARM, Ids.STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_YOUNG_RED_DRAGON,
    render = Ids.YOUNG_RED_DRAGON,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_CHARM, Ids.STAT_EMPOWERMENT),
    reward = attributes("valor" to 3)
).register()
challenge(
    id = Ids.CHALLENGE_SURVIVE_ADULT_WHITE_DRAGON,
    render = Ids.ADULT_WHITE_DRAGON,
    approaches = listOf(Ids.STAT_EMPOWERMENT, Ids.STAT_LOGIC, Ids.STAT_MIGHT),
    reward = attributes("valor" to 3)
).register()
challenge(
    id = Ids.CHALLENGE_NEGOTIATE_ADULT_GREEN_DRAGON,
    render = Ids.ADULT_GREEN_DRAGON,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 3)
).register()
