package heroes.journey.mods.worldgen

import heroes.journey.modlib.BiomeDSL
import heroes.journey.modlib.FeatureGenerationData
import heroes.journey.modlib.FeatureGenerationDataDSL
import heroes.journey.modlib.IBiome
import heroes.journey.tilemap.Biome

class BiomeDSLImpl : BiomeDSL {
    override fun biome(
        id: String,
        baseTerrain: String,
        featureGenerationData: List<FeatureGenerationData>
    ): IBiome {
        return Biome(id, baseTerrain, featureGenerationData)
    }
}

class FeatureGenerationDataDSLImpl : FeatureGenerationDataDSL {
    override fun featureGenerationData(
        featureTypeId: String,
        minDist: Int,
        minInRegion: Int,
        maxInRegion: Int
    ): FeatureGenerationData {
        return FeatureGenerationData(featureTypeId, minDist, minInRegion, maxInRegion)
    }
}
