package heroes.journey.mods

import heroes.journey.modlib.*
import heroes.journey.modlib.actions.ActionDSLProvider
import heroes.journey.mods.art.RenderableDSLImpl
import heroes.journey.mods.art.TextureMapDSLImpl
import heroes.journey.mods.items.ItemDSLImpl
import heroes.journey.mods.items.ItemSubTypeDSLImpl
import heroes.journey.mods.worldgen.*

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
    ChallengeDSLProvider.instance = ChallengeDSLImpl()
    FeatureTypeDSLProvider.instance = FeatureTypeDSLImpl()
    BiomeDSLProvider.instance = BiomeDSLImpl()
    FeatureGenerationDataDSLProvider.instance = FeatureGenerationDataDSLImpl()
    ActionDSLProvider.instance = ActionDSLImpl()
}
