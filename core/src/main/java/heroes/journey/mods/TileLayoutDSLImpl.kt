package heroes.journey.mods

import heroes.journey.modlib.ITileLayout
import heroes.journey.modlib.TileLayoutDSL
import heroes.journey.tilemap.TileLayout

class TileLayoutDSLImpl : TileLayoutDSL {
    override fun tileLayout(id: String, path: String, terrainRoles: List<String>): ITileLayout =
        TileLayout(id, path, terrainRoles)
} 