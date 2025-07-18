package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Quest, used for quest definitions and completion logic.
 * Mods should only use this interface, not implementation classes.
 */
interface IQuest : IRegistrable {
    /** The cost attributes required to complete the quest. */
    val cost: IAttributes

    /** The reward attributes granted by the quest. */
    val rewards: IAttributes

    /** The fame reward granted by the quest. */
    val fameReward: Int

    /** Check if the quest can be afforded by the given input (game-specific context). */
    fun canAfford(input: Any): Boolean

    /** Complete the quest, applying costs and rewards. Returns true if successful. */
    fun onComplete(input: Any): Boolean
    override fun register(): IQuest
}

/**
 * Builder for defining a quest in a natural DSL style.
 */
interface QuestBuilder {
    var id: String
    var fameReward: Int
    fun cost(init: heroes.journey.modlib.attributes.AttributesBuilder.() -> Unit)
    fun rewards(init: heroes.journey.modlib.attributes.AttributesBuilder.() -> Unit)
}

/**
 * Interface for the quest DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface QuestDSL {
    fun quest(init: QuestBuilder.() -> Unit): IQuest
}

/**
 * Singleton provider for the QuestDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object QuestDSLProvider {
    lateinit var instance: QuestDSL
}

/**
 * DSL entrypoint for defining a quest using a builder lambda.
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
fun quest(init: QuestBuilder.() -> Unit): IQuest = QuestDSLProvider.instance.quest(init)
