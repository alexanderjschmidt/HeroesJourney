import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Holy Challenges - included by basegame mod
challenge {
    id = Ids.CHALLENGE_AID_RESOLUTE_ANGEL
    render = Ids.RESOLUTE_ANGEL
    approach(Ids.STAT_CHIVALRY)
    approach(Ids.STAT_EMPOWERMENT)
    approach(Ids.STAT_ILLUSION)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BLESS_BLESSED_GLADIATOR
    render = Ids.BLESSED_GLADIATOR
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_PERSUASION)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_TRAIN_BOLD_MAN_AT_ARMS
    render = Ids.BOLD_MAN_AT_ARMS
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CHIVALRY)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_COMMUNE_WITH_DIVINE_PLANETAR
    render = Ids.DIVINE_PLANETAR
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 3) }
}.register()
challenge {
    id = Ids.CHALLENGE_GUIDE_DEVOUT_ACOLYTE
    render = Ids.DEVOUT_ACOLYTE
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_HEAL_FAVORED_CLERIC
    render = Ids.FAVORED_CLERIC
    approach(Ids.STAT_EMPOWERMENT)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_LOGIC)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BEFRIEND_FLOATING_CHERUB
    render = Ids.FLOATING_CHERUB
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_GUIDE_GENTLE_SHEPARD
    render = Ids.GENTLE_SHEPARD
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_JOIN_HOLY_CRUSADER
    render = Ids.HOLY_CRUSADER
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CHIVALRY)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_LEARN_FROM_JOVIAL_FRIAR
    render = Ids.JOVIAL_FRIAR
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_PERSUASION)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_COMMUNE_WITH_RIGHTEOUS_DEVA
    render = Ids.RIGHTEOUS_DEVA
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 3) }
}.register()
challenge {
    id = Ids.CHALLENGE_TRAIN_WITH_SWORD_ARCHON
    render = Ids.SWORD_ARCHON
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_HONOR_VETERAN_SWORDSMAN
    render = Ids.VETERAN_SWORDSMAN
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CHIVALRY)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_GUIDE_ZEALOUS_PRIEST
    render = Ids.ZEALOUS_PRIEST
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_MESMERISM)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_INSPIRE_DETERMINED_SOLDIER
    render = Ids.DETERMINED_SOLDIER
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_MESMERISM)
    reward { stat("valor", 2) }
}.register()
