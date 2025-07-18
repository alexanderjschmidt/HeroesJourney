package heroes.journey.tilemap.wavefunctiontiles

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.worldgen.ITerrain
import heroes.journey.mods.Registries

class Terrain(id: String, override val terrainCost: Int) : Registrable(id), ITerrain {
    override fun register(): Terrain {
        return Registries.TerrainManager.register(this)
    }
}
