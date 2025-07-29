package heroes.journey.modlib.worldgen

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A TileBatch, used for tile generation and batching.
 * This is a simple data container with no complex functions.
 */
class TileBatch(
    id: String,
    val layout: String,
    val textureMap: String,
    val terrains: Map<String, String>,
    val weight: Int,
    val startX: Int,
    val startY: Int,
    val addToDefault: Boolean,
    val animated: Boolean,
    val frameCount: Int,
    val frameDist: Int
) : Registrable(id) {

    override fun register(): TileBatch {
        Registries.TileBatchManager.register(this)
        return this
    }
}

/**
 * Builder for defining a tile batch in a natural DSL style.
 */
class TileBatchBuilder {
    var id: String = ""
    var layout: String = ""
    var textureMap: String = ""
    private val _terrains: MutableMap<String, String> = mutableMapOf()
    var weight: Int = 1
    var startX: Int = 0
    var startY: Int = 0
    var addToDefault: Boolean = true
    var animated: Boolean = false
    var frameCount: Int = 0
    var frameDist: Int = 0

    /**
     * Add a terrain mapping for a specific layout key.
     */
    fun layoutTerrain(key: String, terrainId: String) {
        _terrains[key] = terrainId
    }

    /**
     * Get the terrains map for the TileBatch constructor.
     */
    fun getTerrains(): Map<String, String> = _terrains
}

/**
 * DSL entrypoint for creating a tile batch.
 *
 * Example usage:
 * ```kotlin
 * tileBatch {
 *     id = "path_edge_batch"
 *     layout = "path_edge"
 *     textureMap = "overworld_tileset"
 *     terrains = mapOf("path" to "plains", "grass" to "grass")
 *     weight = 1
 *     startX = 0
 *     startY = 0
 *     addToDefault = true
 *     animated = false
 *     frameCount = 0
 *     frameDist = 0
 * }
 * ```
 */
fun tileBatch(init: TileBatchBuilder.() -> Unit): TileBatch {
    val builder = TileBatchBuilder()
    builder.init()
    return TileBatch(
        builder.id,
        builder.layout,
        builder.textureMap,
        builder.getTerrains(),
        builder.weight,
        builder.startX,
        builder.startY,
        builder.addToDefault,
        builder.animated,
        builder.frameCount,
        builder.frameDist
    )
}
