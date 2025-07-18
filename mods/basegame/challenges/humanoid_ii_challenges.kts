import heroes.journey.modlib.Ids.*
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Humanoid II Challenges - included by basegame mod
challenge(
    id = CHALLENGE_MENTOR_ADVENTUROUS_ADOLESCENT,
    render = ADVENTUROUS_ADOLESCENT,
    approaches = listOf(STAT_CHARM, STAT_MESMERISM, STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_MENTOR_BOISTEROUS_YOUTH,
    render = BOISTEROUS_YOUTH,
    approaches = listOf(STAT_CHARM, STAT_MESMERISM, STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_LEARN_ELF_BLADEDANCER,
    render = ELF_BLADEDANCER,
    approaches = listOf(STAT_ENCHANTING, STAT_LOGIC, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_STUDY_ELF_ENCHANTER,
    render = ELF_ENCHANTER,
    approaches = listOf(STAT_CHARM, STAT_CONCENTRATION, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_NEGOTIATE_ELF_LORD,
    render = ELF_LORD,
    approaches = listOf(STAT_CHARM, STAT_MESMERISM, STAT_PERSUASION),
    reward = attributes("influence" to 3)
).register()
challenge(
    id = CHALLENGE_COMPETE_ELF_SHARPSHOOTER,
    render = ELF_SHARPSHOOTER,
    approaches = listOf(STAT_LOGIC, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_TRAVEL_ELF_WAYFARER,
    render = ELF_WAYFARER,
    approaches = listOf(STAT_CHARM, STAT_CUNNING, STAT_SKILL),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_PLAY_JOYFUL_KID,
    render = JOYFUL_KID,
    approaches = listOf(STAT_BEWITCHING, STAT_CHARM, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_LEARN_MERFOLK_AQUAMANCER,
    render = MERFOLK_AQUAMANCER,
    approaches = listOf(STAT_CONCENTRATION, STAT_LOGIC, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_SPAR_MERFOLK_IMPALER,
    render = MERFOLK_IMPALER,
    approaches = listOf(STAT_CHARM, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_COMPETE_MERFOLK_JAVELINEER,
    render = MERFOLK_JAVELINEER,
    approaches = listOf(STAT_LOGIC, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_STUDY_MERFOLK_MYSTIC,
    render = MERFOLK_MYSTIC,
    approaches = listOf(STAT_CHARM, STAT_CONCENTRATION, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_SCOUT_MERFOLK_SCOUT,
    render = MERFOLK_SCOUT,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_SKILL),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_HELP_OVERWORKED_VILLAGER,
    render = OVERWORKED_VILLAGER,
    approaches = listOf(STAT_CHARM, STAT_CUNNING, STAT_LOGIC),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_PLAY_PLAYFUL_CHILD,
    render = PLAYFUL_CHILD,
    approaches = listOf(STAT_BEWITCHING, STAT_CHARM, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
