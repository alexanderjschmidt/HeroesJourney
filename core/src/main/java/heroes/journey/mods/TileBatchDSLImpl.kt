package heroes.journey.mods

import heroes.journey.modlib.ITileBatch
import heroes.journey.modlib.TileBatchDSL
import heroes.journey.tilemap.TileBatch

class TileBatchDSLImpl : TileBatchDSL {
    override fun tileBatch(
        id: String,
        layout: String,
        textureMap: String,
        terrains: Map<String, String>,
        weight: Int,
        startX: Int,
        startY: Int,
        addToDefault: Boolean,
        frameCount: Int,
        frameDist: Int
    ): ITileBatch = TileBatch(
        id,
        layout,
        textureMap,
        terrains,
        weight,
        startX,
        startY,
        addToDefault,
        frameCount > 0,
        frameCount,
        frameDist
    )
} 