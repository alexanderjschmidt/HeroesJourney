package heroes.journey.modlib

/**
 * Public interface for a TextureMap, used for sprite sheets and tilemaps.
 * Mods should only use this interface, not implementation classes.
 */
interface ITextureMap {
    /** The unique ID of the texture map. */
    val id: String
    /** The asset path for the texture map. */
    val location: String
    /** The width of each tile/sprite in pixels. */
    val width: Int
    /** The height of each tile/sprite in pixels. */
    val height: Int
    /**
     * Register this texture map with the game.
     * @return the registered texture map
     */
    fun register(): ITextureMap
}

/**
 * Interface for the TextureMap DSL implementation.
 */
interface TextureMapDSL {
    fun textureMap(id: String, location: String, width: Int, height: Int): ITextureMap
}

/**
 * Singleton provider for the TextureMapDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object TextureMapDSLProvider {
    lateinit var instance: TextureMapDSL
}

/**
 * DSL entrypoint for creating a texture map (sprite sheet or tilemap).
 * @param id unique texture map ID
 * @param location asset path (relative to assets directory)
 * @param width width of each tile/sprite in pixels
 * @param height height of each tile/sprite in pixels
 */
fun textureMap(id: String, location: String, width: Int, height: Int): ITextureMap =
    TextureMapDSLProvider.instance.textureMap(id, location, width, height) 