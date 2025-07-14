import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Dragon Challenges - included by basegame mod
challenge("befriend_aqua_drake") {
    render = AQUA_DRAKE
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.ENCHANTING)
    reward { attr("influence", 2) }
}.register()
challenge("rescue_baby_brass_dragon") {
    render = BABY_BRASS_DRAGON
    approaches(Stat.BRAVADO, Stat.CHIVALRY, Stat.LOGIC)
    reward { attr("valor", 2) }
}.register()
challenge("heal_baby_copper_dragon") {
    render = BABY_COPPER_DRAGON
    approaches(Stat.CONCENTRATION, Stat.ILLUSION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("guide_baby_green_dragon") {
    render = BABY_GREEN_DRAGON
    approaches(Stat.ENCHANTING, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("rescue_baby_white_dragon") {
    render = BABY_WHITE_DRAGON
    approaches(Stat.CHARM, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("train_juvenile_bronze_dragon") {
    render = JUVENILE_BRONZE_DRAGON
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("challenge_mature_bronze_dragon") {
    render = MATURE_BRONZE_DRAGON
    approaches(Stat.CHARM, Stat.CHIVALRY, Stat.MIGHT)
    reward { attr("valor", 3) }
}.register()
challenge("defeat_mud_wyvern") {
    render = MUD_WYVERN
    approaches(Stat.ILLUSION, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("outsmart_poison_drake") {
    render = POISON_DRAKE
    approaches(Stat.BEWITCHING, Stat.CUNNING, Stat.LOGIC)
    reward { attr("insight", 2) }
}.register()
challenge("befriend_pygmy_wyvern") {
    render = PYGMY_WYVERN
    approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("guide_viridian_drake") {
    render = VIRIDIAN_DRAKE
    approaches(Stat.BEWITCHING, Stat.ENCHANTING, Stat.LOGIC)
    reward { attr("influence", 2) }
}.register()
challenge("challenge_young_brass_dragon") {
    render = YOUNG_BRASS_DRAGON
    approaches(Stat.BRAVADO, Stat.CHARM, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_young_red_dragon") {
    render = YOUNG_RED_DRAGON
    approaches(Stat.BRAVADO, Stat.CHARM, Stat.EMPOWERMENT)
    reward { attr("valor", 3) }
}.register()
challenge("survive_adult_white_dragon") {
    render = ADULT_WHITE_DRAGON
    approaches(Stat.EMPOWERMENT, Stat.LOGIC, Stat.MIGHT)
    reward { attr("valor", 3) }
}.register()
challenge("negotiate_adult_green_dragon") {
    render = ADULT_GREEN_DRAGON
    approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 3) }
}.register()
