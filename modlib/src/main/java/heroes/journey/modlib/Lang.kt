package heroes.journey.modlib

/**
 * Interface for localization support in mods.
 * Use [Lang.instance.get] to retrieve localized strings by key.
 *
 * Example usage:
 * ```kotlin
 * val name = Lang.instance.get("my_item_name")
 * ```
 */
interface ILang {
    fun name(key: String): String
    fun description(key: String): String
}

/**
 * Singleton provider for the ILang implementation.
 * The core game must set this before any mods are loaded.
 *
 * Example usage:
 * ```kotlin
 * Lang.instance.get("my_key")
 * ```
 */
object Lang {
    lateinit var instance: ILang
}
