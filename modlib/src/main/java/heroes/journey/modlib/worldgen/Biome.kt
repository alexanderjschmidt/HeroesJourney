package heroes.journey.modlib.worldgen

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Biome, used for world generation.
 * Mods should only use this interface, not implementation classes.
 */
interface IBiome : IRegistrable {

    /** The base terrain ID for this biome. */
    val baseTerrain: String

    /** The list of feature generation data for this biome. */
    val featureGenerationData: List<FeatureGenerationData>
    override fun register(): IBiome
}

/**
 * Public interface for feature generation data, used in biomes.
 * Mods should only use this interface, not implementation classes.
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
 * Interface for the Biome DSL implementation.
 */
interface BiomeDSL {
    fun biome(init: BiomeBuilder.() -> Unit): IBiome
}

/**
 * Interface for the FeatureGenerationData DSL implementation.
 */
interface FeatureGenerationDataDSL {
    fun featureGenerationData(init: FeatureGenerationDataBuilder.() -> Unit): FeatureGenerationData
}

/**
 * Singleton provider for the BiomeDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object BiomeDSLProvider {
    lateinit var instance: BiomeDSL
}

/**
 * Singleton provider for the FeatureGenerationDataDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object FeatureGenerationDataDSLProvider {
    lateinit var instance: FeatureGenerationDataDSL
}

/**
 * DSL entrypoint for creating a biome.
 * @param id unique biome ID
 * @param baseTerrain the ID of the base terrain for this biome
 * @param featureGenerationData list of feature generation data
 */
fun biome(init: BiomeBuilder.() -> Unit): IBiome = BiomeDSLProvider.instance.biome(init)

/**
 * DSL entrypoint for creating feature generation data.
 * @param featureTypeId the feature type ID
 * @param minDist minimum distance between features
 * @param minInRegion minimum number of features in a region
 * @param maxInRegion maximum number of features in a region
 */
fun featureGenerationData(init: FeatureGenerationDataBuilder.() -> Unit): FeatureGenerationData =
    FeatureGenerationDataDSLProvider.instance.featureGenerationData(init)

interface FeatureGenerationDataBuilder {
    var featureTypeId: String
    var minDist: Int
    var minInRegion: Int
    var maxInRegion: Int
}

interface BiomeBuilder {
    var id: String
    var baseTerrain: String
    fun feature(init: FeatureGenerationDataBuilder.() -> Unit)
}
