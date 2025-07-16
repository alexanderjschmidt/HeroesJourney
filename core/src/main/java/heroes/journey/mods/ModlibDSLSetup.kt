package heroes.journey.mods

import heroes.journey.modlib.GroupDSLProvider
import heroes.journey.modlib.RenderableDSLProvider
import heroes.journey.modlib.TerrainDSLProvider
import heroes.journey.modlib.TextureMapDSLProvider
import heroes.journey.modlib.TileLayoutDSLProvider

/**
 * Call this at game startup before loading mods to register all modlib DSL providers.
 * Add additional DSL registrations here as new DSLs are migrated.
 */
fun setupModlibDSLs() {
    GroupDSLProvider.instance = GroupDSLImpl()
    RenderableDSLProvider.instance = RenderableDSLImpl()
    TextureMapDSLProvider.instance = TextureMapDSLImpl()
    TerrainDSLProvider.instance = TerrainDSLImpl()
    TileLayoutDSLProvider.instance = TileLayoutDSLImpl()
    // Register other DSLs here as needed
}
