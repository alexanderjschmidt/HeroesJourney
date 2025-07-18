import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Humanoid II Challenges - included by basegame mod
challenge(
    id = Ids.CHALLENGE_MENTOR_ADVENTUROUS_ADOLESCENT,
    render = Ids.ADVENTUROUS_ADOLESCENT,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_MENTOR_BOISTEROUS_YOUTH,
    render = Ids.BOISTEROUS_YOUTH,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_LEARN_ELF_BLADEDANCER,
    render = Ids.ELF_BLADEDANCER,
    approaches = listOf(Ids.STAT_ENCHANTING, Ids.STAT_LOGIC, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_STUDY_ELF_ENCHANTER,
    render = Ids.ELF_ENCHANTER,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CONCENTRATION, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_NEGOTIATE_ELF_LORD,
    render = Ids.ELF_LORD,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION),
    reward = attributes("influence" to 3)
).register()
challenge(
    id = Ids.CHALLENGE_COMPETE_ELF_SHARPSHOOTER,
    render = Ids.ELF_SHARPSHOOTER,
    approaches = listOf(Ids.STAT_LOGIC, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_TRAVEL_ELF_WAYFARER,
    render = Ids.ELF_WAYFARER,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CUNNING, Ids.STAT_SKILL),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_PLAY_JOYFUL_KID,
    render = Ids.JOYFUL_KID,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_LEARN_MERFOLK_AQUAMANCER,
    render = Ids.MERFOLK_AQUAMANCER,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_SPAR_MERFOLK_IMPALER,
    render = Ids.MERFOLK_IMPALER,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_COMPETE_MERFOLK_JAVELINEER,
    render = Ids.MERFOLK_JAVELINEER,
    approaches = listOf(Ids.STAT_LOGIC, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_STUDY_MERFOLK_MYSTIC,
    render = Ids.MERFOLK_MYSTIC,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CONCENTRATION, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_SCOUT_MERFOLK_SCOUT,
    render = Ids.MERFOLK_SCOUT,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_SKILL),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_HELP_OVERWORKED_VILLAGER,
    render = Ids.OVERWORKED_VILLAGER,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CUNNING, Ids.STAT_LOGIC),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_PLAY_PLAYFUL_CHILD,
    render = Ids.PLAYFUL_CHILD,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
