import heroes.journey.initializers.Ids
import heroes.journey.tilemap.biome
import heroes.journey.tilemap.featureType
import heroes.journey.utils.worldgen.namegen.SyllableTownNameGenerator

featureType {
    id = Ids.KINGDOM
    name = "Kingdom"
    onGenerate = { gs, pos ->
        gs.world.entityFactory.generateBasicLocation(
            SyllableTownNameGenerator.generateName(),
            pos.x, pos.y, Ids.CAPITAL_SPRITE
        )
    }
}

featureType {
    id = Ids.TOWN
    name = "Town"
    onGenerate = { gs, pos ->
        gs.world.entityFactory.generateBasicLocation(
            SyllableTownNameGenerator.generateName(),
            pos.x, pos.y, Ids.TOWN_SPRITE
        )
    }
}

featureType {
    id = Ids.DUNGEON
    name = "Dungeon"
    onGenerate = { gs, pos ->
        gs.world.entityFactory.generateDungeon(pos.x, pos.y)
    }
}

featureType {
    id = Ids.MINE
    name = "Mine"
    onGenerate = { gs, pos ->
        gs.world.entityFactory.generateBasicLocation(
            "Mine",
            pos.x, pos.y, Ids.DUNGEON_SPRITE
        )
    }
}

biome {
    id = "kingdom"
    name = "Kingdom"
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
        id = Ids.DUNGEON
        minDist = 5
        minInRegion = 1
        maxInRegion = 2
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
    name = "Desert Kingdom"
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
        id = Ids.DUNGEON
        minDist = 5
        minInRegion = 1
        maxInRegion = 2
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
    name = "Mesa Kingdom"
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
        id = Ids.DUNGEON
        minDist = 5
        minInRegion = 1
        maxInRegion = 2
    }
    feature {
        id = Ids.MINE
        minDist = 5
        minInRegion = 7
        maxInRegion = 12
    }
}.register()
