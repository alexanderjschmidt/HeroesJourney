package heroes.journey.modlib.worldgen

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Terrain, used for map tile types.
 * Mods should only use this interface, not implementation classes.
 */
interface ITerrain : IRegistrable {
    /**
     * The movement cost for this terrain.
     */
    val terrainCost: Int

    /**
     * Register this terrain with the game.
     * @return the registered terrain
     */
    override fun register(): ITerrain
}

/**
 * Interface for the terrain DSL implementation.
 */
interface TerrainDSL {
    fun terrain(init: TerrainBuilder.() -> Unit): ITerrain
}

/**
 * Singleton provider for the TerrainDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object TerrainDSLProvider {
    lateinit var instance: TerrainDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun terrain(init: TerrainBuilder.() -> Unit): ITerrain = TerrainDSLProvider.instance.terrain(init)

class TerrainBuilder {
    var id: String = ""
    var terrainCost: Int = 1
}
