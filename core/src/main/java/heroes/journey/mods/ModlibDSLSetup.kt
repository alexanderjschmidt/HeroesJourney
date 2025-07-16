package heroes.journey.mods

import heroes.journey.modlib.GroupDSLProvider
import heroes.journey.modlib.RenderableDSLProvider
import heroes.journey.modlib.TerrainDSLProvider
import heroes.journey.modlib.TextureMapDSLProvider
import heroes.journey.modlib.TileLayoutDSLProvider
import heroes.journey.modlib.TileBatchDSLProvider
import heroes.journey.modlib.StatDSLProvider
import heroes.journey.modlib.ItemSubTypeDSLProvider
import heroes.journey.modlib.ItemDSLProvider
import heroes.journey.modlib.AttributesDSLProvider
import heroes.journey.modlib.BuffDSLProvider
import heroes.journey.modlib.QuestDSLProvider
import heroes.journey.mods.AttributesDSLImpl
import heroes.journey.mods.BuffDSLImpl
import heroes.journey.mods.QuestDSLImpl

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
    TileBatchDSLProvider.instance = TileBatchDSLImpl()
    StatDSLProvider.instance = StatDSLImpl()
    ItemSubTypeDSLProvider.instance = ItemSubTypeDSLImpl()
    ItemDSLProvider.instance = ItemDSLImpl()
    AttributesDSLProvider.instance = AttributesDSLImpl()
    BuffDSLProvider.instance = BuffDSLImpl()
    QuestDSLProvider.instance = QuestDSLImpl()
    // Register other DSLs here as needed
}
