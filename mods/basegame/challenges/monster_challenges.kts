import heroes.journey.modlib.Ids.*
import heroes.journey.modlib.attributes
import heroes.journey.modlib.challenge

// Monster Challenges - included by basegame mod
challenge(
    id = CHALLENGE_GUIDE_BLINDED_GRIMLOCK,
    render = BLINDED_GRIMLOCK,
    approaches = listOf(STAT_CHARM, STAT_CUNNING, STAT_SKILL),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_BLOODSHOT_EYE,
    render = BLOODSHOT_EYE,
    approaches = listOf(STAT_BEWITCHING, STAT_ILLUSION, STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_BRAWNY_OGRE,
    render = BRAWNY_OGRE,
    approaches = listOf(STAT_BRAVADO, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_OUTSMART_CRIMSON_SLAAD,
    render = CRIMSON_SLAAD,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_CRUSHING_CYCLOPS,
    render = CRUSHING_CYCLOPS,
    approaches = listOf(STAT_BRAVADO, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_PURIFY_DEATH_SLIME,
    render = DEATH_SLIME,
    approaches = listOf(STAT_CONCENTRATION, STAT_LOGIC, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_BEFRIEND_FUNGAL_MYCONID,
    render = FUNGAL_MYCONID,
    approaches = listOf(STAT_CHARM, STAT_ENCHANTING, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_HUMONGOUS_ETTIN,
    render = HUMONGOUS_ETTIN,
    approaches = listOf(STAT_BRAVADO, STAT_ENCHANTING, STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_OUTSMART_MURKY_SLAAD,
    render = MURKY_SLAAD,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_OCHRE_JELLY,
    render = OCHRE_JELLY,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_OCULAR_WATCHER,
    render = OCULAR_WATCHER,
    approaches = listOf(STAT_BEWITCHING, STAT_ILLUSION, STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_OUTSMART_RED_CAP,
    render = RED_CAP,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_SHRIEKER_MUSHROOM,
    render = SHRIEKER_MUSHROOM,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_STONE_TROLL,
    render = STONE_TROLL,
    approaches = listOf(STAT_CHIVALRY, STAT_ENCHANTING, STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_DEFEAT_SWAMP_TROLL,
    render = SWAMP_TROLL,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
