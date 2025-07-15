import heroes.journey.entities.challenge
import heroes.journey.modlib.Ids
import heroes.journey.modlib.Ids.*

// Monster Challenges - included by basegame mod
challenge("guide_blinded_grimlock") {
    render = BLINDED_GRIMLOCK
    approaches(Ids.STAT_CHARM, Ids.STAT_CUNNING, Ids.STAT_SKILL)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_bloodshot_eye") {
    render = BLOODSHOT_EYE
    approaches(Ids.STAT_BEWITCHING, Ids.STAT_ILLUSION, Ids.STAT_PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_brawny_ogre") {
    render = BRAWNY_OGRE
    approaches(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("outsmart_crimson_slaad") {
    render = CRIMSON_SLAAD
    approaches(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("defeat_crushing_cyclops") {
    render = CRUSHING_CYCLOPS
    approaches(Ids.STAT_BRAVADO, Ids.STAT_MIGHT, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("purify_death_slime") {
    render = DEATH_SLIME
    approaches(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("befriend_fungal_myconid") {
    render = FUNGAL_MYCONID
    approaches(Ids.STAT_CHARM, Ids.STAT_ENCHANTING, Ids.STAT_MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_humongous_ettin") {
    render = HUMONGOUS_ETTIN
    approaches(Ids.STAT_BRAVADO, Ids.STAT_ENCHANTING, Ids.STAT_MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("outsmart_murky_slaad") {
    render = MURKY_SLAAD
    approaches(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("defeat_ochre_jelly") {
    render = OCHRE_JELLY
    approaches(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_ocular_watcher") {
    render = OCULAR_WATCHER
    approaches(Ids.STAT_BEWITCHING, Ids.STAT_ILLUSION, Ids.STAT_PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("outsmart_red_cap") {
    render = RED_CAP
    approaches(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("defeat_shrieker_mushroom") {
    render = SHRIEKER_MUSHROOM
    approaches(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_stone_troll") {
    render = STONE_TROLL
    approaches(Ids.STAT_CHIVALRY, Ids.STAT_ENCHANTING, Ids.STAT_MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_swamp_troll") {
    render = SWAMP_TROLL
    approaches(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
