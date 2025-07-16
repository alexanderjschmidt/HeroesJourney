package heroes.journey.modlib

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
}

/**
 * Interface for the attributes DSL implementation.
 */
interface AttributesDSL {
    /**
     * Create an IAttributes object from a list of (statId, value) pairs.
     * Example: attributes("body" to 3, "mind" to 2)
     */
    fun attributes(vararg pairs: Pair<String, Int>): IAttributes
}

/**
 * Singleton provider for the AttributesDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object AttributesDSLProvider {
    lateinit var instance: AttributesDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun attributes(vararg pairs: Pair<String, Int>): IAttributes =
    AttributesDSLProvider.instance.attributes(*pairs) 