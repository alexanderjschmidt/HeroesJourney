package heroes.journey.mods

import heroes.journey.modlib.*
import heroes.journey.tilemap.Biome

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
    // Register other DSLs here as needed
    BiomeDSLProvider.instance = BiomeDSLImpl()
    FeatureGenerationDataDSLProvider.instance = FeatureGenerationDataDSLImpl()
}

// --- DSL Implementations ---

class BiomeDSLImpl : BiomeDSL {
    override fun biome(
        id: String,
        baseTerrain: String,
        featureGenerationData: List<FeatureGenerationData>
    ): IBiome {
        return Biome(id, baseTerrain, featureGenerationData)
    }
}

class FeatureGenerationDataDSLImpl : FeatureGenerationDataDSL {
    override fun featureGenerationData(
        featureTypeId: String,
        minDist: Int,
        minInRegion: Int,
        maxInRegion: Int
    ): FeatureGenerationData {
        return FeatureGenerationData(featureTypeId, minDist, minInRegion, maxInRegion)
    }
}
