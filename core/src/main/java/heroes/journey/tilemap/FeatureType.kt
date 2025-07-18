package heroes.journey.tilemap

import heroes.journey.GameState
import heroes.journey.modlib.Ids
import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.utils.Position
import heroes.journey.modlib.worldgen.IFeatureType
import heroes.journey.mods.Registries.FeatureTypeManager
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
