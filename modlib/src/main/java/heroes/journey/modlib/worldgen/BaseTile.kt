package heroes.journey.modlib.worldgen

/**
 * Builder for defining a base tile in a natural DSL style.
 */
interface BaseTileBuilder {
    var id: String
    var terrain: String
    var textureMap: String
    var x: Int
    var y: Int
    var weight: Int
    var addToBaseTiles: Boolean
    var frameCount: Int
    var frameDist: Int
    var frameRate: Float
}

/**
 * Interface for the base tile DSL implementation.
 */
interface BaseTileDSL {
    fun baseTile(init: BaseTileBuilder.() -> Unit)
}

/**
 * Singleton provider for the BaseTileDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object BaseTileDSLProvider {
    lateinit var instance: BaseTileDSL
}

/**
 * DSL entrypoint for defining a new base tile using a builder lambda.
 */
fun baseTile(init: BaseTileBuilder.() -> Unit) = BaseTileDSLProvider.instance.baseTile(init) 