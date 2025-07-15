import heroes.journey.entities.challenge
import heroes.journey.modlib.Ids.*

// Dragon Challenges - included by basegame mod
challenge("befriend_aqua_drake") {
    render = AQUA_DRAKE
    approaches(STAT_BEWITCHING, STAT_CHARM, STAT_ENCHANTING)
    reward { attr("influence", 2) }
}.register()
challenge("rescue_baby_brass_dragon") {
    render = BABY_BRASS_DRAGON
    approaches(STAT_BRAVADO, STAT_CHIVALRY, STAT_LOGIC)
    reward { attr("valor", 2) }
}.register()
challenge("heal_baby_copper_dragon") {
    render = BABY_COPPER_DRAGON
    approaches(STAT_CONCENTRATION, STAT_ILLUSION, STAT_SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("guide_baby_green_dragon") {
    render = BABY_GREEN_DRAGON
    approaches(STAT_ENCHANTING, STAT_MESMERISM, STAT_PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("rescue_baby_white_dragon") {
    render = BABY_WHITE_DRAGON
    approaches(STAT_CHARM, STAT_MIGHT, STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("train_juvenile_bronze_dragon") {
    render = JUVENILE_BRONZE_DRAGON
    approaches(STAT_CUNNING, STAT_LOGIC, STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("challenge_mature_bronze_dragon") {
    render = MATURE_BRONZE_DRAGON
    approaches(STAT_CHARM, STAT_CHIVALRY, STAT_MIGHT)
    reward { attr("valor", 3) }
}.register()
challenge("defeat_mud_wyvern") {
    render = MUD_WYVERN
    approaches(STAT_ILLUSION, STAT_SKILL, STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("outsmart_poison_drake") {
    render = POISON_DRAKE
    approaches(STAT_BEWITCHING, STAT_CUNNING, STAT_LOGIC)
    reward { attr("insight", 2) }
}.register()
challenge("befriend_pygmy_wyvern") {
    render = PYGMY_WYVERN
    approaches(STAT_CHARM, STAT_MESMERISM, STAT_PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("guide_viridian_drake") {
    render = VIRIDIAN_DRAKE
    approaches(STAT_BEWITCHING, STAT_ENCHANTING, STAT_LOGIC)
    reward { attr("influence", 2) }
}.register()
challenge("challenge_young_brass_dragon") {
    render = YOUNG_BRASS_DRAGON
    approaches(STAT_BRAVADO, STAT_CHARM, STAT_MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_young_red_dragon") {
    render = YOUNG_RED_DRAGON
    approaches(STAT_BRAVADO, STAT_CHARM, STAT_EMPOWERMENT)
    reward { attr("valor", 3) }
}.register()
challenge("survive_adult_white_dragon") {
    render = ADULT_WHITE_DRAGON
    approaches(STAT_EMPOWERMENT, STAT_LOGIC, STAT_MIGHT)
    reward { attr("valor", 3) }
}.register()
challenge("negotiate_adult_green_dragon") {
    render = ADULT_GREEN_DRAGON
    approaches(STAT_CHARM, STAT_MESMERISM, STAT_PERSUASION)
    reward { attr("influence", 3) }
}.register()
