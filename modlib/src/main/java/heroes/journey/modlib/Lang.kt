package heroes.journey.modlib

interface ILang {
    fun get(key: String): String
}

/**
 * Singleton provider for the RenderableDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object Lang {
    lateinit var instance: ILang
}
