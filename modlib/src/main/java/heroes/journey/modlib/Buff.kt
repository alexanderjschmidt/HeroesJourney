package heroes.journey.modlib

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
 * Interface for the buff DSL implementation.
 */
interface BuffDSL {
    fun buff(id: String, turnsBuffLasts: Int, attributes: IAttributes = attributes()): IBuff
}

/**
 * Singleton provider for the BuffDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object BuffDSLProvider {
    lateinit var instance: BuffDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun buff(id: String, turnsBuffLasts: Int, attributes: IAttributes = attributes()): IBuff =
    BuffDSLProvider.instance.buff(id, turnsBuffLasts, attributes)
