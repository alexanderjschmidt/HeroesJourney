import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.buff
import heroes.journey.modlib.misc.quest

// Quests and Buffs - included by basegame mod

// Example quest: Delve a dungeon
// Cost: 2 VALOR, Reward: 1 INSIGHT
quest(
    id = "delve_dungeon",
    cost = attributes(Ids.STAT_VALOR to 2),
    rewards = attributes(Ids.STAT_INSIGHT to 1),
    fameReward = 10
).register()

// Example quest: Gain physical fitness
// Cost: 1 VALOR, Reward: 2 VALOR + 1 BODY stat
quest(
    id = "gain_fitness",
    cost = attributes(Ids.STAT_VALOR to 1),
    rewards = attributes(Ids.STAT_VALOR to 2, Ids.STAT_BODY to 1),
    fameReward = 15
).register()

// Example quest: Find lost ruin
// Cost: 1 INSIGHT, Reward: 2 ARCANUM
quest(
    id = "find_lost_ruin",
    cost = attributes(Ids.STAT_INSIGHT to 1),
    rewards = attributes(Ids.STAT_ARCANUM to 2),
    fameReward = 25
).register()

// Example quest: Study ancient texts
// Cost: 1 INSIGHT, Reward: 2 MIND + 1 INSIGHT
quest(
    id = "study_texts",
    cost = attributes(Ids.STAT_INSIGHT to 1),
    rewards = attributes(Ids.STAT_MIND to 2, Ids.STAT_INSIGHT to 1),
    fameReward = 20
).register()

// Example quest: Perform a noble deed
// Cost: 2 INFLUENCE, Reward: 3 INFLUENCE + 1 CHARISMA
quest(
    id = "noble_deed",
    cost = attributes(Ids.STAT_INFLUENCE to 2),
    rewards = attributes(Ids.STAT_INFLUENCE to 3, Ids.STAT_CHARISMA to 1),
    fameReward = 30
).register()

// Example quest: Master the arcane
// Cost: 2 ARCANUM, Reward: 3 ARCANUM + 1 MAGIC
quest(
    id = "master_arcane",
    cost = attributes(Ids.STAT_ARCANUM to 2),
    rewards = attributes(Ids.STAT_ARCANUM to 3, Ids.STAT_MAGIC to 1),
    fameReward = 35
).register()

// Example Buff usage
buff("rested", 1).register()
