package heroes.journey.utils.art

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class AnimationRenderable : Renderable {

    private val animation: Animation<TextureRegion>

    constructor(id: String, animation: Animation<TextureRegion>) : super(id) {
        this.animation = animation
    }

    constructor(
        id: String,
        textureMap: TextureMap,
        x: Int,
        y: Int,
        frames: Int,
        frameDuration: Float = 0.5f,
        horizontal: Boolean = true
    ) : super(id) {
        val textureRegions: Array<Array<TextureRegion>> = ResourceManager.get(textureMap)
        val keyFrames: Array<TextureRegion> = Array(frames) { i ->
            if (horizontal)
                textureRegions[y][x + i]
            else
                textureRegions[y + i][x]
        }
        this.animation = Animation(frameDuration, *keyFrames)
    }

    override fun getRender(delta: Float): TextureRegion {
        return animation.getKeyFrame(delta)
    }
}

class AnimationRenderableBuilder(
    private val id: String,
    private val textureMap: TextureMap
) {
    var frameDuration: Float = 0.5f
    private val frames = mutableListOf<TextureRegion>()

    fun frame(x: Int, y: Int) {
        val textureRegions: Array<Array<TextureRegion>> = ResourceManager.get(textureMap)
        frames.add(textureRegions[y][x]) // row-major [y][x]
    }

    fun row(startX: Int, y: Int, count: Int) {
        val textureRegions = ResourceManager.get(textureMap)
        for (i in 0 until count) {
            frames.add(textureRegions[y][startX + i])
        }
    }

    fun col(x: Int, startY: Int, count: Int) {
        val textureRegions = ResourceManager.get(textureMap)
        for (i in 0 until count) {
            frames.add(textureRegions[startY + i][x])
        }
    }

    fun build(): AnimationRenderable {
        val keyFrames = frames.toTypedArray()
        val animation = Animation(frameDuration, *keyFrames)
        return AnimationRenderable(id, animation)
    }
}

fun animationRenderable(
    id: String,
    textureMap: TextureMap,
    init: AnimationRenderableBuilder.() -> Unit
): AnimationRenderable {
    val builder = AnimationRenderableBuilder(id, textureMap)
    builder.init()
    return builder.build()
}
