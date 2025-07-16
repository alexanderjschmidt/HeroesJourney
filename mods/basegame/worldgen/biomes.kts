import heroes.journey.modlib.Ids
import heroes.journey.modlib.featureType
import heroes.journey.modlib.biome
import heroes.journey.modlib.featureGenerationData

featureType(Ids.KINGDOM, Ids.CAPITAL_SPRITE).register()
featureType(Ids.TOWN, Ids.TOWN_SPRITE).register()
featureType(Ids.MINE, Ids.DUNGEON_SPRITE).register()

biome(
    id = Ids.BIOME_KINGDOM,
    baseTerrain = Ids.TERRAIN_PLAINS,
    featureGenerationData = listOf(
        featureGenerationData(
            featureTypeId = Ids.KINGDOM,
            minDist = 0,
            minInRegion = 1,
            maxInRegion = 1
        ),
        featureGenerationData(
            featureTypeId = Ids.TOWN,
            minDist = 5,
            minInRegion = 3,
            maxInRegion = 5
        ),
        featureGenerationData(
            featureTypeId = Ids.MINE,
            minDist = 5,
            minInRegion = 5,
            maxInRegion = 8
        )
    )
).register()

biome(
    id = Ids.BIOME_DESERT_KINGDOM,
    baseTerrain = Ids.TERRAIN_SAND,
    featureGenerationData = listOf(
        featureGenerationData(
            featureTypeId = Ids.KINGDOM,
            minDist = 0,
            minInRegion = 1,
            maxInRegion = 1
        ),
        featureGenerationData(
            featureTypeId = Ids.TOWN,
            minDist = 5,
            minInRegion = 3,
            maxInRegion = 5
        ),
        featureGenerationData(
            featureTypeId = Ids.MINE,
            minDist = 5,
            minInRegion = 5,
            maxInRegion = 10
        )
    )
).register()

biome(
    id = Ids.BIOME_MESA_KINGDOM,
    baseTerrain = Ids.TERRAIN_HILLS,
    featureGenerationData = listOf(
        featureGenerationData(
            featureTypeId = Ids.KINGDOM,
            minDist = 0,
            minInRegion = 1,
            maxInRegion = 1
        ),
        featureGenerationData(
            featureTypeId = Ids.TOWN,
            minDist = 5,
            minInRegion = 3,
            maxInRegion = 5
        ),
        featureGenerationData(
            featureTypeId = Ids.MINE,
            minDist = 5,
            minInRegion = 7,
            maxInRegion = 12
        )
    )
).register()
