package heroes.journey.modlib

/**
 * Public interface for a GameMod, used for mod registration and script loading.
 * Mods should only use this interface, not implementation classes.
 *
 * Example usage:
 * ```kotlin
 * gameMod("My Mod") {
 *     includeScript("actions.kts")
 *     includeScriptsFromDirectory("challenges", "challenges")
 * }
 * ```
 */
interface IGameMod {
    /**
     * Include a single script file in the mod.
     * @param scriptPath The name of the script file to include.
     */
    fun includeScript(scriptPath: String)

    /**
     * Include multiple script files in the mod.
     * @param groupName The name of the script group.
     * @param scriptPaths The names of the script files to include.
     * @param parallel Whether to load scripts in parallel (default: false).
     */
    fun includeScripts(groupName: String, vararg scriptPaths: String, parallel: Boolean = false)

    /**
     * Include all script files from a directory in the mod.
     * @param groupName The name of the script group.
     * @param directoryPath The name of the directory containing scripts.
     * @param parallel Whether to load scripts in parallel (default: true).
     */
    fun includeScriptsFromDirectory(
        groupName: String, directoryPath: String,
        parallel: Boolean = true
    )
}

/**
 * Interface for the GameMod DSL implementation.
 * Used internally by modlib; modders should use the [gameMod] DSL entrypoint.
 */
interface GameModDSL {
    fun gameMod(
        name: String,
        priority: Int = 0,
        debug: Boolean = false,
        onInit: IGameMod.() -> Unit
    ): IGameMod
}

/**
 * Singleton provider for the GameModDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object GameModProvider {
    lateinit var instance: GameModDSL
}

/**
 * DSL entrypoint for creating a game mod.
 * @param name The name of the mod.
 * @param priority The loading priority of the mod (higher numbers load first).
 * @param debug Whether to enable debug mode for the mod.
 * @param onInit The initialization block for the mod.
 * @return The created [IGameMod] instance.
 *
 * Example usage:
 * ```kotlin
 * gameMod("My Mod") {
 *     includeScript("actions.kts")
 * }
 * ```
 */
fun gameMod(
    name: String,
    priority: Int = 0,
    debug: Boolean = false,
    onInit: IGameMod.() -> Unit
): IGameMod = GameModProvider.instance.gameMod(name, priority, debug, onInit)
