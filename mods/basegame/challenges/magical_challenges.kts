import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.Ids.*

// Magical Challenges - included by basegame mod
challenge("defeat_adept_necromancer") {
    name = "Defeat Adept Necromancer"
    description = "A necromancer is raising undead. Use concentration, logic, or sorcery to defeat it."
    render = ADEPT_NECROMANCER
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("purify_corrupted_treant") {
    name = "Purify Corrupted Treant"
    description =
        "A treant is corrupted by dark magic. Use charm, empowerment, or enchanting to purify it."
    render = CORRUPTED_TREANT
    approaches(Stat.CHARM, Stat.EMPOWERMENT, Stat.ENCHANTING)
    reward { attr("arcanum", 2) }
}.register()
challenge("outsmart_deft_sorceress") {
    name = "Outsmart Deft Sorceress"
    description =
        "A sorceress is casting dangerous spells. Use cunning, illusion, or logic to outsmart it."
    render = DEFT_SORCERESS
    approaches(Stat.CUNNING, Stat.ILLUSION, Stat.LOGIC)
    reward { attr("arcanum", 2) }
}.register()
challenge("negotiate_earth_elemental") {
    name = "Negotiate with Earth Elemental"
    description =
        "An earth elemental blocks your path. Use enchanting magic, might, or persuasion to negotiate."
    render = EARTH_ELEMENTAL
    approaches(Stat.ENCHANTING, Stat.MIGHT, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("learn_expert_druid") {
    name = "Learn from Expert Druid"
    description = "A druid offers to teach nature magic. Use charm, concentration, or sorcery to learn."
    render = EXPERT_DRUID
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_fire_elemental") {
    name = "Defeat Fire Elemental"
    description =
        "A fire elemental is burning the forest. Use bewitching magic, empowerment, or logic to defeat it."
    render = FIRE_ELEMENTAL
    approaches(Stat.BEWITCHING, Stat.EMPOWERMENT, Stat.LOGIC)
    reward { attr("arcanum", 2) }
}.register()
challenge("befriend_fluttering_pixie") {
    name = "Befriend Fluttering Pixie"
    description =
        "A pixie is curious about you. Use bewitching magic, charm, or mesmerism to befriend it."
    render = FLUTTERING_PIXIE
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("follow_glowing_wisp") {
    name = "Follow Glowing Wisp"
    description =
        "A magical wisp leads you through the forest. Use cunning, illusion, or logic to follow it."
    render = GLOWING_WISP
    approaches(Stat.CUNNING, Stat.ILLUSION, Stat.LOGIC)
    reward { attr("insight", 2) }
}.register()
challenge("aid_grizzled_treant") {
    name = "Aid Grizzled Treant"
    description = "An ancient treant needs help. Use empowerment, enchanting, or persuasion to aid it."
    render = GRIZZLED_TREANT
    approaches(Stat.EMPOWERMENT, Stat.ENCHANTING, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_ice_golem") {
    name = "Defeat Ice Golem"
    description =
        "An ice golem is freezing the village. Use enchanting magic, logic, or might to defeat it."
    render = ICE_GOLEM
    approaches(Stat.ENCHANTING, Stat.LOGIC, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("outmaneuver_iron_golem") {
    name = "Outmaneuver Iron Golem"
    description =
        "An iron golem guards ancient treasure. Use cunning, skill, or technique to outmaneuver it."
    render = IRON_GOLEM
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("befriend_magical_fairy") {
    name = "Befriend Magical Fairy"
    description =
        "A fairy offers magical assistance. Use bewitching magic, charm, or mesmerism to befriend it."
    render = MAGICAL_FAIRY
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("teach_novice_pyromancer") {
    name = "Teach Novice Pyromancer"
    description =
        "A young pyromancer needs guidance. Use concentration, persuasion, or sorcery to teach it."
    render = NOVICE_PYROMANCER
    approaches(Stat.CONCENTRATION, Stat.PERSUASION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("negotiate_water_elemental") {
    name = "Negotiate with Water Elemental"
    description =
        "A water elemental controls the river. Use charm, enchanting, or mesmerism to negotiate."
    render = WATER_ELEMENTAL
    approaches(Stat.CHARM, Stat.ENCHANTING, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_vile_witch") {
    name = "Defeat Vile Witch"
    description = "A witch is cursing villagers. Use bewitching magic, logic, or sorcery to defeat it."
    render = VILE_WITCH
    approaches(Stat.BEWITCHING, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
