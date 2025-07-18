package heroes.journey.modlib.worldgen

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a TileBatch, used for tile batch definitions in worldgen.
 * Mods should only use this interface, not implementation classes.
 */
interface ITileBatch : IRegistrable {
    val layout: String
    val textureMap: String
    val terrains: Map<String, String>
    val weight: Int
    val startX: Int
    val startY: Int
    val addToDefault: Boolean
    val frameCount: Int
    val frameDist: Int
    override fun register(): ITileBatch
}

/**
 * Interface for the tile batch DSL implementation.
 */
interface TileBatchDSL {
    fun tileBatch(init: TileBatchBuilder.() -> Unit): ITileBatch
}

/**
 * Singleton provider for the TileBatchDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object TileBatchDSLProvider {
    lateinit var instance: TileBatchDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun tileBatch(init: TileBatchBuilder.() -> Unit): ITileBatch = TileBatchDSLProvider.instance.tileBatch(init)

class TileBatchBuilder {
    var id: String = ""
    var layout: String = ""
    var textureMap: String = ""
    private val _terrains: MutableMap<String, String> = mutableMapOf()
    val terrains: Map<String, String> get() = _terrains
    var weight: Int = 1
    var startX: Int = 0
    var startY: Int = 0
    var addToDefault: Boolean = true
    var frameCount: Int = 0
    var frameDist: Int = 0
    fun layoutTerrain(key: String, terrainId: String) {
        _terrains[key] = terrainId
    }
    fun build(): ITileBatch = TileBatchDSLProvider.instance.tileBatch {
        id = this@TileBatchBuilder.id
        layout = this@TileBatchBuilder.layout
        textureMap = this@TileBatchBuilder.textureMap
        _terrains.forEach { (k, v) -> layoutTerrain(k, v) }
        weight = this@TileBatchBuilder.weight
        startX = this@TileBatchBuilder.startX
        startY = this@TileBatchBuilder.startY
        addToDefault = this@TileBatchBuilder.addToDefault
        frameCount = this@TileBatchBuilder.frameCount
        frameDist = this@TileBatchBuilder.frameDist
    }
}
