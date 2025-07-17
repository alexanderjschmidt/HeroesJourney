package heroes.journey.tilemap

import heroes.journey.GameState
import heroes.journey.modlib.IFeatureType
import heroes.journey.modlib.Ids
import heroes.journey.modlib.Position
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.FeatureTypeManager
import heroes.journey.registries.TileManager
import lombok.Getter
import java.util.*

@Getter
class FeatureType(id: String, override val renderId: String) : Registrable(id), IFeatureType {

    override fun register(): FeatureType {
        return FeatureTypeManager.register(this)
    }

    fun generateFeature(gs: GameState, pos: Position): UUID {
        gs.map.setEnvironment(pos.x, pos.y, TileManager.BASE_TILES[Ids.BASE_TILE_NULL])
        return gs.world.entityFactory.generateBasicLocation(getName(), pos.x, pos.y, renderId)
    }
}
