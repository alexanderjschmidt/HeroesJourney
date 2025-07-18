package heroes.journey.modlib.worldgen

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a FeatureType, used for world features like towns, dungeons, etc.
 * Mods should only use this interface, not implementation classes.
 */
interface IFeatureType : IRegistrable {

    /** The renderable ID for this feature type. */
    val renderId: String
    override fun register(): IFeatureType
}

/**
 * Interface for the FeatureType DSL implementation.
 */
interface FeatureTypeDSL {
    fun featureType(id: String, renderId: String): IFeatureType
}

/**
 * Singleton provider for the FeatureTypeDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object FeatureTypeDSLProvider {
    lateinit var instance: FeatureTypeDSL
}

/**
 * DSL entrypoint for creating a feature type.
 * @param id unique feature type ID
 * @param renderId the ID of the renderable for this feature type
 */
fun featureType(id: String, renderId: String): IFeatureType =
    FeatureTypeDSLProvider.instance.featureType(id, renderId)

class FeatureTypeBuilder {
    var id: String = ""
    var renderId: String = ""
    fun build(): IFeatureType = featureType(id, renderId)
}

fun featureType(builder: FeatureTypeBuilder.() -> Unit): IFeatureType {
    val b = FeatureTypeBuilder().apply(builder)
    return b.build()
}
