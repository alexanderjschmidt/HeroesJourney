package heroes.journey.mods

import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class GameMod(val name: String, private val onLoadBlock: GameMod.() -> Unit, val priority: Int) :
    Comparable<GameMod> {

    init {
        log("Found")
    }

    fun load() {
        onLoadBlock()
        log("Loaded")
    }

    fun log(message: String) {
        println("[MOD: $name] $message")
    }

    fun includeScript(scriptPath: String, debug: Boolean = false) {
        ScriptModLoader.includeScript(scriptPath, debug)
    }

    fun includeScripts(vararg scriptPaths: String, debug: Boolean = false, parallel: Boolean = true) {
        if (parallel) {
            // Use thread pool for parallel loading
            val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
            val futures = scriptPaths.map { path ->
                executor.submit {
                    try {
                        includeScript(path, debug)
                    } catch (e: Exception) {
                        log("ERROR: Failed to load $path: ${e.message}")
                    }
                }
            }

            // Wait for all to complete
            futures.forEach { it.get() }
            executor.shutdown()
            executor.awaitTermination(30, TimeUnit.SECONDS)
        } else {
            // Sequential loading
            scriptPaths.forEach { path ->
                includeScript(path, debug)
            }
        }
    }

    fun includeScriptsFromDirectory(directoryPath: String, debug: Boolean = false, parallel: Boolean = true) {
        val directory = File(directoryPath)
        if (!directory.exists() || !directory.isDirectory) {
            log("WARNING: Directory not found or not a directory: $directoryPath")
            return
        }

        val scriptFiles = directory.listFiles { file ->
            file.isFile && file.extension == "kts"
        }?.map { it.absolutePath }?.toTypedArray()

        if (scriptFiles.isNullOrEmpty()) {
            log("WARNING: No .kts files found in directory: $directoryPath")
            return
        }

        log("Found ${scriptFiles.size} script files in directory: $directoryPath")
        includeScripts(*scriptFiles, debug = debug, parallel = parallel)
    }

    override fun toString(): String {
        return name
    }

    override fun compareTo(other: GameMod): Int {
        // Higher priority loads first, so descending order
        return other.priority.compareTo(this.priority)
    }
}

fun gameMod(name: String, priority: Int = 0, onLoad: GameMod.() -> Unit): GameMod {
    return GameMod(name, onLoad, priority)
}
