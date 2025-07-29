package heroes.journey.modlib.art

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A TextureMap, used for sprite sheets and tilemaps.
 * This is a simple data container with no complex functions.
 */
class TextureMap(
    id: String,
    var location: String,
    val width: Int,
    val height: Int
) : Registrable(id) {

    init {
        location = if (location.startsWith("Textures/")) location else "Textures/$location"
    }

    override fun register(): TextureMap {
        Registries.TextureManager.register(this)
        return this
    }
}

/**
 * Builder for defining a texture map in a natural DSL style.
 */
class TextureMapBuilder {
    var id: String = ""
    var location: String = ""
    var width: Int = 0
    var height: Int = 0
}

/**
 * DSL entrypoint for creating a texture map (sprite sheet or tilemap).
 *
 * Example usage:
 * ```kotlin
 * textureMap {
 *     id = "sprites"
 *     location = "sprites.png"
 *     width = 16
 *     height = 16
 * }
 * ```
 */
fun textureMap(init: TextureMapBuilder.() -> Unit): TextureMap {
    val builder = TextureMapBuilder()
    builder.init()
    return TextureMap(builder.id, builder.location, builder.width, builder.height)
}
