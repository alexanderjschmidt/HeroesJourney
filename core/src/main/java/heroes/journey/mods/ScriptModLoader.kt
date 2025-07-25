package heroes.journey.mods

import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.baseClassLoader
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

object ScriptModLoader {
    private var totalMods: Int = 0
    private var modsFound: Int = 0
    private var modFiles: List<File> = emptyList()
    private var foundMods: MutableList<GameMod> = mutableListOf()
    private var findingInitialized: Boolean = false
    private var debugMode: Boolean = false

    // Background processing
    private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    private val currentProcessingScript = AtomicReference<String>("")
    private val currentProcessingMod = AtomicReference<String>("")
    private val currentModFolder = AtomicReference<String>("")
    private val scriptsProcessed = AtomicInteger(0)
    private val modsProcessed = AtomicInteger(0)
    private var backgroundFindingStarted = false
    private var backgroundLoadingStarted = false

    // ThreadLocal to store current mod folder during script evaluation
    private val currentModFolderThreadLocal = ThreadLocal<String>()

    fun getCurrentProcessingScript(): String = currentProcessingScript.get()

    fun getCurrentProcessingMod(): String = currentProcessingMod.get()

    fun getCurrentModFolder(): String = currentModFolder.get()

    fun getCurrentModFolderFromThread(): String = currentModFolderThreadLocal.get() ?: ""

    fun getScriptsProcessed(): Int = scriptsProcessed.get()

    fun getModsProcessed(): Int = modsProcessed.get()

    fun getModFindingProgress(): Float {
        return if (totalMods == 0) 0f else modsFound.toFloat() / totalMods
    }

    fun isBackgroundFindingComplete(): Boolean {
        return backgroundFindingStarted && scriptsProcessed.get() >= totalMods && totalMods > 0
    }

    fun isBackgroundLoadingComplete(): Boolean {
        return backgroundLoadingStarted && modsProcessed.get() >= foundMods.size && foundMods.isNotEmpty()
    }

    fun getFoundMods(): List<GameMod> = foundMods

    fun startFindingModsFrom(folder: File, debug: Boolean = true) {
        debugMode = debug
        modFiles = folder.walkTopDown().filter { it.name == "mod.kts" }.toList()
        totalMods = modFiles.size
        modsFound = 0
        foundMods = mutableListOf()
        findingInitialized = true

        // Reset background processing
        scriptsProcessed.set(0)
        modsProcessed.set(0)
        currentProcessingScript.set("")
        currentProcessingMod.set("")
        currentModFolder.set("")
        backgroundFindingStarted = false
        backgroundLoadingStarted = false
    }

    fun startBackgroundFinding(debug: Boolean = true) {
        if (backgroundFindingStarted) return
        backgroundFindingStarted = true

        executor.submit {
            modFiles.forEachIndexed { index, scriptFile ->
                currentProcessingScript.set(scriptFile.name)
                // Extract mod folder name from the path
                val modFolder = scriptFile.parentFile.name
                currentModFolder.set(modFolder)
                findMod(scriptFile, debug)
                scriptsProcessed.incrementAndGet()
            }
        }
    }

    fun startBackgroundLoading(debug: Boolean = true) {
        if (backgroundLoadingStarted || foundMods.isEmpty()) return
        backgroundLoadingStarted = true

        executor.submit {
            foundMods.forEach { mod ->
                currentProcessingMod.set(mod.name)
                try {
                    println("Loading mod: ${mod.name}")
                    mod.load()
                } catch (e: Exception) {
                    println("ERROR: Exception while loading mod '${mod.name}'")
                    println("  Exception: ${e.message}")
                    throw e
                } finally {
                    modsProcessed.incrementAndGet()
                }
            }
        }
    }

    fun getModLoadingProgress(mod: GameMod): Float {
        return mod.getLoadingProgress()
    }

    fun getCurrentModLoadingScript(mod: GameMod): String {
        return mod.getCurrentLoadingScript()
    }

    fun getCurrentModGroup(mod: GameMod): Int {
        return mod.getCurrentGroupIndex()
    }

    fun getTotalModGroups(mod: GameMod): Int {
        return mod.getTotalGroups()
    }

    fun getCurrentModGroupName(mod: GameMod): String {
        return mod.getCurrentGroupName()
    }

