package heroes.journey.tilemap

import heroes.journey.registries.Registries.FeatureTypeManager

class FeatureBuilder {
    var id: String = ""
    var minDist: Int = 2
    var minInRegion: Int = 0
    var maxInRegion: Int = 1
    fun build(): FeatureGenerationData =
        FeatureGenerationData(FeatureTypeManager[id]!!, minDist, minInRegion, maxInRegion)
}

class BiomeBuilder {
    var id: String = ""
    var baseTerrain: String = ""
    private val featuresList = mutableListOf<FeatureGenerationData>()
    fun feature(init: FeatureBuilder.() -> Unit) {
        val builder = FeatureBuilder()
        builder.init()
        featuresList.add(builder.build())
    }

    fun build(): Biome = Biome(id, baseTerrain, featuresList)
}

fun biome(init: BiomeBuilder.() -> Unit): Biome {
    val builder = BiomeBuilder()
    builder.init()
    return builder.build()
}
