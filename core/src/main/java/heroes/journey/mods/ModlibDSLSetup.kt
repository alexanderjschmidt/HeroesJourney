package heroes.journey.mods

import heroes.journey.modlib.GroupDSLProvider

/**
 * Call this at game startup before loading mods to register all modlib DSL providers.
 * Add additional DSL registrations here as new DSLs are migrated.
 */
fun setupModlibDSLs() {
    GroupDSLProvider.instance = GroupDSLImpl()
    // Register other DSLs here as needed
} 