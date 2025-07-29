package heroes.journey.utils.art

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import heroes.journey.modlib.art.TextureMap

class AnimationRenderable(
    id: String,
    private val animationBuilder: () -> Animation<TextureRegion>
) : Renderable(id) {

    private var animation: Animation<TextureRegion>? = null

    override fun getRender(deltaTime: Float): TextureRegion {
        if (animation == null) {
            animation = animationBuilder()
        }
        return animation!!.getKeyFrame(deltaTime)
    }
}

class AnimationRenderableBuilder(
    private val id: String,
    private val textureMap: TextureMap
) {
    var frameDuration: Float = 0.5f
    var playMode: Animation.PlayMode = Animation.PlayMode.LOOP
    private val frameCoords = mutableListOf<Pair<Int, Int>>()

    fun frame(x: Int, y: Int) {
        frameCoords.add(x to y)
    }

    fun row(startX: Int, y: Int, count: Int) {
        for (i in 0 until count) {
            frameCoords.add((startX + i) to y)
        }
    }

    fun col(x: Int, startY: Int, count: Int) {
        for (i in 0 until count) {
            frameCoords.add(x to (startY + i))
        }
    }

    fun build(): AnimationRenderable {
        return AnimationRenderable(id) {
            val textureRegions = ResourceManager.get(textureMap)

            val keyFrames: Array<TextureRegion> = if (frameCoords.isEmpty()) {
                textureRegions
                    .flatMap { row -> row.toList() } // flatten 2D array to list
                    .toTypedArray()
            } else {
                frameCoords.map { (x, y) -> textureRegions[y][x] }.toTypedArray()
            }

            Animation(frameDuration, *keyFrames).apply {
                playMode = this@AnimationRenderableBuilder.playMode
            }
        }
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
