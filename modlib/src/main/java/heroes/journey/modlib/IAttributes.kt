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