import heroes.journey.modlib.Ids.*
import heroes.journey.modlib.attributes
import heroes.journey.modlib.challenge

// Undead Challenges - included by basegame mod
challenge(
    id = CHALLENGE_LAY_BOUND_CADAVER,
    render = BOUND_CADAVER,
    approaches = listOf(STAT_CONCENTRATION, STAT_LOGIC, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_BRITTLE_ARCHER,
    render = BRITTLE_ARCHER,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_CARCASS_FEEDER,
    render = CARCASS_FEEDER,
    approaches = listOf(STAT_BRAVADO, STAT_ENCHANTING, STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DESTROY_DECREPIT_BONES,
    render = DECREPIT_BONES,
    approaches = listOf(STAT_BRAVADO, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_DISMEMBERED_CRAWLER,
    render = DISMEMBERED_CRAWLER,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_GHASTLY_EYE,
    render = GHASTLY_EYE,
    approaches = listOf(STAT_BEWITCHING, STAT_ILLUSION, STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_GRAVE_REVENANT,
    render = GRAVE_REVENANT,
    approaches = listOf(STAT_CONCENTRATION, STAT_LOGIC, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_MUTILATED_STUMBLER,
    render = MUTILATED_STUMBLER,
    approaches = listOf(STAT_BRAVADO, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_SAND_GHOUL,
    render = SAND_GHOUL,
    approaches = listOf(STAT_BRAVADO, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_SKITTERING_HAND,
    render = SKITTERING_HAND,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_UNRAVELING_CRAWLER,
    render = UNRAVELING_CRAWLER,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_VAMPIRE_BAT,
    render = VAMPIRE_BAT,
    approaches = listOf(STAT_BEWITCHING, STAT_ILLUSION, STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_GIANT_ROYAL_SCARAB,
    render = GIANT_ROYAL_SCARAB,
    approaches = listOf(STAT_BRAVADO, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_ROYAL_SCARAB,
    render = ROYAL_SCARAB,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_TOXIC_HOUND,
    render = TOXIC_HOUND,
    approaches = listOf(STAT_BRAVADO, STAT_ENCHANTING, STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
