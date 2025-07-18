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
    fun tileBatch(
        id: String,
        layout: String,
        textureMap: String,
        terrains: Map<String, String>,
        weight: Int = 1,
        startX: Int = 0,
        startY: Int = 0,
        addToDefault: Boolean = true,
        frameCount: Int = 0,
        frameDist: Int = 0
    ): ITileBatch
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
fun tileBatch(
    id: String,
    layout: String,
    textureMap: String,
    terrains: Map<String, String>,
    weight: Int = 1,
    startX: Int = 0,
    startY: Int = 0,
    addToDefault: Boolean = true,
    frameCount: Int = 0,
    frameDist: Int = 0
): ITileBatch = TileBatchDSLProvider.instance.tileBatch(
    id, layout, textureMap, terrains, weight, startX, startY, addToDefault, frameCount, frameDist
)
