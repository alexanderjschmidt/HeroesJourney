import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Monster Challenges - included by basegame mod
challenge("guide_blinded_grimlock") {
    render = BLINDED_GRIMLOCK
    approaches(Stat.CHARM, Stat.CUNNING, Stat.SKILL)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_bloodshot_eye") {
    render = BLOODSHOT_EYE
    approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_brawny_ogre") {
    render = BRAWNY_OGRE
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("outsmart_crimson_slaad") {
    render = CRIMSON_SLAAD
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("defeat_crushing_cyclops") {
    render = CRUSHING_CYCLOPS
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("purify_death_slime") {
    render = DEATH_SLIME
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("befriend_fungal_myconid") {
    render = FUNGAL_MYCONID
    approaches(Stat.CHARM, Stat.ENCHANTING, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_humongous_ettin") {
    render = HUMONGOUS_ETTIN
    approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("outsmart_murky_slaad") {
    render = MURKY_SLAAD
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("defeat_ochre_jelly") {
    render = OCHRE_JELLY
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_ocular_watcher") {
    render = OCULAR_WATCHER
    approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("outsmart_red_cap") {
    render = RED_CAP
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("defeat_shrieker_mushroom") {
    render = SHRIEKER_MUSHROOM
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_stone_troll") {
    render = STONE_TROLL
    approaches(Stat.CHIVALRY, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_swamp_troll") {
    render = SWAMP_TROLL
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
