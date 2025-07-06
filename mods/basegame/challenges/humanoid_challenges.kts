import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.initializers.base.Ids.*

// Humanoid Challenges - included by basegame mod
    challenge("outmaneuver_goblin_archer") {
        name = "Outmaneuver Goblin Archer"
        description =
            "A goblin archer is ambushing travelers. Use cunning, logic, or skill to outmaneuver it."
        render = GOBLIN_ARCHER
        approaches(Stat.CUNNING, Stat.LOGIC, Stat.SKILL)
        reward { attr("valor", 2) }
    }.register()
    challenge("calm_goblin_fanatic") {
        name = "Calm Goblin Fanatic"
        description =
            "A goblin is in a religious frenzy. Use bewitching magic, charm, or mesmerism to calm it."
        render = GOBLIN_FANATIC
        approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
        reward { attr("influence", 2) }
    }.register()
    challenge("defeat_goblin_fighter") {
        name = "Defeat Goblin Fighter"
        description = "A goblin warrior is attacking the village. Use chivalry, might, or skill to defeat it."
        render = GOBLIN_FIGHTER
        approaches(Stat.CHIVALRY, Stat.MIGHT, Stat.SKILL)
        reward { attr("valor", 2) }
    }.register()
    challenge("expose_goblin_occultist") {
        name = "Expose Goblin Occultist"
        description = "A goblin is practicing dark magic. Use concentration, logic, or sorcery to expose it."
        render = GOBLIN_OCCULTIST
        approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
        reward { attr("arcanum", 2) }
    }.register()
    challenge("outrun_goblin_wolf_rider") {
        name = "Outrun Goblin Wolf Rider"
        description = "A goblin on a wolf is chasing you. Use cunning, skill, or technique to outrun it."
        render = GOBLIN_WOLF_RIDER
        approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("capture_halfling_assassin") {
        name = "Capture Halfling Assassin"
        description = "A halfling assassin is targeting nobles. Use cunning, logic, or skill to capture it."
        render = HALFLING_ASSASSIN
        approaches(Stat.CUNNING, Stat.LOGIC, Stat.SKILL)
        reward { attr("valor", 2) }
    }.register()
    challenge("perform_with_halfling_bard") {
        name = "Perform with Halfling Bard"
        description =
            "A halfling bard invites you to perform. Use bewitching magic, charm, or mesmerism to perform."
        render = HALFLING_BARD
        approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
        reward { attr("influence", 2) }
    }.register()
    challenge("track_with_halfling_ranger") {
        name = "Track with Halfling Ranger"
        description = "A halfling ranger offers to teach tracking. Use logic, skill, or technique to track."
        render = HALFLING_RANGER
        approaches(Stat.LOGIC, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("insight", 2) }
    }.register()
    challenge("outwit_halfling_rogue") {
        name = "Outwit Halfling Rogue"
        description =
            "A halfling rogue is stealing from merchants. Use cunning, logic, or technique to outwit it."
        render = HALFLING_ROGUE
        approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
        reward { attr("insight", 2) }
    }.register()
    challenge("compete_with_halfling_slinger") {
        name = "Compete with Halfling Slinger"
        description =
            "A halfling slinger challenges you to a contest. Use charm, skill, or technique to compete."
        render = HALFLING_SLINGER
        approaches(Stat.CHARM, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("scout_with_lizardfolk_scout") {
        name = "Scout with Lizardfolk Scout"
        description = "A lizardfolk scout offers to teach scouting. Use cunning, logic, or skill to scout."
        render = LIZARDFOLK_SCOUT
        approaches(Stat.CUNNING, Stat.LOGIC, Stat.SKILL)
        reward { attr("insight", 2) }
    }.register()
    challenge("spar_lizardfolk_gladiator") {
        name = "Spar with Lizardfolk Gladiator"
        description = "A lizardfolk gladiator offers combat training. Use charm, chivalry, or might to spar."
        render = LIZARDFOLK_GLADIATOR
        approaches(Stat.CHARM, Stat.CHIVALRY, Stat.MIGHT)
        reward { attr("valor", 2) }
    }.register()
    challenge("train_lizardfolk_archer") {
        name = "Train with Lizardfolk Archer"
        description =
            "A lizardfolk archer offers archery training. Use persuasion, skill, or technique to train."
        render = LIZARDFOLK_ARCHER
        approaches(Stat.PERSUASION, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("tame_bestial_lizardfolk") {
        name = "Tame Bestial Lizardfolk"
        description =
            "A savage lizardfolk is attacking travelers. Use bravado, mesmerism, or persuasion to tame it."
        render = BESTIAL_LIZARDFOLK
        approaches(Stat.BRAVADO, Stat.MESMERISM, Stat.PERSUASION)
        reward { attr("influence", 2) }
    }.register()
    challenge("train_lizardfolk_spearman") {
        name = "Train with Lizardfolk Spearman"
        description =
            "A lizardfolk spearman offers weapon training. Use might, persuasion, or skill to train."
        render = LIZARDFOLK_SPEARMAN
        approaches(Stat.MIGHT, Stat.PERSUASION, Stat.SKILL)
        reward { attr("valor", 2) }
    }.register()
