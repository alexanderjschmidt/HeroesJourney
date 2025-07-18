package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.BiomeDSL
import heroes.journey.modlib.worldgen.FeatureGenerationData
import heroes.journey.modlib.worldgen.FeatureGenerationDataDSL
import heroes.journey.modlib.worldgen.IBiome
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
