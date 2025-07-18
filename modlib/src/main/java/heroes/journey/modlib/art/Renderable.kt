package heroes.journey.modlib.art

import heroes.journey.modlib.registries.IRegistrable

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
    fun stillRenderable(init: StillRenderableBuilder.() -> Unit): IRenderable
    fun animationRenderable(init: AnimationRenderableBuilder.() -> Unit): IRenderable
}

/**
 * Singleton provider for the RenderableDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object RenderableDSLProvider {
    lateinit var instance: RenderableDSL
}

class StillRenderableBuilder {
    var id: String = ""
    var textureMapId: String = ""
    var x: Int = 0
    var y: Int = 0
}

fun stillRenderable(init: StillRenderableBuilder.() -> Unit): IRenderable = RenderableDSLProvider.instance.stillRenderable(init)

class AnimationRenderableBuilder {
    var id: String = ""
    var textureMapId: String = ""
    var frameDuration: Float = 0.5f
    var frames: List<Pair<Int, Int>> = emptyList()
    var rows: List<Triple<Int, Int, Int>> = emptyList()
    var cols: List<Triple<Int, Int, Int>> = emptyList()
}

fun animationRenderable(init: AnimationRenderableBuilder.() -> Unit): IRenderable = RenderableDSLProvider.instance.animationRenderable(init)
