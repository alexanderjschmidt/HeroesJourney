import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Monster Challenges - included by basegame mod
challenge(
    id = Ids.CHALLENGE_GUIDE_BLINDED_GRIMLOCK,
    render = Ids.BLINDED_GRIMLOCK,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CUNNING, Ids.STAT_SKILL),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_BLOODSHOT_EYE,
    render = Ids.BLOODSHOT_EYE,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_ILLUSION, Ids.STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_BRAWNY_OGRE,
    render = Ids.BRAWNY_OGRE,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_OUTSMART_CRIMSON_SLAAD,
    render = Ids.CRIMSON_SLAAD,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_CRUSHING_CYCLOPS,
    render = Ids.CRUSHING_CYCLOPS,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_PURIFY_DEATH_SLIME,
    render = Ids.DEATH_SLIME,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BEFRIEND_FUNGAL_MYCONID,
    render = Ids.FUNGAL_MYCONID,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_ENCHANTING, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_HUMONGOUS_ETTIN,
    render = Ids.HUMONGOUS_ETTIN,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_ENCHANTING, Ids.STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_OUTSMART_MURKY_SLAAD,
    render = Ids.MURKY_SLAAD,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_OCHRE_JELLY,
    render = Ids.OCHRE_JELLY,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_OCULAR_WATCHER,
    render = Ids.OCULAR_WATCHER,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_ILLUSION, Ids.STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_OUTSMART_RED_CAP,
    render = Ids.RED_CAP,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_SHRIEKER_MUSHROOM,
    render = Ids.SHRIEKER_MUSHROOM,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_STONE_TROLL,
    render = Ids.STONE_TROLL,
    approaches = listOf(Ids.STAT_CHIVALRY, Ids.STAT_ENCHANTING, Ids.STAT_MIGHT),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DEFEAT_SWAMP_TROLL,
    render = Ids.SWAMP_TROLL,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
