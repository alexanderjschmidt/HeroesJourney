import heroes.journey.entities.challenge
import heroes.journey.modlib.Ids
import heroes.journey.modlib.Ids.*

// Humanoid Challenges - included by basegame mod
challenge("outmaneuver_goblin_archer") {
    render = GOBLIN_ARCHER
    approaches(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("calm_goblin_fanatic") {
    render = GOBLIN_FANATIC
    approaches(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_goblin_fighter") {
    render = GOBLIN_FIGHTER
    approaches(Ids.STAT_CHIVALRY, Ids.STAT_MIGHT, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("expose_goblin_occultist") {
    render = GOBLIN_OCCULTIST
    approaches(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("outrun_goblin_wolf_rider") {
    render = GOBLIN_WOLF_RIDER
    approaches(Ids.STAT_CUNNING, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("capture_halfling_assassin") {
    render = HALFLING_ASSASSIN
    approaches(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("perform_with_halfling_bard") {
    render = HALFLING_BARD
    approaches(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("track_with_halfling_ranger") {
    render = HALFLING_RANGER
    approaches(Ids.STAT_LOGIC, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("outwit_halfling_rogue") {
    render = HALFLING_ROGUE
    approaches(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("compete_with_halfling_slinger") {
    render = HALFLING_SLINGER
    approaches(Ids.STAT_CHARM, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("scout_with_lizardfolk_scout") {
    render = LIZARDFOLK_SCOUT
    approaches(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_SKILL)
    reward { attr("insight", 2) }
}.register()
challenge("spar_lizardfolk_gladiator") {
    render = LIZARDFOLK_GLADIATOR
    approaches(Ids.STAT_CHARM, Ids.STAT_CHIVALRY, Ids.STAT_MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("train_lizardfolk_archer") {
    render = LIZARDFOLK_ARCHER
    approaches(Ids.STAT_PERSUASION, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("tame_bestial_lizardfolk") {
    render = BESTIAL_LIZARDFOLK
    approaches(Ids.STAT_BRAVADO, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("train_lizardfolk_spearman") {
    render = LIZARDFOLK_SPEARMAN
    approaches(Ids.STAT_MIGHT, Ids.STAT_PERSUASION, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
