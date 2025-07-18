package heroes.journey.modlib.attributes

/**
 * Public interface for a stat container, used for character and challenge attributes.
 * Mods should only use this interface, not implementation classes.
 * Provides read-only access to stat values by ID or IStat.
 */
interface IAttributes {
    /**
     * Get the value of a stat by its string ID, applying all formulas and modifiers.
     * @param statId the stat ID (see [Ids])
     * @return the calculated value for the stat
     */
    fun get(statId: String): Int

    /**
     * Get the value of a stat by its IStat, applying all formulas and modifiers.
     * @param stat the stat
     * @return the calculated value for the stat
     */
    fun get(stat: IStat): Int

    /**
     * Get the direct (raw) value of a stat by its string ID, ignoring formulas.
     * @param statId the stat ID (see [Ids])
     * @return the direct value for the stat
     */
    fun getDirect(statId: String): Int

    /**
     * Get the direct (raw) value of a stat by its IStat, ignoring formulas.
     * @param stat the stat
     * @return the direct value for the stat
     */
    fun getDirect(stat: IStat): Int

    fun add(
        stat: IStat,
        value: Int
    ): IAttributes

    fun add(stat: String?, value: Int): IAttributes
}

/**
 * Builder for defining attributes in a natural DSL style.
 */
interface AttributesBuilder {
    fun stat(id: String, value: Int)
}

/**
 * Interface for the attributes DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface AttributesDSL {
    fun attributes(init: AttributesBuilder.() -> Unit): IAttributes
}

/**
 * Singleton provider for the AttributesDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object AttributesDSLProvider {
    lateinit var instance: AttributesDSL
}

/**
 * DSL entrypoint for defining attributes using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * attributes {
 *     stat(Ids.STAT_BODY, 3)
 *     stat(Ids.STAT_MIND, 2)
 * }
 * ```
 */
fun attributes(init: AttributesBuilder.() -> Unit): IAttributes = AttributesDSLProvider.instance.attributes(init)
