import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Humanoid Challenges - included by basegame mod
challenge {
    id = Ids.CHALLENGE_OUTMANEUVER_GOBLIN_ARCHER
    render = Ids.GOBLIN_ARCHER
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_CALM_GOBLIN_FANATIC
    render = Ids.GOBLIN_FANATIC
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_DEFEAT_GOBLIN_FIGHTER
    render = Ids.GOBLIN_FIGHTER
    approach(Ids.STAT_CHIVALRY)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_EXPOSE_GOBLIN_OCCULTIST
    render = Ids.GOBLIN_OCCULTIST
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_OUTRUN_GOBLIN_WOLF_RIDER
    render = Ids.GOBLIN_WOLF_RIDER
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_CAPTURE_HALFLING_ASSASSIN
    render = Ids.HALFLING_ASSASSIN
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_PERFORM_WITH_HALFLING_BARD
    render = Ids.HALFLING_BARD
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_TRACK_WITH_HALFLING_RANGER
    render = Ids.HALFLING_RANGER
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_OUTWIT_HALFLING_ROGUE
    render = Ids.HALFLING_ROGUE
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_COMPETE_WITH_HALFLING_SLINGER
    render = Ids.HALFLING_SLINGER
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_SCOUT_WITH_LIZARDFOLK_SCOUT
    render = Ids.LIZARDFOLK_SCOUT
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SKILL)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_SPAR_LIZARDFOLK_GLADIATOR
    render = Ids.LIZARDFOLK_GLADIATOR
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CHIVALRY)
    approach(Ids.STAT_MIGHT)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_TRAIN_LIZARDFOLK_ARCHER
    render = Ids.LIZARDFOLK_ARCHER
    approach(Ids.STAT_PERSUASION)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_TAME_BESTIAL_LIZARDFOLK
    render = Ids.BESTIAL_LIZARDFOLK
    approach(Ids.STAT_BRAVADO)
    approach(Ids.STAT_MESMERISM)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_TRAIN_LIZARDFOLK_SPEARMAN
    render = Ids.LIZARDFOLK_SPEARMAN
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_PERSUASION)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
