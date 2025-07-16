package heroes.journey.mods

import heroes.journey.modlib.QuestDSL
import heroes.journey.modlib.IQuest
import heroes.journey.modlib.IAttributes
import heroes.journey.entities.Quest
import heroes.journey.entities.tagging.Attributes

class QuestDSLImpl : QuestDSL {
    override fun quest(id: String, cost: IAttributes, rewards: IAttributes, fameReward: Int): IQuest {
        val coreCost = cost as? Attributes ?: Attributes()
        val coreRewards = rewards as? Attributes ?: Attributes()
        return Quest(id, coreCost, coreRewards, fameReward)
    }
} 