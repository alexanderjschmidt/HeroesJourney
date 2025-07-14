import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Undead Challenges - included by basegame mod
challenge("lay_bound_cadaver") {
    render = BOUND_CADAVER
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_brittle_archer") {
    render = BRITTLE_ARCHER
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_carcass_feeder") {
    render = CARCASS_FEEDER
    approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("destroy_decrepit_bones") {
    render = DECREPIT_BONES
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_dismembered_crawler") {
    render = DISMEMBERED_CRAWLER
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_ghastly_eye") {
    render = GHASTLY_EYE
    approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_grave_revenant") {
    render = GRAVE_REVENANT
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_mutilated_stumbler") {
    render = MUTILATED_STUMBLER
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_sand_ghoul") {
    render = SAND_GHOUL
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_skittering_hand") {
    render = SKITTERING_HAND
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_unraveling_crawler") {
    render = UNRAVELING_CRAWLER
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_vampire_bat") {
    render = VAMPIRE_BAT
    approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_giant_royal_scarab") {
    render = GIANT_ROYAL_SCARAB
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_royal_scarab") {
    render = ROYAL_SCARAB
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_toxic_hound") {
    render = TOXIC_HOUND
    approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
