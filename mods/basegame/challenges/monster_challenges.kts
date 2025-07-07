import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.Ids.*

// Monster Challenges - included by basegame mod
challenge("guide_blinded_grimlock") {
    name = "Guide Blinded Grimlock"
    description = "A blind grimlock is lost in the caves. Use charm, cunning, or skill to guide it."
    render = BLINDED_GRIMLOCK
    approaches(Stat.CHARM, Stat.CUNNING, Stat.SKILL)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_bloodshot_eye") {
    name = "Defeat Bloodshot Eye"
    description =
        "A monstrous eye is cursing villagers. Use bewitching magic, illusion, or persuasion to defeat it."
    render = BLOODSHOT_EYE
    approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_brawny_ogre") {
    name = "Defeat Brawny Ogre"
    description = "A massive ogre is terrorizing the village. Use bravado, might, or skill to defeat it."
    render = BRAWNY_OGRE
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("outsmart_crimson_slaad") {
    name = "Outsmart Crimson Slaad"
    description =
        "A chaotic slaad is causing destruction. Use cunning, logic, or technique to outsmart it."
    render = CRIMSON_SLAAD
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("defeat_crushing_cyclops") {
    name = "Defeat Crushing Cyclops"
    description =
        "A cyclops is crushing everything in its path. Use bravado, might, or skill to defeat it."
    render = CRUSHING_CYCLOPS
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("purify_death_slime") {
    name = "Purify Death Slime"
    description =
        "A deadly slime is poisoning the water. Use concentration, logic, or sorcery to purify it."
    render = DEATH_SLIME
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("befriend_fungal_myconid") {
    name = "Befriend Fungal Myconid"
    description =
        "A myconid offers to share its spores. Use charm, enchanting, or mesmerism to befriend it."
    render = FUNGAL_MYCONID
    approaches(Stat.CHARM, Stat.ENCHANTING, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_humongous_ettin") {
    name = "Defeat Humongous Ettin"
    description =
        "A two-headed giant is attacking travelers. Use bravado, enchanting, or might to defeat it."
    render = HUMONGOUS_ETTIN
    approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("outsmart_murky_slaad") {
    name = "Outsmart Murky Slaad"
    description = "A murky slaad is spreading chaos. Use cunning, logic, or technique to outsmart it."
    render = MURKY_SLAAD
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("defeat_ochre_jelly") {
    name = "Defeat Ochre Jelly"
    description =
        "A jelly is consuming everything it touches. Use cunning, skill, or technique to defeat it."
    render = OCHRE_JELLY
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_ocular_watcher") {
    name = "Defeat Ocular Watcher"
    description =
        "A watcher is spying on the village. Use bewitching magic, illusion, or persuasion to defeat it."
    render = OCULAR_WATCHER
    approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("outsmart_red_cap") {
    name = "Outsmart Red Cap"
    description = "A red cap is hunting travelers. Use cunning, logic, or technique to outsmart it."
    render = RED_CAP
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("defeat_shrieker_mushroom") {
    name = "Defeat Shrieker Mushroom"
    description = "A shrieker is alerting enemies. Use cunning, skill, or technique to defeat it."
    render = SHRIEKER_MUSHROOM
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_stone_troll") {
    name = "Defeat Stone Troll"
    description = "A stone troll is blocking the bridge. Use chivalry, enchanting, or might to defeat it."
    render = STONE_TROLL
    approaches(Stat.CHIVALRY, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_swamp_troll") {
    name = "Defeat Swamp Troll"
    description = "A swamp troll is terrorizing the marsh. Use cunning, skill, or technique to defeat it."
    render = SWAMP_TROLL
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
