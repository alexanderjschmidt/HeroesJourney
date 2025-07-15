import heroes.journey.entities.challenge
import heroes.journey.modlib.Ids
import heroes.journey.modlib.Ids.*

// Undead Challenges - included by basegame mod
challenge("lay_bound_cadaver") {
    render = BOUND_CADAVER
    approaches(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_brittle_archer") {
    render = BRITTLE_ARCHER
    approaches(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_carcass_feeder") {
    render = CARCASS_FEEDER
    approaches(Ids.STAT_BRAVADO, Ids.STAT_ENCHANTING, Ids.STAT_MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("destroy_decrepit_bones") {
    render = DECREPIT_BONES
    approaches(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_dismembered_crawler") {
    render = DISMEMBERED_CRAWLER
    approaches(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_ghastly_eye") {
    render = GHASTLY_EYE
    approaches(Ids.STAT_BEWITCHING, Ids.STAT_ILLUSION, Ids.STAT_PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_grave_revenant") {
    render = GRAVE_REVENANT
    approaches(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_mutilated_stumbler") {
    render = MUTILATED_STUMBLER
    approaches(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_sand_ghoul") {
    render = SAND_GHOUL
    approaches(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_skittering_hand") {
    render = SKITTERING_HAND
    approaches(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_unraveling_crawler") {
    render = UNRAVELING_CRAWLER
    approaches(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_vampire_bat") {
    render = VAMPIRE_BAT
    approaches(Ids.STAT_BEWITCHING, Ids.STAT_ILLUSION, Ids.STAT_PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_giant_royal_scarab") {
    render = GIANT_ROYAL_SCARAB
    approaches(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_royal_scarab") {
    render = ROYAL_SCARAB
    approaches(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_toxic_hound") {
    render = TOXIC_HOUND
    approaches(Ids.STAT_BRAVADO, Ids.STAT_ENCHANTING, Ids.STAT_MIGHT)
    reward { attr("valor", 2) }
}.register()
