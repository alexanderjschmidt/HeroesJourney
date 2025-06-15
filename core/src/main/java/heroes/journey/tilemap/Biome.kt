package heroes.journey.tilemap

import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.BiomeManager

class Biome(id: String, name: String?, baseTerrain: String, featureGenerationData: List<FeatureGenerationData>) :
    Registrable(id, name) {

    override fun register(): Registrable {
        return BiomeManager.register(this)
    }
}
