package heroes.journey.mods.worldgen

import heroes.journey.modlib.worldgen.ITileLayout
import heroes.journey.modlib.worldgen.TileLayoutDSL
import heroes.journey.tilemap.TileLayout

class TileLayoutDSLImpl : TileLayoutDSL {
    override fun tileLayout(id: String, path: String, terrainRoles: List<String>): ITileLayout =
        TileLayout(id, path, terrainRoles)
}
