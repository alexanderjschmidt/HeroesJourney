package heroes.journey.modlib.worldgen

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A Biome, used for world generation.
 * This is a simple data container with no complex functions.
 */
class Biome(
    id: String,
    val baseTerrain: String,
    val featureGenerationData: List<FeatureGenerationData>
) : Registrable(id) {

    override fun register(): Biome {
        Registries.BiomeManager.register(this)
        return this
    }
}

/**
 * Public interface for feature generation data, used in biomes.
 * This is a simple data container with no complex functions.
 */
class FeatureGenerationData(
    /** The feature type ID to generate. */
    val featureTypeId: String,
    /** Minimum distance between features of this type. */
    val minDist: Int = 3,
    /** Minimum number of features in a region. */
    val minInRegion: Int = 1,
    /** Maximum number of features in a region. */
    val maxInRegion: Int = 1
)

/**
 * Builder for defining a biome in a natural DSL style.
 */
class BiomeBuilder {
    var id: String = ""
    var baseTerrain: String = ""
    private val _features = mutableListOf<FeatureGenerationData>()
    val features: List<FeatureGenerationData> get() = _features

    fun feature(init: FeatureGenerationDataBuilder.() -> Unit) {
        val builder = FeatureGenerationDataBuilder().apply(init)
        _features.add(
            FeatureGenerationData(
                builder.featureTypeId,
                builder.minDist,
                builder.minInRegion,
                builder.maxInRegion
            )
        )
    }
}

/**
 * Builder for defining feature generation data in a natural DSL style.
 */
class FeatureGenerationDataBuilder {
    var featureTypeId: String = ""
    var minDist: Int = 2
    var minInRegion: Int = 0
    var maxInRegion: Int = 1
}

/**
 * DSL entrypoint for creating a biome.
 *
 * Example usage:
 * ```kotlin
 * biome {
 *     id = Ids.BIOME_KINGDOM
 *     baseTerrain = Ids.TERRAIN_PLAINS
 *     feature {
 *         featureTypeId = Ids.KINGDOM
 *         minDist = 0
 *         minInRegion = 1
 *         maxInRegion = 1
 *     }
 * }
 * ```
 */
fun biome(init: BiomeBuilder.() -> Unit): Biome {
    val builder = BiomeBuilder()
    builder.init()
    return Biome(builder.id, builder.baseTerrain, builder.features)
}

/**
 * DSL entrypoint for creating feature generation data.
 *
 * Example usage:
 * ```kotlin
 * featureGenerationData {
 *     featureTypeId = Ids.KINGDOM
 *     minDist = 0
 *     minInRegion = 1
 *     maxInRegion = 1
 * }
 * ```
 */
fun featureGenerationData(init: FeatureGenerationDataBuilder.() -> Unit): FeatureGenerationData {
    val builder = FeatureGenerationDataBuilder()
    builder.init()
    return FeatureGenerationData(
        builder.featureTypeId,
        builder.minDist,
        builder.minInRegion,
        builder.maxInRegion
    )
}
