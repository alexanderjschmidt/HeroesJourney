package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.Attributes
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
    val cost: Attributes,
    val rewards: Attributes,
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
    private var _cost: Attributes? = null
    private var _rewards: Attributes? = null

    fun cost(init: heroes.journey.modlib.attributes.AttributesBuilder.() -> Unit) {
        _cost = attributes(init)
    }

    fun rewards(init: heroes.journey.modlib.attributes.AttributesBuilder.() -> Unit) {
        _rewards = attributes(init)
    }

    fun builtCost(): Attributes = _cost ?: attributes { }
    fun builtRewards(): Attributes = _rewards ?: attributes { }
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
