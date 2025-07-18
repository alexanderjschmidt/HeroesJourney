package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.FeatureTypeDSL
import heroes.journey.modlib.worldgen.IFeatureType
import heroes.journey.tilemap.FeatureType

class FeatureTypeDSLImpl : FeatureTypeDSL {
    override fun featureType(id: String, renderId: String): IFeatureType {
        return FeatureType(id, renderId)
    }
}
