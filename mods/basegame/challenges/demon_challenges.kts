import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Demon Challenges - included by basegame mod
challenge(
    id = Ids.CHALLENGE_BANISH_ANTLERED_RASCAL,
    render = Ids.ANTLERED_RASCAL,
    approaches = listOf(Ids.STAT_ILLUSION, Ids.STAT_LOGIC, Ids.STAT_PERSUASION),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_SUBDUE_CLAWED_ABOMINATION,
    render = Ids.CLAWED_ABOMINATION,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CONCENTRATION, Ids.STAT_ENCHANTING),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_TRICK_CRIMSON_IMP,
    render = Ids.CRIMSON_IMP,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_ILLUSION, Ids.STAT_TECHNIQUE),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_CAPTURE_DEPRAVED_BLACKGUARD,
    render = Ids.DEPRAVED_BLACKGUARD,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_MIGHT, Ids.STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_REDEEM_FLEDGLING_DEMON,
    render = Ids.FLEDGLING_DEMON,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_LOGIC, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DESTROY_FLOATING_EYE,
    render = Ids.FLOATING_EYE,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_ILLUSION, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_STOP_FOUL_GOUGER,
    render = Ids.FOUL_GOUGER,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_EMPOWERMENT, Ids.STAT_PERSUASION),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_OUTWIT_GRINNING_GREMLIN,
    render = Ids.GRINNING_GREMLIN,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_CATCH_NEFARIOUS_SCAMP,
    render = Ids.NEFARIOUS_SCAMP,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_ILLUSION, Ids.STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DUEL_PIT_BALOR,
    render = Ids.PIT_BALOR,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_CHARM, Ids.STAT_MIGHT),
    reward = attributes("valor" to 3)
).register()
challenge(
    id = Ids.CHALLENGE_BANISH_POINTED_DEMONSPAWN,
    render = Ids.POINTED_DEMONSPAWN,
    approaches = listOf(Ids.STAT_ENCHANTING, Ids.STAT_PERSUASION, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DISCIPLINE_RASCALLY_DEMONLING,
    render = Ids.RASCALLY_DEMONLING,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_LOGIC, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_HUNT_SKEWERING_STALKER,
    render = Ids.SKEWERING_STALKER,
    approaches = listOf(Ids.STAT_ILLUSION, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_REFORM_TAINTED_SCOUNDREL,
    render = Ids.TAINTED_SCOUNDREL,
    approaches = listOf(Ids.STAT_BRAVADO, Ids.STAT_CUNNING, Ids.STAT_LOGIC),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_SHATTER_WARP_SKULL,
    render = Ids.WARP_SKULL,
    approaches = listOf(Ids.STAT_ILLUSION, Ids.STAT_SORCERY, Ids.STAT_BEWITCHING),
    reward = attributes("arcanum" to 2)
).register()
