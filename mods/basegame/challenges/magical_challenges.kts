import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Magical Challenges - included by basegame mod
challenge(
    id = Ids.CHALLENGE_ARCANE_PUZZLE,
    render = Ids.ARCANE_PUZZLE,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_ENCHANT_ITEM,
    render = Ids.ENCHANT_ITEM,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_EMPOWERMENT, Ids.STAT_ENCHANTING),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_TRICK_MAGICAL_GUARDIAN,
    render = Ids.TRICK_MAGICAL_GUARDIAN,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_ILLUSION, Ids.STAT_LOGIC),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_EMPOWER_RELIC,
    render = Ids.EMPOWER_RELIC,
    approaches = listOf(Ids.STAT_ENCHANTING, Ids.STAT_MIGHT, Ids.STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BANISH_CURSED_SPIRIT,
    render = Ids.BANISH_CURSED_SPIRIT,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_CONCENTRATION, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BEWITCH_FAMILIAR,
    render = Ids.BEWITCH_FAMILIAR,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_EMPOWERMENT, Ids.STAT_LOGIC),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_CHARM_MAGICAL_BEAST,
    render = Ids.CHARM_MAGICAL_BEAST,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_OUTWIT_MAGICAL_TRAP,
    render = Ids.OUTWIT_MAGICAL_TRAP,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_ILLUSION, Ids.STAT_LOGIC),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_ENCHANT_WEAPON,
    render = Ids.ENCHANT_WEAPON,
    approaches = listOf(Ids.STAT_EMPOWERMENT, Ids.STAT_ENCHANTING, Ids.STAT_PERSUASION),
    reward = attributes("arcanum" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_SOLVE_RIDDLE,
    render = Ids.SOLVE_RIDDLE,
    approaches = listOf(Ids.STAT_ENCHANTING, Ids.STAT_LOGIC, Ids.STAT_MIGHT),
    reward = attributes("insight" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_DISARM_GOLEM,
    render = Ids.DISARM_GOLEM,
    approaches = listOf(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE),
    reward = attributes("valor" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BEWITCH_MAGICAL_FOE,
    render = Ids.BEWITCH_MAGICAL_FOE,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_PERSUADE_ELEMENTAL,
    render = Ids.PERSUADE_ELEMENTAL,
    approaches = listOf(Ids.STAT_CONCENTRATION, Ids.STAT_PERSUASION, Ids.STAT_SORCERY),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BEFRIEND_FAIRY,
    render = Ids.BEFRIEND_FAIRY,
    approaches = listOf(Ids.STAT_CHARM, Ids.STAT_ENCHANTING, Ids.STAT_MESMERISM),
    reward = attributes("influence" to 2)
).register()
challenge(
    id = Ids.CHALLENGE_BANISH_DEMON,
    render = Ids.BANISH_DEMON,
    approaches = listOf(Ids.STAT_BEWITCHING, Ids.STAT_LOGIC, Ids.STAT_SORCERY),
    reward = attributes("arcanum" to 2)
).register()
