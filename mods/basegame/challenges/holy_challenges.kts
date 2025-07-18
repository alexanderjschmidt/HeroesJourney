import heroes.journey.modlib.Ids.*
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Holy Challenges - included by basegame mod
challenge(
    id = CHALLENGE_AID_RESOLUTE_ANGEL,
    render = RESOLUTE_ANGEL,
    approaches = listOf(STAT_CHIVALRY, STAT_EMPOWERMENT, STAT_ILLUSION),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_BLESS_BLESSED_GLADIATOR,
    render = BLESSED_GLADIATOR,
    approaches = listOf(STAT_CONCENTRATION, STAT_ENCHANTING, STAT_PERSUASION),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_TRAIN_BOLD_MAN_AT_ARMS,
    render = BOLD_MAN_AT_ARMS,
    approaches = listOf(STAT_CHARM, STAT_CHIVALRY, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_COMMUNE_WITH_DIVINE_PLANETAR,
    render = DIVINE_PLANETAR,
    approaches = listOf(STAT_CHARM, STAT_CONCENTRATION, STAT_SORCERY),
    reward = attributes("arcanum" to 3)
).register()
challenge(
    id = CHALLENGE_GUIDE_DEVOUT_ACOLYTE,
    render = DEVOUT_ACOLYTE,
    approaches = listOf(STAT_CONCENTRATION, STAT_ILLUSION, STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_HEAL_FAVORED_CLERIC,
    render = FAVORED_CLERIC,
    approaches = listOf(STAT_EMPOWERMENT, STAT_ENCHANTING, STAT_LOGIC),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_BEFRIEND_FLOATING_CHERUB,
    render = FLOATING_CHERUB,
    approaches = listOf(STAT_BEWITCHING, STAT_CHARM, STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_GUIDE_GENTLE_SHEPARD,
    render = GENTLE_SHEPARD,
    approaches = listOf(STAT_CHARM, STAT_LOGIC, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_JOIN_HOLY_CRUSADER,
    render = HOLY_CRUSADER,
    approaches = listOf(STAT_CHARM, STAT_CHIVALRY, STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_LEARN_FROM_JOVIAL_FRIAR,
    render = JOVIAL_FRIAR,
    approaches = listOf(STAT_CHARM, STAT_LOGIC, STAT_PERSUASION),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_COMMUNE_WITH_RIGHTEOUS_DEVA,
    render = RIGHTEOUS_DEVA,
    approaches = listOf(STAT_CHARM, STAT_LOGIC, STAT_SORCERY),
    reward = attributes("arcanum" to 3)
).register()
challenge(
    id = CHALLENGE_TRAIN_WITH_SWORD_ARCHON,
    render = SWORD_ARCHON,
    approaches = listOf(STAT_LOGIC, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_HONOR_VETERAN_SWORDSMAN,
    render = VETERAN_SWORDSMAN,
    approaches = listOf(STAT_CHARM, STAT_CHIVALRY, STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_GUIDE_ZEALOUS_PRIEST,
    render = ZEALOUS_PRIEST,
    approaches = listOf(STAT_BRAVADO, STAT_MESMERISM, STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_INSPIRE_DETERMINED_SOLDIER,
    render = DETERMINED_SOLDIER,
    approaches = listOf(STAT_BRAVADO, STAT_LOGIC, STAT_MESMERISM),
    reward = attributes("valor" to 2)
).register()