    private fun findMod(scriptFile: File, debug: Boolean) {
        val compilationConfig = ScriptCompilationConfiguration {
            jvm {
                dependenciesFromCurrentContext(wholeClasspath = true)
            }
        }
        val evaluationConfig = ScriptEvaluationConfiguration {
            jvm {
                baseClassLoader(this::class.java.classLoader)
            }
        }
        val scriptingHost = BasicJvmScriptingHost()
        if (debug) println("\n=== Finding mod from: ${scriptFile.name} ===")
        try {
            // Set the current mod folder in ThreadLocal for the script to access
            val modFolder = scriptFile.parentFile.name
            currentModFolderThreadLocal.set(modFolder)

            val result = scriptingHost.eval(scriptFile.toScriptSource(), compilationConfig, evaluationConfig)
            if (result.reports.isNotEmpty()) {
                if (debug) println("Script reports:")
                result.reports.forEach { report ->
                    val isError = report.severity.name == "ERROR"
                    if (debug || isError) {
                        println("  [${report.severity}] ${report.message}")
                        if (report.location != null) {
                            println("    Location: ${report.location}")
                        }
                        if (report.exception != null) {
                            println("    Exception: ${report.exception}")
                            report.exception!!.printStackTrace()
                        }
                    }
                }
            }
            val returnValue = (result.valueOrNull()?.returnValue as? ResultValue.Value)?.value
            if (returnValue == null) {
                println("ERROR: No return value found in ${scriptFile.name}")
                println("  Expected: A GameMod object returned directly from the script")
                println("  Actual: null")
            } else if (returnValue is GameMod) {
                synchronized(foundMods) {
                    foundMods.add(returnValue)
                }
                if (debug) println("SUCCESS: Found mod '${returnValue.name}' from ${scriptFile.name}")
            } else {
                println("ERROR: Return value in ${scriptFile.name} is not a GameMod")
                println("  Return value type: ${returnValue::class.simpleName}")
                println("  Return value: $returnValue")
                println("  Expected: A GameMod object returned directly from the script")
            }
        } catch (e: Exception) {
            println("ERROR: Exception while finding ${scriptFile.name}")
            println("  Exception: ${e.message}")
            e.printStackTrace()
        } finally {
            // Clean up ThreadLocal
            currentModFolderThreadLocal.remove()
        }
        modsFound++
    }

    fun includeScript(scriptPath: String, debug: Boolean = false, modFolder: String = "") {
        // Handle different types of paths
        val scriptFile = when {
            // Absolute path (starts with / or contains drive letter)
            scriptPath.startsWith("/") || scriptPath.contains(":") -> {
                File(scriptPath)
            }
            // Path starting with / - relative to current mod directory
            scriptPath.startsWith("/") -> {
                val folder = if (modFolder.isNotEmpty()) modFolder else currentModFolder.get()
                File("mods/$folder${scriptPath}")
            }
            // Relative path (contains / but not absolute)
            scriptPath.contains("/") -> {
                val folder = if (modFolder.isNotEmpty()) modFolder else currentModFolder.get()
                File("mods/$folder/$scriptPath")
            }
            // Just filename - try to find in current mod's directory
            else -> {
                val folder = if (modFolder.isNotEmpty()) modFolder else currentModFolder.get()
                val possiblePaths = listOf(
                    "mods/$folder/$scriptPath",
                    scriptPath
                )
                possiblePaths.firstOrNull { File(it).exists() }?.let { File(it) } ?: File(scriptPath)
            }
        }

        if (!scriptFile.exists()) {
            println("WARNING: Include file not found: $scriptPath")
            return
        }
        if (debugMode) println("Including script: $scriptPath")
        val compilationConfig = ScriptCompilationConfiguration {
            jvm {
                dependenciesFromCurrentContext(wholeClasspath = true)
            }
        }
        val evaluationConfig = ScriptEvaluationConfiguration {
            jvm {
                baseClassLoader(this::class.java.classLoader)
            }
        }
        val scriptingHost = BasicJvmScriptingHost()
        try {
            val result = scriptingHost.eval(scriptFile.toScriptSource(), compilationConfig, evaluationConfig)
            if (result.reports.isNotEmpty()) {
                result.reports.forEach { report ->
                    val isError = report.severity.name == "ERROR"
                    if (debugMode || isError) {
                        println("  [${report.severity}] ${report.message} in $scriptPath")
                    }
                }
            }
            // ✅ Check for thrown exceptions during evaluation
            when (val returnValue = result.valueOrNull()?.returnValue) {
                is ResultValue.Error -> {
                    println("ERROR: Script threw an exception: ${returnValue.error}")
                    returnValue.error.printStackTrace()
                }

                is ResultValue.Value -> {
                    if (debugMode) println("Successfully included: $scriptPath")
                }

                else -> {
                    println("Script did not return a value")
                }
            }
        } catch (e: Exception) {
            println("ERROR: Failed to include $scriptPath")
            println("  Exception: ${e.message}")
            e.printStackTrace()
        }
    }

    fun shutdown() {
        executor.shutdown()
    }
}
