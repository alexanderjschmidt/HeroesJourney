package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.ITileLayout
import heroes.journey.modlib.worldgen.TileLayoutDSL
import heroes.journey.modlib.worldgen.TileLayoutBuilder
import heroes.journey.tilemap.TileLayout

class TileLayoutDSLImpl : TileLayoutDSL {
    override fun tileLayout(init: TileLayoutBuilder.() -> Unit): ITileLayout {
        val builder = TileLayoutBuilder().apply(init)
        return TileLayout(builder.id, builder.path, builder.terrainRoles)
    }
}
