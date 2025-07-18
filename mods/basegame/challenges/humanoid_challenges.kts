import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Humanoid Challenges - included by basegame mod
challenge(
    id = Ids.CHALLENGE_OUTMANEUVER_GOBLIN_ARCHER,
    render = Ids.GOBLIN_ARCHER,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_CALM_GOBLIN_FANATIC,
    render = Ids.GOBLIN_FANATIC,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_GOBLIN_FIGHTER,
    render = Ids.GOBLIN_FIGHTER,
    approaches = listOf(Ids.STAT_CHIVALRY, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_EXPOSE_GOBLIN_OCCULTIST,
    render = Ids.GOBLIN_OCCULTIST,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_OUTRUN_GOBLIN_WOLF_RIDER,
    render = Ids.GOBLIN_WOLF_RIDER,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_CAPTURE_HALFLING_ASSASSIN,
    render = Ids.HALFLING_ASSASSIN,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_PERFORM_WITH_HALFLING_BARD,
    render = Ids.HALFLING_BARD,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_TRACK_WITH_HALFLING_RANGER,
    render = Ids.HALFLING_RANGER,
    approaches = listOf(Ids.STAT_LOGIC, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_OUTWIT_HALFLING_ROGUE,
    render = Ids.HALFLING_ROGUE,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_COMPETE_WITH_HALFLING_SLINGER,
    render = Ids.HALFLING_SLINGER,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_SCOUT_WITH_LIZARDFOLK_SCOUT,
    render = Ids.LIZARDFOLK_SCOUT,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_SKILL),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_SPAR_LIZARDFOLK_GLADIATOR,
    render = Ids.LIZARDFOLK_GLADIATOR,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CHIVALRY, Ids.STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_TRAIN_LIZARDFOLK_ARCHER,
    render = Ids.LIZARDFOLK_ARCHER,
    approaches = listOf(Ids.STAT_PERSUASION, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_TAME_BESTIAL_LIZARDFOLK,
    render = Ids.BESTIAL_LIZARDFOLK,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_TRAIN_LIZARDFOLK_SPEARMAN,
    render = Ids.LIZARDFOLK_SPEARMAN,
    approaches = listOf(Ids.STAT_MIGHT, Ids.STAT_PERSUASION, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
