package heroes.journey.modlib

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
 * Interface for the quest DSL implementation.
 */
interface QuestDSL {
    fun quest(
        id: String,
        cost: IAttributes = attributes(),
        rewards: IAttributes = attributes(),
        fameReward: Int = 0
    ): IQuest
}

/**
 * Singleton provider for the QuestDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object QuestDSLProvider {
    lateinit var instance: QuestDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun quest(
    id: String,
    cost: IAttributes = attributes(),
    rewards: IAttributes = attributes(),
    fameReward: Int = 0
): IQuest =
    QuestDSLProvider.instance.quest(id, cost, rewards, fameReward)
