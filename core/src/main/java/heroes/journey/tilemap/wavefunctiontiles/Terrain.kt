package heroes.journey.tilemap.wavefunctiontiles

import heroes.journey.modlib.ITerrain
import heroes.journey.modlib.Registrable
import heroes.journey.registries.Registries

class Terrain(id: String, override val terrainCost: Int) : Registrable(id), ITerrain {
    override fun register(): Terrain {
        return Registries.TerrainManager.register(this)
    }
}
