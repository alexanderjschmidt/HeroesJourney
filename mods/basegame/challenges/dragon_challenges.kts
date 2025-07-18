import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Dragon Challenges - included by basegame mod
challenge {
    id = Ids.CHALLENGE_BEFRIEND_AQUA_DRAKE
    render = Ids.AQUA_DRAKE
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_ENCHANTING)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_RESCUE_BABY_BRASS_DRAGON
    render = Ids.BABY_BRASS_DRAGON
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_CHIVALRY)
    approach(Ids.STAT_LOGIC)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_HEAL_BABY_COPPER_DRAGON
    render = Ids.BABY_COPPER_DRAGON
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_GUIDE_BABY_GREEN_DRAGON
    render = Ids.BABY_GREEN_DRAGON
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_MESMERISM)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_RESCUE_BABY_WHITE_DRAGON
    render = Ids.BABY_WHITE_DRAGON
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_TRAIN_JUVENILE_BRONZE_DRAGON
    render = Ids.JUVENILE_BRONZE_DRAGON
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_CHALLENGE_MATURE_BRONZE_DRAGON
    render = Ids.MATURE_BRONZE_DRAGON
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CHIVALRY)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 3) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_MUD_WYVERN
    render = Ids.MUD_WYVERN
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_OUTSMART_POISON_DRAKE
    render = Ids.POISON_DRAKE
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BEFRIEND_PYGMY_WYVERN
    render = Ids.PYGMY_WYVERN
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_GUIDE_VIRIDIAN_DRAKE
    render = Ids.VIRIDIAN_DRAKE
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_LOGIC)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_CHALLENGE_YOUNG_BRASS_DRAGON
    render = Ids.YOUNG_BRASS_DRAGON
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_YOUNG_RED_DRAGON
    render = Ids.YOUNG_RED_DRAGON
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_EMPOWERMENT)
    reward { stat("valor", 3) }
}.register()
challenge {
    id = Ids.CHALLENGE_SURVIVE_ADULT_WHITE_DRAGON
    render = Ids.ADULT_WHITE_DRAGON
    approach(Ids.STAT_EMPOWERMENT)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 3) }
}.register()
challenge {
    id = Ids.CHALLENGE_NEGOTIATE_ADULT_GREEN_DRAGON
    render = Ids.ADULT_GREEN_DRAGON
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 3) }
}.register()
