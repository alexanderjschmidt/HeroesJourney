import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Humanoid Challenges - included by basegame mod
challenge("outmaneuver_goblin_archer") {
    render = GOBLIN_ARCHER
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("calm_goblin_fanatic") {
    render = GOBLIN_FANATIC
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("defeat_goblin_fighter") {
    render = GOBLIN_FIGHTER
    approaches(Stat.CHIVALRY, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("expose_goblin_occultist") {
    render = GOBLIN_OCCULTIST
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("outrun_goblin_wolf_rider") {
    render = GOBLIN_WOLF_RIDER
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("capture_halfling_assassin") {
    render = HALFLING_ASSASSIN
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("perform_with_halfling_bard") {
    render = HALFLING_BARD
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("track_with_halfling_ranger") {
    render = HALFLING_RANGER
    approaches(Stat.LOGIC, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("outwit_halfling_rogue") {
    render = HALFLING_ROGUE
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("compete_with_halfling_slinger") {
    render = HALFLING_SLINGER
    approaches(Stat.CHARM, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("scout_with_lizardfolk_scout") {
    render = LIZARDFOLK_SCOUT
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.SKILL)
    reward { attr("insight", 2) }
}.register()
challenge("spar_lizardfolk_gladiator") {
    render = LIZARDFOLK_GLADIATOR
    approaches(Stat.CHARM, Stat.CHIVALRY, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("train_lizardfolk_archer") {
    render = LIZARDFOLK_ARCHER
    approaches(Stat.PERSUASION, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("tame_bestial_lizardfolk") {
    render = BESTIAL_LIZARDFOLK
    approaches(Stat.BRAVADO, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("train_lizardfolk_spearman") {
    render = LIZARDFOLK_SPEARMAN
    approaches(Stat.MIGHT, Stat.PERSUASION, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
