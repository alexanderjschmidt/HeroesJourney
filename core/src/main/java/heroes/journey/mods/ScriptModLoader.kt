package heroes.journey.mods

import heroes.journey.modding.api.GameMod
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
    fun loadModsFrom(folder: File): List<GameMod> {
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
        val mods = mutableListOf<GameMod>()

        folder.walkTopDown().filter { it.extension == "kts" }.forEach { scriptFile ->
            val result = scriptingHost.eval(scriptFile.toScriptSource(), compilationConfig, evaluationConfig)
            result.reports.forEach {
                println("[${it.severity}] ${it.message}")
            }

            val returnValue = (result.valueOrNull()?.returnValue as? ResultValue.Value)?.value
            if (returnValue is GameMod) {
                mods.add(returnValue)
            } else {
                println("Invalid mod in ${scriptFile.name}")
            }
        }

        return mods
    }
}
