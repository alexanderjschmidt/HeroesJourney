import heroes.journey.entities.Buff
import heroes.journey.entities.Quest
import heroes.journey.entities.questCost
import heroes.journey.entities.questRewards
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.Ids

// Quests and Buffs - included by basegame mod

// Example quest: Delve a dungeon
// Cost: 2 VALOR, Reward: 1 INSIGHT
Quest(
    "delve_dungeon",
    cost = questCost(Ids.STAT_VALOR to 2),
    rewards = questRewards(Ids.STAT_INSIGHT to 1),
    10
).register()

// Example quest: Gain physical fitness
// Cost: 1 VALOR, Reward: 2 VALOR + 1 BODY stat
Quest(
    "gain_fitness",
    cost = questCost(Ids.STAT_VALOR to 1),
    rewards = questRewards(Ids.STAT_VALOR to 2, Ids.STAT_BODY to 1),
    15
).register()

// Example quest: Find lost ruin
// Cost: 1 INSIGHT, Reward: 2 ARCANUM
Quest(
    "find_lost_ruin",
    cost = questCost(Ids.STAT_INSIGHT to 1),
    rewards = questRewards(Ids.STAT_ARCANUM to 2),
    25
).register()

// Example quest: Study ancient texts
// Cost: 1 INSIGHT, Reward: 2 MIND + 1 INSIGHT
Quest(
    "study_texts",
    cost = questCost(Ids.STAT_INSIGHT to 1),
    rewards = questRewards(Ids.STAT_MIND to 2, Ids.STAT_INSIGHT to 1),
    20
).register()

// Example quest: Perform a noble deed
// Cost: 2 INFLUENCE, Reward: 3 INFLUENCE + 1 CHARISMA
Quest(
    "noble_deed",
    cost = questCost(Ids.STAT_INFLUENCE to 2),
    rewards = questRewards(Ids.STAT_INFLUENCE to 3, Ids.STAT_CHARISMA to 1),
    30
).register()

// Example quest: Master the arcane
// Cost: 2 ARCANUM, Reward: 3 ARCANUM + 1 MAGIC
Quest(
    "master_arcane",
    cost = questCost(Ids.STAT_ARCANUM to 2),
    rewards = questRewards(Ids.STAT_ARCANUM to 3, Ids.STAT_MAGIC to 1),
    35
).register()

Buff("rested", 1, Attributes()).register();
