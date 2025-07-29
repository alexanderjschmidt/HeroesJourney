@file:Suppress("UNCHECKED_CAST")

package heroes.journey.mods

import heroes.journey.modlib.GameModProvider
import heroes.journey.modlib.actions.ActionDSLProvider
import heroes.journey.modlib.actions.IAction
import heroes.journey.modlib.art.IRenderable
import heroes.journey.modlib.art.RenderableDSLProvider
import heroes.journey.modlib.attributes.AttributesDSLProvider
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.attributes.StatDSLProvider
import heroes.journey.modlib.items.IItem
import heroes.journey.modlib.items.ItemDSLProvider
import heroes.journey.modlib.misc.*
import heroes.journey.modlib.registries.Registry
import heroes.journey.modlib.worldgen.*
import heroes.journey.mods.art.RenderableDSLImpl
import heroes.journey.mods.attributes.AttributesDSLImpl
import heroes.journey.mods.attributes.StatDSLImpl
import heroes.journey.mods.items.ItemDSLImpl
import heroes.journey.mods.misc.ActionDSLImpl
import heroes.journey.mods.misc.ApproachDSLImpl
import heroes.journey.mods.misc.ChallengeDSLImpl
import heroes.journey.mods.misc.QuestDSLImpl
import heroes.journey.mods.worldgen.BaseTileDSLImpl
import heroes.journey.mods.worldgen.FeatureTypeDSLImpl
import heroes.journey.mods.worldgen.TileBatchDSLImpl
import heroes.journey.mods.worldgen.TileLayoutDSLImpl
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

    heroes.journey.modlib.registries.Registries.ChallengeManager =
        Registries.ChallengeManager as Registry<IChallenge>
    heroes.journey.modlib.registries.Registries.ApproachManager =
        Registries.ApproachManager as Registry<IApproach>
    heroes.journey.modlib.registries.Registries.ActionManager = Registries.ActionManager as Registry<IAction>
    heroes.journey.modlib.registries.Registries.ItemManager = Registries.ItemManager as Registry<IItem>


    heroes.journey.modlib.registries.Registries.FeatureTypeManager =
        Registries.FeatureTypeManager as Registry<IFeatureType>

    heroes.journey.modlib.registries.Registries.TileLayoutManager =
        Registries.TileLayoutManager as Registry<ITileLayout>
    heroes.journey.modlib.registries.Registries.TileBatchManager =
        Registries.TileBatchManager as Registry<ITileBatch>
    heroes.journey.modlib.registries.Registries.RenderableManager =
        Registries.RenderableManager as Registry<IRenderable>


    GameModProvider.instance = GameModDSLImpl()

    // Wire up modlib DSLs

    RenderableDSLProvider.instance = RenderableDSLImpl()


    TileLayoutDSLProvider.instance = TileLayoutDSLImpl()
    TileBatchDSLProvider.instance = TileBatchDSLImpl()
    BaseTileDSLProvider.instance = BaseTileDSLImpl()
    StatDSLProvider.instance = StatDSLImpl()

    ItemDSLProvider.instance = ItemDSLImpl()
    AttributesDSLProvider.instance = AttributesDSLImpl()

    QuestDSLProvider.instance = QuestDSLImpl()
    ChallengeDSLProvider.instance = ChallengeDSLImpl()
    ApproachDSLProvider.instance = ApproachDSLImpl()
    FeatureTypeDSLProvider.instance = FeatureTypeDSLImpl()


    ActionDSLProvider.instance = ActionDSLImpl()
}
