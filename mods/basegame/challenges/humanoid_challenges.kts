import heroes.journey.modlib.Ids.*
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Humanoid Challenges - included by basegame mod
challenge(
    id = CHALLENGE_OUTMANEUVER_GOBLIN_ARCHER,
    render = GOBLIN_ARCHER,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_CALM_GOBLIN_FANATIC,
    render = GOBLIN_FANATIC,
    approaches = listOf(STAT_BEWITCHING, STAT_CHARM, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_GOBLIN_FIGHTER,
    render = GOBLIN_FIGHTER,
    approaches = listOf(STAT_CHIVALRY, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_EXPOSE_GOBLIN_OCCULTIST,
    render = GOBLIN_OCCULTIST,
    approaches = listOf(STAT_CONCENTRATION, STAT_LOGIC, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_OUTRUN_GOBLIN_WOLF_RIDER,
    render = GOBLIN_WOLF_RIDER,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_CAPTURE_HALFLING_ASSASSIN,
    render = HALFLING_ASSASSIN,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_PERFORM_WITH_HALFLING_BARD,
    render = HALFLING_BARD,
    approaches = listOf(STAT_BEWITCHING, STAT_CHARM, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_TRACK_WITH_HALFLING_RANGER,
    render = HALFLING_RANGER,
    approaches = listOf(STAT_LOGIC, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_OUTWIT_HALFLING_ROGUE,
    render = HALFLING_ROGUE,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_COMPETE_WITH_HALFLING_SLINGER,
    render = HALFLING_SLINGER,
    approaches = listOf(STAT_CHARM, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_SCOUT_WITH_LIZARDFOLK_SCOUT,
    render = LIZARDFOLK_SCOUT,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_SKILL),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_SPAR_LIZARDFOLK_GLADIATOR,
    render = LIZARDFOLK_GLADIATOR,
    approaches = listOf(STAT_CHARM, STAT_CHIVALRY, STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_TRAIN_LIZARDFOLK_ARCHER,
    render = LIZARDFOLK_ARCHER,
    approaches = listOf(STAT_PERSUASION, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_TAME_BESTIAL_LIZARDFOLK,
    render = BESTIAL_LIZARDFOLK,
    approaches = listOf(STAT_BRAVADO, STAT_MESMERISM, STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_TRAIN_LIZARDFOLK_SPEARMAN,
    render = LIZARDFOLK_SPEARMAN,
    approaches = listOf(STAT_MIGHT, STAT_PERSUASION, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
