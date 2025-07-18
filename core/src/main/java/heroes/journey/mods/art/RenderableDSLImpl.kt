package heroes.journey.mods.art

import heroes.journey.modlib.art.IRenderable
import heroes.journey.modlib.art.RenderableDSL
import heroes.journey.mods.Registries
import heroes.journey.utils.art.AnimationRenderable
import heroes.journey.utils.art.ResourceManager
import heroes.journey.utils.art.StillRenderable
import heroes.journey.modlib.art.StillRenderableBuilder
import heroes.journey.modlib.art.AnimationRenderableBuilder

class RenderableDSLImpl : RenderableDSL {
    override fun stillRenderable(init: StillRenderableBuilder.() -> Unit): IRenderable {
        val builder = StillRenderableBuilder().apply(init)
        val textureMap = Registries.TextureManager.get(builder.textureMapId)
            ?: throw IllegalArgumentException("TextureMap with id '${builder.textureMapId}' not found")
        return StillRenderable(builder.id, textureMap, builder.x, builder.y)
    }

    override fun animationRenderable(init: AnimationRenderableBuilder.() -> Unit): IRenderable {
        val builder = AnimationRenderableBuilder().apply(init)
        val textureMap = Registries.TextureManager.get(builder.textureMapId)
            ?: throw IllegalArgumentException("TextureMap with id '${builder.textureMapId}' not found")
        val frameCoords = mutableListOf<Pair<Int, Int>>()
        frameCoords.addAll(builder.frames)
        builder.rows.forEach { (startX, y, count) ->
            for (i in 0 until count) frameCoords.add((startX + i) to y)
        }
        builder.cols.forEach { (x, startY, count) ->
            for (i in 0 until count) frameCoords.add(x to (startY + i))
        }
        return AnimationRenderable(builder.id) {
            val textureRegions = heroes.journey.utils.art.ResourceManager.get(textureMap)
            val keyFrames: Array<com.badlogic.gdx.graphics.g2d.TextureRegion> =
                if (frameCoords.isEmpty()) {
                    textureRegions.flatMap { row -> row.toList() }.toTypedArray()
                } else {
                    frameCoords.map { (x, y) -> textureRegions[y][x] }.toTypedArray()
                }
            com.badlogic.gdx.graphics.g2d.Animation(builder.frameDuration, *keyFrames).apply {
                playMode = com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP
            }
        }
    }
}
