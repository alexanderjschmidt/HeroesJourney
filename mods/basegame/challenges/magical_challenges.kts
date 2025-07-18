import heroes.journey.modlib.Ids.*
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Magical Challenges - included by basegame mod
challenge(
    id = CHALLENGE_ARCANE_PUZZLE,
    render = ARCANE_PUZZLE,
    approaches = listOf(STAT_CONCENTRATION, STAT_LOGIC, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_ENCHANT_ITEM,
    render = ENCHANT_ITEM,
    approaches = listOf(STAT_CHARM, STAT_EMPOWERMENT, STAT_ENCHANTING),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_TRICK_MAGICAL_GUARDIAN,
    render = TRICK_MAGICAL_GUARDIAN,
    approaches = listOf(STAT_CUNNING, STAT_ILLUSION, STAT_LOGIC),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_EMPOWER_RELIC,
    render = EMPOWER_RELIC,
    approaches = listOf(STAT_ENCHANTING, STAT_MIGHT, STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_BANISH_CURSED_SPIRIT,
    render = BANISH_CURSED_SPIRIT,
    approaches = listOf(STAT_CHARM, STAT_CONCENTRATION, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_BEWITCH_FAMILIAR,
    render = BEWITCH_FAMILIAR,
    approaches = listOf(STAT_BEWITCHING, STAT_EMPOWERMENT, STAT_LOGIC),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_CHARM_MAGICAL_BEAST,
    render = CHARM_MAGICAL_BEAST,
    approaches = listOf(STAT_BEWITCHING, STAT_CHARM, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_OUTWIT_MAGICAL_TRAP,
    render = OUTWIT_MAGICAL_TRAP,
    approaches = listOf(STAT_CUNNING, STAT_ILLUSION, STAT_LOGIC),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_ENCHANT_WEAPON,
    render = ENCHANT_WEAPON,
    approaches = listOf(STAT_EMPOWERMENT, STAT_ENCHANTING, STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = CHALLENGE_SOLVE_RIDDLE,
    render = SOLVE_RIDDLE,
    approaches = listOf(STAT_ENCHANTING, STAT_LOGIC, STAT_MIGHT),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = CHALLENGE_DISARM_GOLEM,
    render = DISARM_GOLEM,
    approaches = listOf(STAT_CUNNING, STAT_SKILL, STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = CHALLENGE_BEWITCH_MAGICAL_FOE,
    render = BEWITCH_MAGICAL_FOE,
    approaches = listOf(STAT_BEWITCHING, STAT_CHARM, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_PERSUADE_ELEMENTAL,
    render = PERSUADE_ELEMENTAL,
    approaches = listOf(STAT_CONCENTRATION, STAT_PERSUASION, STAT_SORCERY),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_BEFRIEND_FAIRY,
    render = BEFRIEND_FAIRY,
    approaches = listOf(STAT_CHARM, STAT_ENCHANTING, STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = CHALLENGE_BANISH_DEMON,
    render = BANISH_DEMON,
    approaches = listOf(STAT_BEWITCHING, STAT_LOGIC, STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
