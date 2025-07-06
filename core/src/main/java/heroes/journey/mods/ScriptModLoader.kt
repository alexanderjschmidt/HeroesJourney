package heroes.journey.mods

import java.io.File
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
    fun loadModsFrom(folder: File, debug: Boolean = true): List<GameMod> {
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
        var mods = mutableListOf<GameMod>()

        for (scriptFile in folder.walkTopDown().filter { it.name == "mod.kts" }) {
            if (debug) println("\n=== Finding mod from: ${scriptFile.name} ===")
            try {
                val result =
                    scriptingHost.eval(scriptFile.toScriptSource(), compilationConfig, evaluationConfig)
                // Log all reports (errors, warnings, etc.)
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
                    continue
                }
                if (debug) println("Found return value of type: ${returnValue::class.simpleName}")
                if (returnValue is GameMod) {
                    mods.add(returnValue)
                    if (debug) println("SUCCESS: Added mod '${returnValue.name}' from ${scriptFile.name}")
                } else {
                    println("ERROR: Return value in ${scriptFile.name} is not a GameMod")
                    println("  Return value type: ${returnValue::class.simpleName}")
                    println("  Return value: $returnValue")
                    println("  Expected: A GameMod object returned directly from the script")
                }
            } catch (e: Exception) {
                println("ERROR: Exception while loading ${scriptFile.name}")
                println("  Exception: ${e.message}")
                e.printStackTrace()
            }
        }
        println("\n=== Mod Finding Summary ===")
        println("Successfully found ${mods.size} mod(s):")
        mods = mods.sortedDescending().stream().toList()
        mods.forEach { mod ->
            println("  - ${mod.name} (priority: ${mod.priority})")
        }
        return mods
    }

    // Function to include other script files within a mod
    fun includeScript(scriptPath: String, debug: Boolean = false) {
        val scriptFile = File(scriptPath)
        if (!scriptFile.exists()) {
            println("WARNING: Include file not found: $scriptPath")
            return
        }

        if (debug) println("Including script: $scriptPath")

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
                    if (debug || isError) {
                        println("  [${report.severity}] ${report.message} in $scriptPath")
                    }
                }
            }

            if (debug) println("Successfully included: $scriptPath")
        } catch (e: Exception) {
            println("ERROR: Failed to include $scriptPath")
            println("  Exception: ${e.message}")
            e.printStackTrace()
        }
    }
}
