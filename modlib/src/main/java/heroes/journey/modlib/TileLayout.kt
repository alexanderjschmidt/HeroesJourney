package heroes.journey.modlib

/**
 * Public interface for a TileLayout, used for tile arrangement and terrain roles.
 * Mods should only use this interface, not implementation classes.
 */
interface ITileLayout : IRegistrable {

    /** The path for the tile layout. */
    val path: String

    /** The terrain roles for this layout. */
    val terrainRoles: List<String>
    override fun register(): ITileLayout
}

/**
 * Interface for the tile layout DSL implementation.
 */
interface TileLayoutDSL {
    fun tileLayout(id: String, path: String, terrainRoles: List<String>): ITileLayout
}

/**
 * Singleton provider for the TileLayoutDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object TileLayoutDSLProvider {
    lateinit var instance: TileLayoutDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun tileLayout(id: String, path: String, terrainRoles: List<String>): ITileLayout =
    TileLayoutDSLProvider.instance.tileLayout(id, path, terrainRoles)
