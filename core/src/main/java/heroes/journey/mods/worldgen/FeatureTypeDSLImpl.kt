package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.FeatureTypeDSL
import heroes.journey.modlib.worldgen.IFeatureType
import heroes.journey.tilemap.FeatureType
import heroes.journey.modlib.worldgen.FeatureTypeBuilder

class FeatureTypeDSLImpl : FeatureTypeDSL {
    override fun featureType(init: FeatureTypeBuilder.() -> Unit): IFeatureType {
        val builder = FeatureTypeBuilder().apply(init)
        return FeatureType(builder.id, builder.renderId)
    }
}
