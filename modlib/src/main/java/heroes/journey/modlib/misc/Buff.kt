package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Buff, used for temporary stat modifications.
 * Mods should only use this interface, not implementation classes.
 */
interface IBuff : IRegistrable {

    /** The number of turns the buff lasts. */
    val turnsBuffLasts: Int

    /** The attributes this buff grants. */
    val attributes: IAttributes
    override fun register(): IBuff
}

/**
 * Builder for defining a buff in a natural DSL style.
 */
interface BuffBuilder {
    var id: String
    var turnsBuffLasts: Int
    fun attributes(init: heroes.journey.modlib.attributes.AttributesBuilder.() -> Unit)
}

/**
 * Interface for the buff DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface BuffDSL {
    fun buff(init: BuffBuilder.() -> Unit): IBuff
}

/**
 * Singleton provider for the BuffDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object BuffDSLProvider {
    lateinit var instance: BuffDSL
}

/**
 * DSL entrypoint for defining a buff using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * buff {
 *     id = "rested"
 *     turnsBuffLasts = 3
 *     attributes {
 *         stat(Ids.STAT_BODY, 2)
 *     }
 * }
 * ```
 */
fun buff(init: BuffBuilder.() -> Unit): IBuff = BuffDSLProvider.instance.buff(init)
