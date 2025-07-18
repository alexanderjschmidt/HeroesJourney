import heroes.journey.modlib.Ids
import heroes.journey.modlib.worldgen.featureType
import heroes.journey.modlib.worldgen.biome
import heroes.journey.modlib.worldgen.featureGenerationData

featureType { id = Ids.KINGDOM; renderId = Ids.CAPITAL_SPRITE }.register()
featureType { id = Ids.TOWN; renderId = Ids.TOWN_SPRITE }.register()
featureType { id = Ids.MINE; renderId = Ids.DUNGEON_SPRITE }.register()

biome {
    id = Ids.BIOME_KINGDOM
    baseTerrain = Ids.TERRAIN_PLAINS
    feature { featureTypeId = Ids.KINGDOM; minDist = 0; minInRegion = 1; maxInRegion = 1 }
    feature { featureTypeId = Ids.TOWN; minDist = 5; minInRegion = 3; maxInRegion = 5 }
    feature { featureTypeId = Ids.MINE; minDist = 5; minInRegion = 5; maxInRegion = 8 }
}.register()

biome {
    id = Ids.BIOME_DESERT_KINGDOM
    baseTerrain = Ids.TERRAIN_SAND
    feature { featureTypeId = Ids.KINGDOM; minDist = 0; minInRegion = 1; maxInRegion = 1 }
    feature { featureTypeId = Ids.TOWN; minDist = 5; minInRegion = 3; maxInRegion = 5 }
    feature { featureTypeId = Ids.MINE; minDist = 5; minInRegion = 5; maxInRegion = 10 }
}.register()

biome {
    id = Ids.BIOME_MESA_KINGDOM
    baseTerrain = Ids.TERRAIN_HILLS
    feature { featureTypeId = Ids.KINGDOM; minDist = 0; minInRegion = 1; maxInRegion = 1 }
    feature { featureTypeId = Ids.TOWN; minDist = 5; minInRegion = 3; maxInRegion = 5 }
    feature { featureTypeId = Ids.MINE; minDist = 5; minInRegion = 7; maxInRegion = 12 }
}.register()
