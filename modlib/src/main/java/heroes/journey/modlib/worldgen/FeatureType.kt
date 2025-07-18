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
    fun featureType(init: FeatureTypeBuilder.() -> Unit): IFeatureType
}

/**
 * Singleton provider for the FeatureTypeDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object FeatureTypeDSLProvider {
    lateinit var instance: FeatureTypeDSL
}

class FeatureTypeBuilder {
    var id: String = ""
    var renderId: String = ""
}

fun featureType(init: FeatureTypeBuilder.() -> Unit): IFeatureType = FeatureTypeDSLProvider.instance.featureType(init)
