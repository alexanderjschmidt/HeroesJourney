package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A Quest, used for quest definitions and data storage.
 * This is a simple data container with no complex functions.
 * Quest logic is handled by QuestUtils in the core module.
 */
class Quest(
    id: String,
    val cost: IAttributes,
    val rewards: IAttributes,
    val fameReward: Int = 0
) : Registrable(id) {

    override fun register(): Quest {
        Registries.QuestManager.register(this)
        return this
    }
}

/**
 * Builder for defining a quest in a natural DSL style.
 */
class QuestBuilder {
    var id: String = ""
    var fameReward: Int = 0
    private var _cost: IAttributes? = null
    private var _rewards: IAttributes? = null

    fun cost(init: heroes.journey.modlib.attributes.AttributesBuilder.() -> Unit) {
        _cost = attributes(init)
    }

    fun rewards(init: heroes.journey.modlib.attributes.AttributesBuilder.() -> Unit) {
        _rewards = attributes(init)
    }

    fun builtCost(): IAttributes = _cost ?: attributes { }
    fun builtRewards(): IAttributes = _rewards ?: attributes { }
}

/**
 * DSL entrypoint for defining a quest.
 *
 * Example usage:
 * ```kotlin
 * quest {
 *     id = Ids.MY_QUEST
 *     fameReward = 10
 *     cost {
 *         stat(Ids.STAT_VALOR, 2)
 *     }
 *     rewards {
 *         stat(Ids.STAT_INSIGHT, 1)
 *     }
 * }
 * ```
 */
fun quest(init: QuestBuilder.() -> Unit): Quest {
    val builder = QuestBuilder()
    builder.init()
    return Quest(builder.id, builder.builtCost(), builder.builtRewards(), builder.fameReward)
}
