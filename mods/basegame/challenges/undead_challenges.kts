import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Undead Challenges - included by basegame mod
challenge(
    id = Ids.CHALLENGE_LAY_BOUND_CADAVER,
    render = Ids.BOUND_CADAVER,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_BRITTLE_ARCHER,
    render = Ids.BRITTLE_ARCHER,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_CARCASS_FEEDER,
    render = Ids.CARCASS_FEEDER,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_ENCHANTING, Ids.STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DESTROY_DECREPIT_BONES,
    render = Ids.DECREPIT_BONES,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_DISMEMBERED_CRAWLER,
    render = Ids.DISMEMBERED_CRAWLER,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_GHASTLY_EYE,
    render = Ids.GHASTLY_EYE,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_ILLUSION, Ids.STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_GRAVE_REVENANT,
    render = Ids.GRAVE_REVENANT,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_MUTILATED_STUMBLER,
    render = Ids.MUTILATED_STUMBLER,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_SAND_GHOUL,
    render = Ids.SAND_GHOUL,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_SKITTERING_HAND,
    render = Ids.SKITTERING_HAND,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_UNRAVELING_CRAWLER,
    render = Ids.UNRAVELING_CRAWLER,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_VAMPIRE_BAT,
    render = Ids.VAMPIRE_BAT,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_ILLUSION, Ids.STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_GIANT_ROYAL_SCARAB,
    render = Ids.GIANT_ROYAL_SCARAB,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_ROYAL_SCARAB,
    render = Ids.ROYAL_SCARAB,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_TOXIC_HOUND,
    render = Ids.TOXIC_HOUND,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_ENCHANTING, Ids.STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
