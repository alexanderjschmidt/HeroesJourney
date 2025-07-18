package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.ITerrain
import heroes.journey.modlib.worldgen.TerrainDSL
import heroes.journey.tilemap.wavefunctiontiles.Terrain

class TerrainDSLImpl : TerrainDSL {
    override fun terrain(id: String, terrainCost: Int): ITerrain = Terrain(id, terrainCost)
}
