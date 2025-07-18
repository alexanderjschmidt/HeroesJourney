import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Magical Challenges - included by basegame mod
challenge {
    id = Ids.CHALLENGE_ARCANE_PUZZLE
    render = Ids.ARCANE_PUZZLE
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_ENCHANT_ITEM
    render = Ids.ENCHANT_ITEM
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_EMPOWERMENT)
    approach(Ids.STAT_ENCHANTING)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_TRICK_MAGICAL_GUARDIAN
    render = Ids.TRICK_MAGICAL_GUARDIAN
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_LOGIC)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_EMPOWER_RELIC
    render = Ids.EMPOWER_RELIC
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_PERSUASION)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BANISH_CURSED_SPIRIT
    render = Ids.BANISH_CURSED_SPIRIT
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BEWITCH_FAMILIAR
    render = Ids.BEWITCH_FAMILIAR
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_EMPOWERMENT)
    approach(Ids.STAT_LOGIC)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_CHARM_MAGICAL_BEAST
    render = Ids.CHARM_MAGICAL_BEAST
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_OUTWIT_MAGICAL_TRAP
    render = Ids.OUTWIT_MAGICAL_TRAP
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_LOGIC)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_ENCHANT_WEAPON
    render = Ids.ENCHANT_WEAPON
    approach(Ids.STAT_EMPOWERMENT)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_PERSUASION)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_SOLVE_RIDDLE
    render = Ids.SOLVE_RIDDLE
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_MIGHT)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DISARM_GOLEM
    render = Ids.DISARM_GOLEM
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BEWITCH_MAGICAL_FOE
    render = Ids.BEWITCH_MAGICAL_FOE
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_PERSUADE_ELEMENTAL
    render = Ids.PERSUADE_ELEMENTAL
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_PERSUASION)
    approach(Ids.STAT_SORCERY)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BEFRIEND_FAIRY
    render = Ids.BEFRIEND_FAIRY
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BANISH_DEMON
    render = Ids.BANISH_DEMON
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
