package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.*
import heroes.journey.tilemap.Biome

class BiomeBuilderImpl : BiomeBuilder {
    override var id: String = ""
    override var baseTerrain: String = ""
    private val _features = mutableListOf<FeatureGenerationData>()
    val features: List<FeatureGenerationData> get() = _features
    override fun feature(init: FeatureGenerationDataBuilder.() -> Unit) {
        val builder = FeatureGenerationDataBuilderImpl().apply(init)
        _features.add(FeatureGenerationData(
            builder.featureTypeId,
            builder.minDist,
            builder.minInRegion,
            builder.maxInRegion
        ))
    }
}

class FeatureGenerationDataBuilderImpl : FeatureGenerationDataBuilder {
    override var featureTypeId: String = ""
    override var minDist: Int = 2
    override var minInRegion: Int = 0
    override var maxInRegion: Int = 1
}

class BiomeDSLImpl : BiomeDSL {
    override fun biome(init: BiomeBuilder.() -> Unit): IBiome {
        val builder = BiomeBuilderImpl().apply(init)
        return Biome(builder.id, builder.baseTerrain, builder.features)
    }
}

class FeatureGenerationDataDSLImpl : FeatureGenerationDataDSL {
    override fun featureGenerationData(init: FeatureGenerationDataBuilder.() -> Unit): FeatureGenerationData {
        val builder = FeatureGenerationDataBuilderImpl().apply(init)
        return FeatureGenerationData(
            builder.featureTypeId,
            builder.minDist,
            builder.minInRegion,
            builder.maxInRegion
        )
    }
}
