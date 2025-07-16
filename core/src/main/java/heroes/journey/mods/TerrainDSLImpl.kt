package heroes.journey.mods

import heroes.journey.modlib.ITerrain
import heroes.journey.modlib.TerrainDSL
import heroes.journey.tilemap.wavefunctiontiles.Terrain

class TerrainDSLImpl : TerrainDSL {
    override fun terrain(id: String, terrainCost: Int): ITerrain = Terrain(id, terrainCost)
} 