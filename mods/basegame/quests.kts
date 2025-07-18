import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.buff
import heroes.journey.modlib.misc.quest

// Quests and Buffs - included by basegame mod

// Example quest: Delve a dungeon
// Cost: 2 VALOR, Reward: 1 INSIGHT
quest {
    id = Ids.DELVE_DUNGEON
    cost { stat(Ids.STAT_VALOR, 2) }
    rewards { stat(Ids.STAT_INSIGHT, 1) }
    fameReward = 10
}.register()

// Example quest: Gain physical fitness
// Cost: 1 VALOR, Reward: 2 VALOR + 1 BODY stat
quest {
    id = Ids.GAIN_FITNESS
    cost { stat(Ids.STAT_VALOR, 1) }
    rewards {
        stat(Ids.STAT_VALOR, 2)
        stat(Ids.STAT_BODY, 1)
    }
    fameReward = 15
}.register()

// Example quest: Find lost ruin
// Cost: 1 INSIGHT, Reward: 2 ARCANUM
quest {
    id = Ids.FIND_LOST_RUIN
    cost { stat(Ids.STAT_INSIGHT, 1) }
    rewards { stat(Ids.STAT_ARCANUM, 2) }
    fameReward = 25
}.register()

// Example quest: Study ancient texts
// Cost: 1 INSIGHT, Reward: 2 MIND + 1 INSIGHT
quest {
    id = Ids.STUDY_TEXTS
    cost { stat(Ids.STAT_INSIGHT, 1) }
    rewards {
        stat(Ids.STAT_MIND, 2)
        stat(Ids.STAT_INSIGHT, 1)
    }
    fameReward = 20
}.register()

// Example quest: Perform a noble deed
// Cost: 2 INFLUENCE, Reward: 3 INFLUENCE + 1 CHARISMA
quest {
    id = Ids.NOBLE_DEED
    cost { stat(Ids.STAT_INFLUENCE, 2) }
    rewards {
        stat(Ids.STAT_INFLUENCE, 3)
        stat(Ids.STAT_CHARISMA, 1)
    }
    fameReward = 30
}.register()

// Example quest: Master the arcane
// Cost: 2 ARCANUM, Reward: 3 ARCANUM + 1 MAGIC
quest {
    id = Ids.MASTER_ARCANE
    cost { stat(Ids.STAT_ARCANUM, 2) }
    rewards {
        stat(Ids.STAT_ARCANUM, 3)
        stat(Ids.STAT_MAGIC, 1)
    }
    fameReward = 35
}.register()

// Example Buff usage
buff {
    id = "rested"
    turnsBuffLasts = 1
}.register()
