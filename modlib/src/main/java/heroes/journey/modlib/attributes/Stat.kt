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
    val formula: (IAttributes) -> Int?
    val defaultValue: Int?
    fun getMin(attributes: IAttributes): Int
    fun getMax(attributes: IAttributes): Int
    override fun register(): IStat
}

/**
 * Builder for defining a stat in a natural DSL style.
 */
interface StatBuilder {
    var id: String
    var min: Int // Direct minimum value if no group-based min stat exists
    var max: Int // Direct maximum value if no group-based max stat exists
    var formula: ((IAttributes) -> Int?)?
    var defaultValue: Int?
    fun group(id: String)
}

/**
 * Interface for the stat DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface StatDSL {
    fun stat(init: StatBuilder.() -> Unit): IStat
}

/**
 * Singleton provider for the StatDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object StatDSLProvider {
    lateinit var instance: StatDSL
}

/**
 * DSL entrypoint for defining a new stat using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * stat {
 *     id = Ids.STAT_BODY
 *     min = 1
 *     max = 10
 *     group(Ids.GROUP_BASESTATS)
 *     group(Ids.GROUP_BODY)
 * }
 * ```
 */
fun stat(init: StatBuilder.() -> Unit): IStat = StatDSLProvider.instance.stat(init)
