import heroes.journey.modlib.Ids.*
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Demon Challenges - included by basegame mod
challenge(
    id = CHALLENGE_BANISH_ANTLERED_RASCAL,
    render = ANTLERED_RASCAL,
    approaches = listOf(STAT_ILLUSION, STAT_LOGIC, STAT_PERSUASION),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_SUBDUE_CLAWED_ABOMINATION,
    render = CLAWED_ABOMINATION,
    approaches = listOf(STAT_CHARM, STAT_CONCENTRATION, STAT_ENCHANTING),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_TRICK_CRIMSON_IMP,
    render = CRIMSON_IMP,
    approaches = listOf(STAT_CUNNING, STAT_ILLUSION, STAT_TECHNIQUE),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_CAPTURE_DEPRAVED_BLACKGUARD,
    render = DEPRAVED_BLACKGUARD,
    approaches = listOf(STAT_CHARM, STAT_MIGHT, STAT_SKILL),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_REDEEM_FLEDGLING_DEMON,
    render = FLEDGLING_DEMON,
    approaches = listOf(STAT_BEWITCHING, STAT_LOGIC, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_DESTROY_FLOATING_EYE,
    render = FLOATING_EYE,
    approaches = listOf(STAT_CONCENTRATION, STAT_ILLUSION, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_STOP_FOUL_GOUGER,
    render = FOUL_GOUGER,
    approaches = listOf(STAT_CHARM, STAT_EMPOWERMENT, STAT_PERSUASION),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_OUTWIT_GRINNING_GREMLIN,
    render = GRINNING_GREMLIN,
    approaches = listOf(STAT_CUNNING, STAT_LOGIC, STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_CATCH_NEFARIOUS_SCAMP,
    render = NEFARIOUS_SCAMP,
    approaches = listOf(STAT_CUNNING, STAT_ILLUSION, STAT_TECHNIQUE),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_DUEL_PIT_BALOR,
    render = PIT_BALOR,
    approaches = listOf(STAT_BRAVADO, STAT_CHARM, STAT_MIGHT),
    reward = attributes("valor" to 3)
).register()
challenge(
    id = CHALLENGE_BANISH_POINTED_DEMONSPAWN,
    render = POINTED_DEMONSPAWN,
    approaches = listOf(STAT_ENCHANTING, STAT_PERSUASION, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_DISCIPLINE_RASCALLY_DEMONLING,
    render = RASCALLY_DEMONLING,
    approaches = listOf(STAT_CHARM, STAT_LOGIC, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_HUNT_SKEWERING_STALKER,
    render = SKEWERING_STALKER,
    approaches = listOf(STAT_ILLUSION, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_REFORM_TAINTED_SCOUNDREL,
    render = TAINTED_SCOUNDREL,
    approaches = listOf(STAT_BRAVADO, STAT_CUNNING, STAT_LOGIC),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_SHATTER_WARP_SKULL,
    render = WARP_SKULL,
    approaches = listOf(STAT_ILLUSION, STAT_SORCERY, STAT_BEWITCHING),
    reward = attributes("arcanum" to 2)
).register()
