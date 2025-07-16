package heroes.journey.tilemap

import heroes.journey.modlib.FeatureGenerationData
import heroes.journey.modlib.IBiome
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.BiomeManager

class Biome(
    id: String,
    override val baseTerrain: String,
    override val featureGenerationData: List<FeatureGenerationData>
) :
    Registrable(id), IBiome {

    override fun register(): Biome {
        return BiomeManager.register(this)
    }
}
