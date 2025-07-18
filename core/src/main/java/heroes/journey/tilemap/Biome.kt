package heroes.journey.tilemap

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.worldgen.FeatureGenerationData
import heroes.journey.modlib.worldgen.IBiome
import heroes.journey.mods.Registries.BiomeManager

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
