package heroes.journey.modlib

/**
 * Public interface for a Renderable, used for sprites and animations.
 * Mods should only use this interface, not implementation classes.
 */
interface IRenderable : IRegistrable {
    override fun register(): IRenderable
}

/**
 * Interface for the renderable DSL implementation.
 */
interface RenderableDSL {
    fun stillRenderable(id: String, textureMapId: String, x: Int, y: Int): IRenderable
    fun animationRenderable(
        id: String,
        textureMapId: String,
        frameDuration: Float = 0.5f,
        frames: List<Pair<Int, Int>> = emptyList(),
        rows: List<Triple<Int, Int, Int>> = emptyList(),
        cols: List<Triple<Int, Int, Int>> = emptyList()
    ): IRenderable
}

/**
 * Singleton provider for the RenderableDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object RenderableDSLProvider {
    lateinit var instance: RenderableDSL
}

/**
 * DSL entrypoint for creating a still renderable (single sprite).
 * @param id unique renderable ID
 * @param textureMapId the ID of the texture map
 * @param x x coordinate in the texture map
 * @param y y coordinate in the texture map
 */
fun stillRenderable(id: String, textureMapId: String, x: Int, y: Int): IRenderable =
    RenderableDSLProvider.instance.stillRenderable(id, textureMapId, x, y)

/**
 * DSL entrypoint for creating an animation renderable.
 * @param id unique renderable ID
 * @param textureMapId the ID of the texture map
 * @param frameDuration duration of each frame
 * @param frames list of (x, y) frame coordinates
 * @param rows list of (startX, y, count) for row frames
 * @param cols list of (x, startY, count) for column frames
 */
fun animationRenderable(
    id: String,
    textureMapId: String,
    frameDuration: Float = 0.5f,
    frames: List<Pair<Int, Int>> = emptyList(),
    rows: List<Triple<Int, Int, Int>> = emptyList(),
    cols: List<Triple<Int, Int, Int>> = emptyList()
): IRenderable = RenderableDSLProvider.instance.animationRenderable(
    id, textureMapId, frameDuration, frames, rows, cols
)
