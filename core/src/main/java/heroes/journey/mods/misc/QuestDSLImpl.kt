package heroes.journey.mods.misc

import heroes.journey.entities.Quest
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.IQuest
import heroes.journey.modlib.misc.QuestDSL
import heroes.journey.modlib.misc.QuestBuilder
import heroes.journey.modlib.attributes.AttributesBuilder
import heroes.journey.modlib.attributes.attributes

class QuestBuilderImpl : QuestBuilder {
    override var id: String = ""
    override var fameReward: Int = 0
    private var _cost: IAttributes? = null
    private var _rewards: IAttributes? = null
    override fun cost(init: AttributesBuilder.() -> Unit) {
        _cost = attributes(init)
    }
    override fun rewards(init: AttributesBuilder.() -> Unit) {
        _rewards = attributes(init)
    }
    fun builtCost(): Attributes = (_cost as? Attributes) ?: heroes.journey.entities.tagging.Attributes()
    fun builtRewards(): Attributes = (_rewards as? Attributes) ?: heroes.journey.entities.tagging.Attributes()
}

class QuestDSLImpl : QuestDSL {
    override fun quest(init: QuestBuilder.() -> Unit): IQuest {
        val builder = QuestBuilderImpl()
        builder.init()
        return Quest(builder.id, builder.builtCost(), builder.builtRewards(), builder.fameReward)
    }
}
