@file:Suppress("UNCHECKED_CAST")

package heroes.journey.mods

import heroes.journey.modlib.GameModProvider
import heroes.journey.modlib.art.IRenderable
import heroes.journey.modlib.art.RenderableDSLProvider

import heroes.journey.modlib.registries.Registry
import heroes.journey.modlib.worldgen.BaseTileDSLProvider
import heroes.journey.modlib.worldgen.FeatureTypeDSLProvider
import heroes.journey.modlib.worldgen.IFeatureType
import heroes.journey.mods.art.RenderableDSLImpl

import heroes.journey.mods.worldgen.BaseTileDSLImpl
import heroes.journey.mods.worldgen.FeatureTypeDSLImpl
import heroes.journey.utils.Lang

/**
 * Call this at game startup before loading mods to register all modlib DSL providers.
 * Add additional DSL registrations here as new DSLs are migrated.
 */
fun setupModlibDSLs() {
    heroes.journey.modlib.Lang.instance = Lang

    // Wire up modlib registries to core implementations
    heroes.journey.modlib.registries.Registries.FeatureTypeManager =
        Registries.FeatureTypeManager as Registry<IFeatureType>
    heroes.journey.modlib.registries.Registries.RenderableManager =
        Registries.RenderableManager as Registry<IRenderable>

    GameModProvider.instance = GameModDSLImpl()

    // Wire up modlib DSLs
    RenderableDSLProvider.instance = RenderableDSLImpl()
    BaseTileDSLProvider.instance = BaseTileDSLImpl()
    FeatureTypeDSLProvider.instance = FeatureTypeDSLImpl()
}
