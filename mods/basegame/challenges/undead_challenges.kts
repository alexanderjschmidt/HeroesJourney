import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Undead Challenges - included by basegame mod
challenge {
    id = Ids.CHALLENGE_LAY_BOUND_CADAVER
    render = Ids.BOUND_CADAVER
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_BRITTLE_ARCHER
    render = Ids.BRITTLE_ARCHER
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_CARCASS_FEEDER
    render = Ids.CARCASS_FEEDER
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DESTROY_DECREPIT_BONES
    render = Ids.DECREPIT_BONES
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_DISMEMBERED_CRAWLER
    render = Ids.DISMEMBERED_CRAWLER
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_GHASTLY_EYE
    render = Ids.GHASTLY_EYE
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_PERSUASION)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_GRAVE_REVENANT
    render = Ids.GRAVE_REVENANT
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_MUTILATED_STUMBLER
    render = Ids.MUTILATED_STUMBLER
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_SAND_GHOUL
    render = Ids.SAND_GHOUL
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_SKITTERING_HAND
    render = Ids.SKITTERING_HAND
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_UNRAVELING_CRAWLER
    render = Ids.UNRAVELING_CRAWLER
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_VAMPIRE_BAT
    render = Ids.VAMPIRE_BAT
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_ILLUSION)
    approach(Ids.STAT_PERSUASION)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_GIANT_ROYAL_SCARAB
    render = Ids.GIANT_ROYAL_SCARAB
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_ROYAL_SCARAB
    render = Ids.ROYAL_SCARAB
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_TOXIC_HOUND
    render = Ids.TOXIC_HOUND
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 2) }
}.register()
