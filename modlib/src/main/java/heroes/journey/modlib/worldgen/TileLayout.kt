package heroes.journey.modlib.worldgen

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A TileLayout, used for tile arrangement and terrain roles.
 * This is a simple data container with no complex functions.
 */
class TileLayout(
    id: String,
    val path: String,
    val terrainRoles: List<String>
) : Registrable(id) {

    override fun register(): TileLayout {
        Registries.TileLayoutManager.register(this)
        return this
    }
}

/**
 * Builder for defining a tile layout in a natural DSL style.
 */
class TileLayoutBuilder {
    var id: String = ""
    var path: String = ""
    var terrainRoles: List<String> = emptyList()
}

/**
 * DSL entrypoint for creating a tile layout.
 *
 * Example usage:
 * ```kotlin
 * tileLayout {
 *     id = "path_edge"
 *     path = "Textures/path_edge.png"
 *     terrainRoles = listOf("path", "grass")
 * }
 * ```
 */
fun tileLayout(init: TileLayoutBuilder.() -> Unit): TileLayout {
    val builder = TileLayoutBuilder()
    builder.init()
    return TileLayout(builder.id, builder.path, builder.terrainRoles)
}
