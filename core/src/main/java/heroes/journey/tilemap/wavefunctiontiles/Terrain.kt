package heroes.journey.tilemap.wavefunctiontiles

import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries

class Terrain(id: String, name: String?, @JvmField val terrainCost: Int) : Registrable(id, name) {
    override fun register(): Terrain {
        return Registries.TerrainManager.register(this)
    }
}
