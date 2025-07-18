package heroes.journey.mods

import heroes.journey.modlib.*
import heroes.journey.modlib.actions.ActionDSLProvider
import heroes.journey.modlib.actions.IAction
import heroes.journey.mods.art.RenderableDSLImpl
import heroes.journey.mods.art.TextureMapDSLImpl
import heroes.journey.mods.items.ItemDSLImpl
import heroes.journey.mods.items.ItemSubTypeDSLImpl
import heroes.journey.mods.worldgen.*
import heroes.journey.registries.Registries
import heroes.journey.utils.Lang

/**
 * Call this at game startup before loading mods to register all modlib DSL providers.
 * Add additional DSL registrations here as new DSLs are migrated.
 */
fun setupModlibDSLs() {
    heroes.journey.modlib.Lang.instance = Lang

    // Wire up modlib registries to core implementations
    heroes.journey.modlib.Registries.QuestManager = Registries.QuestManager as Registry<IQuest>
    heroes.journey.modlib.Registries.StatManager = Registries.StatManager as Registry<IStat>
    heroes.journey.modlib.Registries.BuffManager = Registries.BuffManager as Registry<IBuff>
    heroes.journey.modlib.Registries.ChallengeManager = Registries.ChallengeManager as Registry<IChallenge>
    heroes.journey.modlib.Registries.ActionManager = Registries.ActionManager as Registry<IAction>
    heroes.journey.modlib.Registries.ItemManager = Registries.ItemManager as Registry<IItem>
    heroes.journey.modlib.Registries.ItemSubTypeManager =
        Registries.ItemSubTypeManager as Registry<IItemSubType>
    heroes.journey.modlib.Registries.BiomeManager = Registries.BiomeManager as Registry<IBiome>
    heroes.journey.modlib.Registries.FeatureTypeManager =
        Registries.FeatureTypeManager as Registry<IFeatureType>
    heroes.journey.modlib.Registries.TerrainManager = Registries.TerrainManager as Registry<ITerrain>
    heroes.journey.modlib.Registries.TileLayoutManager = Registries.TileLayoutManager as Registry<ITileLayout>
    heroes.journey.modlib.Registries.TileBatchManager = Registries.TileBatchManager as Registry<ITileBatch>
    heroes.journey.modlib.Registries.GroupManager = Registries.GroupManager as Registry<IGroup>
    heroes.journey.modlib.Registries.RenderableManager = Registries.RenderableManager as Registry<IRenderable>
    heroes.journey.modlib.Registries.TextureManager = Registries.TextureManager as Registry<ITextureMap>

    // Wire up modlib DSLs
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
