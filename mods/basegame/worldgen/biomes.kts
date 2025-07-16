import heroes.journey.modlib.Ids
import heroes.journey.modlib.featureType
import heroes.journey.tilemap.biome

featureType(Ids.KINGDOM, Ids.CAPITAL_SPRITE).register()
featureType(Ids.TOWN, Ids.TOWN_SPRITE).register()
featureType(Ids.MINE, Ids.DUNGEON_SPRITE).register()

biome {
    id = "kingdom"
    baseTerrain = Ids.TERRAIN_PLAINS
    feature {
        id = Ids.KINGDOM
        minDist = 0
        minInRegion = 1
        maxInRegion = 1
    }
    feature {
        id = Ids.TOWN
        minDist = 5
        minInRegion = 3
        maxInRegion = 5
    }
    feature {
        id = Ids.MINE
        minDist = 5
        minInRegion = 5
        maxInRegion = 8
    }
}.register()

biome {
    id = "desert_kingdom"
    baseTerrain = Ids.TERRAIN_SAND
    feature {
        id = Ids.KINGDOM
        minDist = 0
        minInRegion = 1
        maxInRegion = 1
    }
    feature {
        id = Ids.TOWN
        minDist = 5
        minInRegion = 3
        maxInRegion = 5
    }
    feature {
        id = Ids.MINE
        minDist = 5
        minInRegion = 5
        maxInRegion = 10
    }
}.register()

biome {
    id = "mesa_kingdom"
    baseTerrain = Ids.TERRAIN_HILLS
    feature {
        id = Ids.KINGDOM
        minDist = 0
        minInRegion = 1
        maxInRegion = 1
    }
    feature {
        id = Ids.TOWN
        minDist = 5
        minInRegion = 3
        maxInRegion = 5
    }
    feature {
        id = Ids.MINE
        minDist = 5
        minInRegion = 7
        maxInRegion = 12
    }
}.register()
