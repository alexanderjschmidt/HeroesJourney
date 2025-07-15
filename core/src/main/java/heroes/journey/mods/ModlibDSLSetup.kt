package heroes.journey.mods

import heroes.journey.modlib.GroupDSLProvider
import heroes.journey.modlib.RenderableDSLProvider

/**
 * Call this at game startup before loading mods to register all modlib DSL providers.
 * Add additional DSL registrations here as new DSLs are migrated.
 */
fun setupModlibDSLs() {
    GroupDSLProvider.instance = GroupDSLImpl()
    RenderableDSLProvider.instance = RenderableDSLImpl()
    // Register other DSLs here as needed
} 