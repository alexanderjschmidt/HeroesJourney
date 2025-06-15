package heroes.journey.utils.worldgen

import heroes.journey.GameState
import heroes.journey.entities.Position
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.FeatureTypeManager
import heroes.journey.tilemap.Feature
import lombok.Getter

@Getter
abstract class FeatureType(id: String, name: String?) : Registrable(id, name) {

    override fun register(): FeatureType {
        return FeatureTypeManager.register(this)
    }

    abstract fun generateFeature(gs: GameState, pos: Position, vararg connections: Feature): Feature
}
