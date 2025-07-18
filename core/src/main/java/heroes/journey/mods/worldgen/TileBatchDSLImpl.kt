package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.ITileBatch
import heroes.journey.modlib.worldgen.TileBatchDSL
import heroes.journey.modlib.worldgen.TileBatchBuilder
import heroes.journey.tilemap.TileBatch

class TileBatchDSLImpl : TileBatchDSL {
    override fun tileBatch(init: TileBatchBuilder.() -> Unit): ITileBatch {
        val builder = TileBatchBuilder().apply(init)
        return TileBatch(
            builder.id,
            builder.layout,
            builder.textureMap,
            builder.terrains,
            builder.weight,
            builder.startX,
            builder.startY,
            builder.addToDefault,
            builder.frameCount > 0,
            builder.frameCount,
            builder.frameDist
        )
    }
}
