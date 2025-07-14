import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Magical Challenges - included by basegame mod
challenge("defeat_adept_necromancer") {
    render = ADEPT_NECROMANCER
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("purify_corrupted_treant") {
    render = CORRUPTED_TREANT
    approaches(Stat.CHARM, Stat.EMPOWERMENT, Stat.ENCHANTING)
    reward { attr("arcanum", 2) }
}.register()
challenge("outsmart_deft_sorceress") {
    render = DEFT_SORCERESS
    approaches(Stat.CUNNING, Stat.ILLUSION, Stat.LOGIC)
    reward { attr("arcanum", 2) }
}.register()
challenge("negotiate_earth_elemental") {
    render = EARTH_ELEMENTAL
    approaches(Stat.ENCHANTING, Stat.MIGHT, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("learn_expert_druid") {
    render = EXPERT_DRUID
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_fire_elemental") {
    render = FIRE_ELEMENTAL
    approaches(Stat.BEWITCHING, Stat.EMPOWERMENT, Stat.LOGIC)
    reward { attr("arcanum", 2) }
}.register()
challenge("befriend_fluttering_pixie") {
    render = FLUTTERING_PIXIE
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("follow_glowing_wisp") {
    render = GLOWING_WISP
    approaches(Stat.CUNNING, Stat.ILLUSION, Stat.LOGIC)
    reward { attr("insight", 2) }
}.register()
challenge("aid_grizzled_treant") {
    render = GRIZZLED_TREANT
    approaches(Stat.EMPOWERMENT, Stat.ENCHANTING, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_ice_golem") {
    render = ICE_GOLEM
    approaches(Stat.ENCHANTING, Stat.LOGIC, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("outmaneuver_iron_golem") {
    render = IRON_GOLEM
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("befriend_magical_fairy") {
    render = MAGICAL_FAIRY
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("teach_novice_pyromancer") {
    render = NOVICE_PYROMANCER
    approaches(Stat.CONCENTRATION, Stat.PERSUASION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("negotiate_water_elemental") {
    render = WATER_ELEMENTAL
    approaches(Stat.CHARM, Stat.ENCHANTING, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_vile_witch") {
    render = VILE_WITCH
    approaches(Stat.BEWITCHING, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
