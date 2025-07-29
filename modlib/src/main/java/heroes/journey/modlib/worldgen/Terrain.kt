package heroes.journey.modlib.worldgen

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A Terrain, used for map tile types.
 * This is a simple data container with no complex functions.
 */
class Terrain(
    id: String,
    val terrainCost: Int
) : Registrable(id) {

    override fun register(): Terrain {
        Registries.TerrainManager.register(this)
        return this
    }
}

/**
 * Builder for defining a terrain in a natural DSL style.
 */
class TerrainBuilder {
    var id: String = ""
    var terrainCost: Int = 1
}

/**
 * DSL entrypoint for defining a terrain.
 *
 * Example usage:
 * ```kotlin
 * terrain {
 *     id = Ids.TERRAIN_PLAINS
 *     terrainCost = 1
 * }
 * ```
 */
fun terrain(init: TerrainBuilder.() -> Unit): Terrain {
    val builder = TerrainBuilder()
    builder.init()
    return Terrain(builder.id, builder.terrainCost)
}
