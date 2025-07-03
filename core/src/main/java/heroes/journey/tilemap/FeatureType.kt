package heroes.journey.tilemap

import heroes.journey.GameState
import heroes.journey.entities.Position
import heroes.journey.initializers.base.Tiles
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.FeatureTypeManager
import lombok.Getter
import java.util.*

@Getter
abstract class FeatureType(id: String, name: String?) : Registrable(id, name) {

    override fun register(): FeatureType {
        return FeatureTypeManager.register(this)
    }

    fun generateFeature(gs: GameState, pos: Position): UUID {
        gs.map.setEnvironment(pos.x, pos.y, Tiles.NULL)
        return generateFeatureInner(gs, pos)
    }

    abstract fun generateFeatureInner(gs: GameState, pos: Position): UUID
}
