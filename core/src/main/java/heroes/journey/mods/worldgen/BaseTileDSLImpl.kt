package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.*
import heroes.journey.tilemap.wavefunctiontiles.BaseTile as CoreBaseTile
import heroes.journey.modlib.worldgen.Terrain
import heroes.journey.tilemap.TileManager

class BaseTileBuilderImpl : BaseTileBuilder {
    override var id: String = ""
    override var terrain: String = ""
    override var textureMap: String = ""
    override var x: Int = 0
    override var y: Int = 0
    override var weight: Int = 1
    override var addToBaseTiles: Boolean = true
    override var frameCount: Int = 0
    override var frameDist: Int = 0
    override var frameRate: Float = 0.2f
}

class BaseTileDSLImpl : BaseTileDSL {
    override fun baseTile(init: BaseTileBuilder.() -> Unit) {
        val builder = BaseTileBuilderImpl().apply(init)
        val def = heroes.journey.tilemap.BaseTileDef(
            builder.id,
            builder.terrain,
            builder.textureMap,
            builder.x,
            builder.y,
            builder.weight,
            builder.addToBaseTiles,
            builder.frameCount,
            builder.frameDist,
            builder.frameRate
        )
        TileManager.addBaseTileDef(def)
    }
} 