package heroes.journey.tilemap

class FeatureGenerationData(
    val featureType: FeatureType,
    val minDist: Int = 2,
    val minInRegion: Int = 0,
    val maxInRegion: Int = 1
)
