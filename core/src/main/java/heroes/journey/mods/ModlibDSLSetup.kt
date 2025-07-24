@file:Suppress("UNCHECKED_CAST")

package heroes.journey.mods

import heroes.journey.modlib.GameModProvider
import heroes.journey.modlib.actions.ActionDSLProvider
import heroes.journey.modlib.actions.IAction
import heroes.journey.modlib.art.IRenderable
import heroes.journey.modlib.art.ITextureMap
import heroes.journey.modlib.art.RenderableDSLProvider
import heroes.journey.modlib.art.TextureMapDSLProvider
import heroes.journey.modlib.attributes.*
import heroes.journey.modlib.items.IItem
import heroes.journey.modlib.items.IItemSubType
import heroes.journey.modlib.items.ItemDSLProvider
import heroes.journey.modlib.items.ItemSubTypeDSLProvider
import heroes.journey.modlib.misc.*
import heroes.journey.modlib.registries.Registry
import heroes.journey.modlib.worldgen.*
import heroes.journey.mods.art.RenderableDSLImpl
import heroes.journey.mods.art.TextureMapDSLImpl
import heroes.journey.mods.attributes.AttributesDSLImpl
import heroes.journey.mods.attributes.GroupDSLImpl
import heroes.journey.mods.attributes.StatDSLImpl
import heroes.journey.mods.items.ItemDSLImpl
import heroes.journey.mods.items.ItemSubTypeDSLImpl
import heroes.journey.mods.misc.ActionDSLImpl
import heroes.journey.mods.misc.ApproachDSLImpl
import heroes.journey.mods.misc.BuffDSLImpl
import heroes.journey.mods.misc.ChallengeDSLImpl
import heroes.journey.mods.misc.QuestDSLImpl
import heroes.journey.mods.worldgen.*
import heroes.journey.utils.Lang

/**
 * Call this at game startup before loading mods to register all modlib DSL providers.
 * Add additional DSL registrations here as new DSLs are migrated.
 */
fun setupModlibDSLs() {
    heroes.journey.modlib.Lang.instance = Lang

    // Wire up modlib registries to core implementations
    heroes.journey.modlib.registries.Registries.QuestManager = Registries.QuestManager as Registry<IQuest>
    heroes.journey.modlib.registries.Registries.StatManager = Registries.StatManager as Registry<IStat>
    heroes.journey.modlib.registries.Registries.BuffManager = Registries.BuffManager as Registry<IBuff>
    heroes.journey.modlib.registries.Registries.ChallengeManager =
        Registries.ChallengeManager as Registry<IChallenge>
    heroes.journey.modlib.registries.Registries.ApproachManager =
        Registries.ApproachManager as Registry<IApproach>
    heroes.journey.modlib.registries.Registries.ActionManager = Registries.ActionManager as Registry<IAction>
    heroes.journey.modlib.registries.Registries.ItemManager = Registries.ItemManager as Registry<IItem>
    heroes.journey.modlib.registries.Registries.ItemSubTypeManager =
        Registries.ItemSubTypeManager as Registry<IItemSubType>
    heroes.journey.modlib.registries.Registries.BiomeManager = Registries.BiomeManager as Registry<IBiome>
    heroes.journey.modlib.registries.Registries.FeatureTypeManager =
        Registries.FeatureTypeManager as Registry<IFeatureType>
    heroes.journey.modlib.registries.Registries.TerrainManager =
        Registries.TerrainManager as Registry<ITerrain>
    heroes.journey.modlib.registries.Registries.TileLayoutManager =
        Registries.TileLayoutManager as Registry<ITileLayout>
    heroes.journey.modlib.registries.Registries.TileBatchManager =
        Registries.TileBatchManager as Registry<ITileBatch>
    heroes.journey.modlib.registries.Registries.GroupManager = Registries.GroupManager as Registry<IGroup>
    heroes.journey.modlib.registries.Registries.RenderableManager =
        Registries.RenderableManager as Registry<IRenderable>
    heroes.journey.modlib.registries.Registries.TextureManager =
        Registries.TextureManager as Registry<ITextureMap>

    GameModProvider.instance = GameModDSLImpl()

    // Wire up modlib DSLs
    GroupDSLProvider.instance = GroupDSLImpl()
    RenderableDSLProvider.instance = RenderableDSLImpl()
    TextureMapDSLProvider.instance = TextureMapDSLImpl()
    TerrainDSLProvider.instance = TerrainDSLImpl()
    TileLayoutDSLProvider.instance = TileLayoutDSLImpl()
    TileBatchDSLProvider.instance = TileBatchDSLImpl()
    BaseTileDSLProvider.instance = BaseTileDSLImpl()
    StatDSLProvider.instance = StatDSLImpl()
    ItemSubTypeDSLProvider.instance = ItemSubTypeDSLImpl()
    ItemDSLProvider.instance = ItemDSLImpl()
    AttributesDSLProvider.instance = AttributesDSLImpl()
    BuffDSLProvider.instance = BuffDSLImpl()
    QuestDSLProvider.instance = QuestDSLImpl()
    ChallengeDSLProvider.instance = ChallengeDSLImpl()
    ApproachDSLProvider.instance = ApproachDSLImpl()
    FeatureTypeDSLProvider.instance = FeatureTypeDSLImpl()
    BiomeDSLProvider.instance = BiomeDSLImpl()
    FeatureGenerationDataDSLProvider.instance = FeatureGenerationDataDSLImpl()
    ActionDSLProvider.instance = ActionDSLImpl()
}
