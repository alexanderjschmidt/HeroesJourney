package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.ITerrain
import heroes.journey.modlib.worldgen.TerrainDSL
import heroes.journey.modlib.worldgen.TerrainBuilder
import heroes.journey.tilemap.wavefunctiontiles.Terrain

class TerrainDSLImpl : TerrainDSL {
    override fun terrain(init: TerrainBuilder.() -> Unit): ITerrain {
        val builder = TerrainBuilder().apply(init)
        return Terrain(builder.id, builder.terrainCost)
    }
}
