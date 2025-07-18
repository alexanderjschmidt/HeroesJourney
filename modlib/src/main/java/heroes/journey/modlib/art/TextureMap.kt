package heroes.journey.modlib.art

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a TextureMap, used for sprite sheets and tilemaps.
 * Mods should only use this interface, not implementation classes.
 */
interface ITextureMap : IRegistrable {
    /** The asset path for the texture map. */
    val location: String

    /** The width of each tile/sprite in pixels. */
    val width: Int

    /** The height of each tile/sprite in pixels. */
    val height: Int
    override fun register(): ITextureMap
}

/**
 * Interface for the TextureMap DSL implementation.
 */
interface TextureMapDSL {
    fun textureMap(init: TextureMapBuilder.() -> Unit): ITextureMap
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
fun textureMap(init: TextureMapBuilder.() -> Unit): ITextureMap = TextureMapDSLProvider.instance.textureMap(init)

class TextureMapBuilder {
    var id: String = ""
    var location: String = ""
    var width: Int = 0
    var height: Int = 0
}
