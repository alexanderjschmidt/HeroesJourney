package heroes.journey.mods.misc

import heroes.journey.entities.Quest
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.IQuest
import heroes.journey.modlib.misc.QuestDSL

class QuestDSLImpl : QuestDSL {
    override fun quest(id: String, cost: IAttributes, rewards: IAttributes, fameReward: Int): IQuest {
        val coreCost = cost as? Attributes ?: Attributes()
        val coreRewards = rewards as? Attributes ?: Attributes()
        return Quest(id, coreCost, coreRewards, fameReward)
    }
}
