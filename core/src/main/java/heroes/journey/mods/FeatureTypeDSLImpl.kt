package heroes.journey.mods

import heroes.journey.modlib.FeatureTypeDSL
import heroes.journey.modlib.IFeatureType
import heroes.journey.tilemap.FeatureType

class FeatureTypeDSLImpl : FeatureTypeDSL {
    override fun featureType(id: String, renderId: String): IFeatureType {
        return FeatureType(id, renderId)
    }
}
