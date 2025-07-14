package heroes.journey.tilemap

import heroes.journey.GameState
import heroes.journey.entities.Position
import heroes.journey.modlib.Ids
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.FeatureTypeManager
import heroes.journey.registries.TileManager
import lombok.Getter
import java.util.*

@Getter
abstract class FeatureType(id: String) : Registrable(id) {

    override fun register(): FeatureType {
        return FeatureTypeManager.register(this)
    }

    fun generateFeature(gs: GameState, pos: Position): UUID {
        gs.map.setEnvironment(pos.x, pos.y, TileManager.BASE_TILES[Ids.BASE_TILE_NULL])
        return generateFeatureInner(gs, pos)
    }

    abstract fun generateFeatureInner(gs: GameState, pos: Position): UUID
}
