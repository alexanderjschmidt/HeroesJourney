import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Holy Challenges - included by basegame mod
challenge(
    id = Ids.CHALLENGE_AID_RESOLUTE_ANGEL,
    render = Ids.RESOLUTE_ANGEL,
    approaches = listOf(Ids.STAT_CHIVALRY, Ids.STAT_EMPOWERMENT, Ids.STAT_ILLUSION),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BLESS_BLESSED_GLADIATOR,
    render = Ids.BLESSED_GLADIATOR,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_ENCHANTING, Ids.STAT_PERSUASION),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_TRAIN_BOLD_MAN_AT_ARMS,
    render = Ids.BOLD_MAN_AT_ARMS,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CHIVALRY, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_COMMUNE_WITH_DIVINE_PLANETAR,
    render = Ids.DIVINE_PLANETAR,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CONCENTRATION, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 3)
).register()
challenge(
    id = Ids.CHALLENGE_GUIDE_DEVOUT_ACOLYTE,
    render = Ids.DEVOUT_ACOLYTE,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_ILLUSION, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_HEAL_FAVORED_CLERIC,
    render = Ids.FAVORED_CLERIC,
    approaches = listOf(Ids.STAT_EMPOWERMENT, Ids.STAT_ENCHANTING, Ids.STAT_LOGIC),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BEFRIEND_FLOATING_CHERUB,
    render = Ids.FLOATING_CHERUB,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_GUIDE_GENTLE_SHEPARD,
    render = Ids.GENTLE_SHEPARD,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_LOGIC, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_JOIN_HOLY_CRUSADER,
    render = Ids.HOLY_CRUSADER,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CHIVALRY, Ids.STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_LEARN_FROM_JOVIAL_FRIAR,
    render = Ids.JOVIAL_FRIAR,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_LOGIC, Ids.STAT_PERSUASION),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_COMMUNE_WITH_RIGHTEOUS_DEVA,
    render = Ids.RIGHTEOUS_DEVA,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_LOGIC, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 3)
).register()
challenge(
    id = Ids.CHALLENGE_TRAIN_WITH_SWORD_ARCHON,
    render = Ids.SWORD_ARCHON,
    approaches = listOf(Ids.STAT_LOGIC, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_HONOR_VETERAN_SWORDSMAN,
    render = Ids.VETERAN_SWORDSMAN,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CHIVALRY, Ids.STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_GUIDE_ZEALOUS_PRIEST,
    render = Ids.ZEALOUS_PRIEST,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_INSPIRE_DETERMINED_SOLDIER,
    render = Ids.DETERMINED_SOLDIER,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_LOGIC, Ids.STAT_MESMERISM),
    reward = attributes("valor" to 2)
).register()
