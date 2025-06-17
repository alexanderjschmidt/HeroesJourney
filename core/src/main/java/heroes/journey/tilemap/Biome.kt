package heroes.journey.tilemap

import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.BiomeManager

class Biome(
    id: String,
    name: String?,
    val baseTerrain: String,
    val featureGenerationData: List<FeatureGenerationData>
) :
    Registrable(id, name) {

    override fun register(): Biome {
        return BiomeManager.register(this)
    }
}
