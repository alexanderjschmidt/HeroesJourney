package heroes.journey.utils

import heroes.journey.modlib.ILang
import heroes.journey.mods.GameMod
import heroes.journey.mods.ScriptModLoader
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

object Lang : ILang {
    private var currentLanguage: String = "en"
    private val langMap: MutableMap<String, String> = mutableMapOf()

    // Async loading state
    private val isLoading = AtomicBoolean(false)
    private val modsLoaded = AtomicInteger(0)
    private val totalMods = AtomicInteger(0)
    private val currentMod = AtomicReference<String>("")
    private var onComplete: (() -> Unit)? = null

    fun startAsyncLoad(language: String, onComplete: (() -> Unit)? = null) {
        if (isLoading.get()) return
        isLoading.set(true)
        this.onComplete = onComplete
        currentLanguage = language
        langMap.clear()
        val mods = ScriptModLoader.getFoundMods()
        totalMods.set(mods.size)
        modsLoaded.set(0)
        currentMod.set("")
        if (mods.isEmpty()) {
            isLoading.set(false)
            onComplete?.invoke()
            return
        }
        val executor = Executors.newSingleThreadExecutor()
        executor.submit {
            for (mod: GameMod in mods) {
                currentMod.set(mod.name)
                val modFolder = File("mods/${mod.getModFolder()}")
                if (modFolder.exists() && modFolder.isDirectory) {
                    loadLangFilesRecursively(modFolder, language)
                }
                modsLoaded.incrementAndGet()
            }
            isLoading.set(false)
            currentMod.set("")
            onComplete?.invoke()
            executor.shutdown()
        }
    }

    private fun loadLangFilesRecursively(folder: File, language: String) {
        val langFileName = "$language.lang"
        folder.walkTopDown().forEach { file ->
            if (file.isFile && file.name == langFileName) {
                loadLangFile(file)
            }
        }
    }

    private fun loadLangFile(file: File) {
        file.forEachLine { line ->
            val trimmed = line.trim()
            if (trimmed.isEmpty() || trimmed.startsWith("#")) return@forEachLine
            val idx = trimmed.indexOf('=')
            if (idx > 0) {
                val key = trimmed.substring(0, idx).trim()
                val value = trimmed.substring(idx + 1).trim()
                langMap[key] = value
            }
        }
    }

    override fun get(key: String): String {
        val value = langMap[key]
        if (value == null && (key.endsWith("_name") || key.endsWith("_description"))) {
            System.err.println("[Lang] Missing translation for key: '$key' in language '$currentLanguage'")
        }
        return value ?: key
    }

    fun setLanguage(language: String) {
        currentLanguage = language
        langMap.clear()
        val mods = ScriptModLoader.getFoundMods()
        for (mod in mods) {
            val modFolder = File("mods/${mod.getModFolder()}")
            if (modFolder.exists() && modFolder.isDirectory) {
                loadLangFilesRecursively(modFolder, language)
            }
        }
    }

    fun getCurrentLanguage(): String = currentLanguage

    // Async loading progress API
    fun isLoading(): Boolean = isLoading.get()
    fun getProgress(): Float = if (totalMods.get() == 0) 1f else modsLoaded.get().toFloat() / totalMods.get()
    fun getCurrentMod(): String = currentMod.get()
}
