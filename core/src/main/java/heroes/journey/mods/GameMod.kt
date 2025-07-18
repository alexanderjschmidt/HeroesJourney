package heroes.journey.mods

import heroes.journey.modlib.GameModDSL
import heroes.journey.modlib.IGameMod
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

data class ScriptTask(val path: String, val parallel: Boolean)

data class ScriptGroup(val name: String, val tasks: MutableList<ScriptTask>)

/**
 * Core implementation of the GameModDSL interface.
 * Delegates to the core GameMod class for actual functionality.
 */
class GameModDSLImpl : GameModDSL {
    override fun gameMod(
        name: String,
        priority: Int,
        debug: Boolean,
        onInit: IGameMod.() -> Unit
    ): IGameMod {
        val modFolder = ScriptModLoader.getCurrentModFolder()
        val coreGameMod = GameMod(name, onInit as GameMod.() -> Unit, priority, debug, modFolder)
        return coreGameMod
    }
}

class GameMod(
    val name: String,
    private val onInitBlock: GameMod.() -> Unit,
    val priority: Int,
    private val debug: Boolean = false,
    private val modFolder: String = ""
) :
    Comparable<GameMod>, IGameMod {

    private val scriptGroups = mutableListOf<ScriptGroup>()
    private var totalScripts = 0
    private var scriptsLoaded = 0
    private var currentLoadingScript = ""
    private var isInitialized = false
    private var currentGroupIndex = 0

    init {
        onInitBlock()
        isInitialized = true
        log("Initialized with $totalScripts scripts to load in ${scriptGroups.size} groups")
    }

    fun load() {
        if (!isInitialized) {
            log("ERROR: Cannot load before initialization")
            return
        }

        log("Starting to load $totalScripts scripts in ${scriptGroups.size} groups")

        scriptGroups.forEachIndexed { groupIndex, group ->
            currentGroupIndex = groupIndex
            log("Loading group: ${group.name} (${groupIndex + 1}/${scriptGroups.size}) with ${group.tasks.size} scripts")

            if (group.tasks.isNotEmpty()) {
                val parallelTasks = group.tasks.filter { it.parallel }
                val sequentialTasks = group.tasks.filter { !it.parallel }

                // Load parallel tasks first within this group
                if (parallelTasks.isNotEmpty()) {
                    loadScriptsParallel(parallelTasks)
                }

                // Load sequential tasks within this group
                if (sequentialTasks.isNotEmpty()) {
                    loadScriptsSequential(sequentialTasks)
                }
            }
        }

        log("Loaded all $totalScripts scripts")
    }

    private fun loadScriptsParallel(tasks: List<ScriptTask>) {
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val futures = tasks.map { task ->
            executor.submit {
                try {
                    currentLoadingScript = task.path
                    ScriptModLoader.includeScript(task.path, debug, modFolder)
                    scriptsLoaded++
                } catch (e: Exception) {
                    log("ERROR: Failed to load ${task.path}: ${e.message}")
                }
            }
        }

        // Wait for all to complete
        futures.forEach { it.get() }
        executor.shutdown()
        executor.awaitTermination(30, TimeUnit.SECONDS)
    }

    private fun loadScriptsSequential(tasks: List<ScriptTask>) {
        tasks.forEach { task ->
            try {
                currentLoadingScript = task.path
                ScriptModLoader.includeScript(task.path, debug, modFolder)
                scriptsLoaded++
            } catch (e: Exception) {
                log("ERROR: Failed to load ${task.path}: ${e.message}")
            }
        }
    }

    fun getLoadingProgress(): Float {
        return if (totalScripts == 0) 1.0f else scriptsLoaded.toFloat() / totalScripts
    }

    fun getCurrentLoadingScript(): String = currentLoadingScript

    fun getCurrentGroupIndex(): Int = currentGroupIndex

    fun getCurrentGroupName(): String {
        return if (currentGroupIndex < scriptGroups.size) scriptGroups[currentGroupIndex].name else ""
    }

    fun getTotalGroups(): Int = scriptGroups.size

    fun getTotalScripts(): Int = totalScripts

    fun getScriptsLoaded(): Int = scriptsLoaded

    fun getModFolder(): String = modFolder

    fun log(message: String) {
        println("[MOD: $name] $message")
    }

    override fun includeScript(scriptPath: String) {
        // Create a new group for this sequential script
        val newGroup =
            ScriptGroup("Script: ${File(scriptPath).name}", mutableListOf(ScriptTask(scriptPath, false)))
        scriptGroups.add(newGroup)
        totalScripts++
    }

    override fun includeScripts(groupName: String, vararg scriptPaths: String, parallel: Boolean) {
        // Create a new group for these scripts
        val newGroup = ScriptGroup(groupName, mutableListOf())
        scriptPaths.forEach { path ->
            newGroup.tasks.add(ScriptTask(path, parallel))
            totalScripts++
        }
        scriptGroups.add(newGroup)
    }

    override fun includeScriptsFromDirectory(
        groupName: String,
        directoryPath: String,
        parallel: Boolean
    ) {
        // Use the mod's own folder instead of getting it from ScriptModLoader
        val modDirectory = File("mods/$modFolder")
        val directory = File(modDirectory, directoryPath)

        if (!directory.exists() || !directory.isDirectory) {
            log("WARNING: Directory not found or not a directory: $directoryPath in mod $modFolder")
            return
        }

        val scriptFiles = directory.listFiles { file ->
            file.isFile && file.extension == "kts"
        }?.map { file ->
            // Create path relative to the mod directory (not including mod folder name)
            val relativePath = file.absolutePath.substring(modDirectory.absolutePath.length + 1)
            relativePath.replace("\\", "/") // Normalize path separators
        }?.toTypedArray()

        if (scriptFiles.isNullOrEmpty()) {
            log("WARNING: No .kts files found in directory: $directoryPath")
            return
        }

        log("Found ${scriptFiles.size} script files in directory: $directoryPath")
        includeScripts(groupName, *scriptFiles, parallel = parallel)
    }

    override fun toString(): String {
        return name
    }

    override fun compareTo(other: GameMod): Int {
        // Higher priority loads first, so descending order
        return other.priority.compareTo(this.priority)
    }
}

// Legacy function for backward compatibility
fun gameMod(
    name: String,
    priority: Int = 0,
    debug: Boolean = false,
    onInit: GameMod.() -> Unit
): GameMod {
    val modFolder = ScriptModLoader.getCurrentModFolder()
    return GameMod(name, onInit, priority, debug, modFolder)
}
