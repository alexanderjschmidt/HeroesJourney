import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Monster Challenges - included by basegame mod
challenge {
    id = Ids.CHALLENGE_GUIDE_BLINDED_GRIMLOCK
    render = Ids.BLINDED_GRIMLOCK
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_BLOODSHOT_EYE
    render = Ids.BLOODSHOT_EYE
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_PERSUASION)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_BRAWNY_OGRE
    render = Ids.BRAWNY_OGRE
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_OUTSMART_CRIMSON_SLAAD
    render = Ids.CRIMSON_SLAAD
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_CRUSHING_CYCLOPS
    render = Ids.CRUSHING_CYCLOPS
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_PURIFY_DEATH_SLIME
    render = Ids.DEATH_SLIME
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_BEFRIEND_FUNGAL_MYCONID
    render = Ids.FUNGAL_MYCONID
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_HUMONGOUS_ETTIN
    render = Ids.HUMONGOUS_ETTIN
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_OUTSMART_MURKY_SLAAD
    render = Ids.MURKY_SLAAD
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_OCHRE_JELLY
    render = Ids.OCHRE_JELLY
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_OCULAR_WATCHER
    render = Ids.OCULAR_WATCHER
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_PERSUASION)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_OUTSMART_RED_CAP
    render = Ids.RED_CAP
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_SHRIEKER_MUSHROOM
    render = Ids.SHRIEKER_MUSHROOM
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_STONE_TROLL
    render = Ids.STONE_TROLL
    approach(Ids.STAT_CHIVALRY)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_SWAMP_TROLL
    render = Ids.SWAMP_TROLL
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
