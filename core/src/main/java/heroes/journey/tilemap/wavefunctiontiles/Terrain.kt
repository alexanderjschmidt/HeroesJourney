package heroes.journey.tilemap.wavefunctiontiles

import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries

class Terrain(id: String, @JvmField val terrainCost: Int) : Registrable(id) {
    override fun register(): Terrain {
        return Registries.TerrainManager.register(this)
    }
}
