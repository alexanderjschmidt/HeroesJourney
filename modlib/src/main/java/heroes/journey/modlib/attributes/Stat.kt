package heroes.journey.modlib.attributes

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Stat, used for character and challenge stats.
 * Mods should only use this interface, not implementation classes.
 *
 * Example usage:
 * ```kotlin
 * stat(id = Ids.MY_STAT, min = 0, max = 10, groups = listOf(group(Ids.MY_GROUP))).register()
 * ```
 */
interface IStat : IRegistrable {
    val min: Int
    val max: Int
    val groups: List<IGroup>
    val formula: (IAttributes) -> Int
    override fun register(): IStat
}

/**
 * Interface for the stat DSL implementation.
 */
interface StatDSL {
    fun stat(
        id: String,
        min: Int = 1,
        max: Int = 10,
        groups: List<IGroup> = emptyList(),
        formula: ((IAttributes) -> Int)? = null
    ): IStat
}

/**
 * Singleton provider for the StatDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object StatDSLProvider {
    lateinit var instance: StatDSL
}

/**
 * DSL entrypoint for defining a new stat.
 * @param id The unique stat ID.
 * @param min The minimum value (default: 1).
 * @param max The maximum value (default: 10).
 * @param groups The stat groups (optional).
 * @param formula Optional formula for derived stats.
 * @return The created [IStat] instance.
 *
 * Example usage:
 * ```kotlin
 * stat(id = Ids.MY_STAT, min = 0, max = 10, groups = listOf(group(Ids.MY_GROUP))).register()
 * ```
 */
fun stat(
    id: String,
    min: Int = 1,
    max: Int = 10,
    groups: List<IGroup> = emptyList(),
    formula: ((IAttributes) -> Int)? = null
): IStat = StatDSLProvider.instance.stat(id, min, max, groups, formula)
