package heroes.journey.mods

import heroes.journey.modlib.IRenderable
import heroes.journey.modlib.RenderableDSL
import heroes.journey.utils.art.ResourceManager
import heroes.journey.utils.art.StillRenderable
import heroes.journey.utils.art.AnimationRenderable
import heroes.journey.utils.art.TextureMap

class RenderableDSLImpl : RenderableDSL {
    override fun stillRenderable(id: String, textureMapId: String, x: Int, y: Int): IRenderable {
        val textureMap = ResourceManager.TextureManager.get(textureMapId)
            ?: throw IllegalArgumentException("TextureMap with id '$textureMapId' not found")
        return StillRenderable(id, textureMap, x, y)
    }

    override fun animationRenderable(
        id: String,
        textureMapId: String,
        frameDuration: Float,
        frames: List<Pair<Int, Int>>,
        rows: List<Triple<Int, Int, Int>>,
        cols: List<Triple<Int, Int, Int>>
    ): IRenderable {
        val textureMap = ResourceManager.TextureManager.get(textureMapId)
            ?: throw IllegalArgumentException("TextureMap with id '$textureMapId' not found")
        val builder = object {
            val frameCoords = mutableListOf<Pair<Int, Int>>()
            init {
                frameCoords.addAll(frames)
                rows.forEach { (startX, y, count) ->
                    for (i in 0 until count) frameCoords.add((startX + i) to y)
                }
                cols.forEach { (x, startY, count) ->
                    for (i in 0 until count) frameCoords.add(x to (startY + i))
                }
            }
        }
        return AnimationRenderable(id) {
            val textureRegions = ResourceManager.get(textureMap)
            val keyFrames: Array<com.badlogic.gdx.graphics.g2d.TextureRegion> =
                if (builder.frameCoords.isEmpty()) {
                    textureRegions.flatMap { row -> row.toList() }.toTypedArray()
                } else {
                    builder.frameCoords.map { (x, y) -> textureRegions[y][x] }.toTypedArray()
                }
            com.badlogic.gdx.graphics.g2d.Animation(frameDuration, *keyFrames).apply {
                playMode = com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP
            }
        }
    }
} 