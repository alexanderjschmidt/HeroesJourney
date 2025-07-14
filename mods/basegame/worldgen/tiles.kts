import heroes.journey.modlib.Ids
import heroes.journey.tilemap.baseTile
import heroes.journey.tilemap.tileBatch
import heroes.journey.tilemap.tileLayout

tileLayout {
  id = Ids.TILE_LAYOUT_WANG_CORNER
  asset = "Textures/wangCorner.png"
  terrainRoles = listOf("base", "adjacentTileOuter", "adjacentTileInner")
}.register()

tileLayout {
  id = Ids.TILE_LAYOUT_CLIFF_TRANSITION_TAPPER
  asset = "Textures/cliffTransitionTapper.png"
  terrainRoles = listOf("base", "adjacentTileOuter", "adjacentTileInner")
}.register()

tileLayout {
  id = Ids.TILE_LAYOUT_CLIFF_TRANSITION
  asset = "Textures/cliffTransition.png"
  terrainRoles = listOf("cliff1", "cliff2", "adjacentTileOuter1", "adjacentTileOuter2", "adjacentTileInner")
}.register()

tileLayout {
  id = Ids.TILE_LAYOUT_WANG_EDGE
  asset = "Textures/wangEdge.png"
  terrainRoles = listOf("terrain", "adjacentTerrain")
}.register()

// Tile Batches (migrated from Tiles.java)
tileBatch {
  id = Ids.TILE_BATCH_PLAINS_TO_HILL_CORNER
  layout = Ids.TILE_LAYOUT_WANG_CORNER
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "base" to Ids.TERRAIN_PLAINS_TO_HILL,
    "adjacentTileOuter" to Ids.TERRAIN_PLAINS,
    "adjacentTileInner" to Ids.TERRAIN_HILLS
  )
  weight = 500
  startX = 0
  startY = 10
  addToDefault = true
}.register()

tileBatch {
  id = Ids.TILE_BATCH_CLIFF_TRANSITION_TAPPER_PLAINS_HILLS
  layout = Ids.TILE_LAYOUT_CLIFF_TRANSITION_TAPPER
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "base" to Ids.TERRAIN_PLAINS_TO_HILL,
    "adjacentTileOuter" to Ids.TERRAIN_PLAINS,
    "adjacentTileInner" to Ids.TERRAIN_HILLS
  )
  weight = 1
  startX = 5
  startY = 9
  addToDefault = true
}.register()

tileBatch {
  id = Ids.TILE_BATCH_SAND_TO_HILL_CORNER
  layout = Ids.TILE_LAYOUT_WANG_CORNER
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "base" to Ids.TERRAIN_SAND_TO_HILL,
    "adjacentTileOuter" to Ids.TERRAIN_SAND,
    "adjacentTileInner" to Ids.TERRAIN_HILLS
  )
  weight = 50
  startX = 0
  startY = 13
  addToDefault = true
}.register()

tileBatch {
  id = Ids.TILE_BATCH_CLIFF_TRANSITION_TAPPER_SAND_HILLS
  layout = Ids.TILE_LAYOUT_CLIFF_TRANSITION_TAPPER
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "base" to Ids.TERRAIN_SAND_TO_HILL,
    "adjacentTileOuter" to Ids.TERRAIN_SAND,
    "adjacentTileInner" to Ids.TERRAIN_HILLS
  )
  weight = 1
  startX = 5
  startY = 13
  addToDefault = true
}.register()

tileBatch {
  id = Ids.TILE_BATCH_CLIFF_TRANSITION_SAND_HILL
  layout = Ids.TILE_LAYOUT_CLIFF_TRANSITION
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "cliff1" to Ids.TERRAIN_SAND_TO_HILL,
    "cliff2" to Ids.TERRAIN_PLAINS_TO_HILL,
    "adjacentTileOuter1" to Ids.TERRAIN_SAND,
    "adjacentTileOuter2" to Ids.TERRAIN_PLAINS,
    "adjacentTileInner" to Ids.TERRAIN_HILLS
  )
  weight = 10
  startX = 5
  startY = 17
  addToDefault = true
}.register()

tileBatch {
  id = Ids.TILE_BATCH_PLAINS_TO_SAND_CORNER
  layout = Ids.TILE_LAYOUT_WANG_CORNER
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "base" to Ids.TERRAIN_PLAINS_TO_SAND,
    "adjacentTileOuter" to Ids.TERRAIN_PLAINS,
    "adjacentTileInner" to Ids.TERRAIN_SAND
  )
  weight = 500
  startX = 0
  startY = 16
  addToDefault = true
}.register()

tileBatch {
  id = Ids.TILE_BATCH_TREES_CORNER
  layout = Ids.TILE_LAYOUT_WANG_CORNER
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "base" to Ids.TERRAIN_TREES,
    "adjacentTileOuter" to Ids.TERRAIN_NULL,
    "adjacentTileInner" to Ids.TERRAIN_TREES
  )
  weight = 500
  startX = 0
  startY = 7
  addToDefault = false
}.register()

tileBatch {
  id = Ids.TILE_BATCH_PATH_EDGE
  layout = Ids.TILE_LAYOUT_WANG_EDGE
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "terrain" to Ids.TERRAIN_PATH,
    "adjacentTerrain" to Ids.TERRAIN_PLAINS
  )
  weight = 10000
  startX = 12
  startY = 0
  addToDefault = false
}.register()

// Animated Water Transitions

tileBatch {
  id = Ids.TILE_BATCH_PLAINS_TO_WATER_CORNER_ANIMATED
  layout = Ids.TILE_LAYOUT_WANG_CORNER
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "base" to Ids.TERRAIN_PLAINS_TO_WATER,
    "adjacentTileOuter" to Ids.TERRAIN_PLAINS,
    "adjacentTileInner" to Ids.TERRAIN_WATER
  )
  weight = 100
  startX = 20
  startY = 10
  addToDefault = true
  frameCount = 4
  frameDist = 5
}.register()

tileBatch {
  id = Ids.TILE_BATCH_HILL_TO_WATER_CORNER_ANIMATED
  layout = Ids.TILE_LAYOUT_WANG_CORNER
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "base" to Ids.TERRAIN_HILL_TO_WATER,
    "adjacentTileOuter" to Ids.TERRAIN_HILLS,
    "adjacentTileInner" to Ids.TERRAIN_WATER
  )
  weight = 10
  startX = 20
  startY = 13
  addToDefault = true
  frameCount = 4
  frameDist = 5
}.register()

tileBatch {
  id = Ids.TILE_BATCH_CLIFF_TRANSITION_PLAINS_HILL_WATER_ANIMATED
  layout = Ids.TILE_LAYOUT_CLIFF_TRANSITION
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "cliff1" to Ids.TERRAIN_PLAINS_TO_WATER,
    "cliff2" to Ids.TERRAIN_HILL_TO_WATER,
    "adjacentTileOuter1" to Ids.TERRAIN_PLAINS,
    "adjacentTileOuter2" to Ids.TERRAIN_HILLS,
    "adjacentTileInner" to Ids.TERRAIN_WATER
  )
  weight = 10
  startX = 20
  startY = 4
  addToDefault = true
  frameCount = 4
  frameDist = 4
}.register()

tileBatch {
  id = Ids.TILE_BATCH_SAND_TO_WATER_CORNER_ANIMATED
  layout = Ids.TILE_LAYOUT_WANG_CORNER
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "base" to Ids.TERRAIN_SAND_TO_WATER,
    "adjacentTileOuter" to Ids.TERRAIN_SAND,
    "adjacentTileInner" to Ids.TERRAIN_WATER
  )
  weight = 500
  startX = 20
  startY = 16
  addToDefault = true
  frameCount = 4
  frameDist = 5
}.register()

tileBatch {
  id = Ids.TILE_BATCH_CLIFF_TRANSITION_SAND_HILL_WATER_ANIMATED
  layout = Ids.TILE_LAYOUT_CLIFF_TRANSITION
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "cliff1" to Ids.TERRAIN_SAND_TO_WATER,
    "cliff2" to Ids.TERRAIN_HILL_TO_WATER,
    "adjacentTileOuter1" to Ids.TERRAIN_SAND,
    "adjacentTileOuter2" to Ids.TERRAIN_HILLS,
    "adjacentTileInner" to Ids.TERRAIN_WATER
  )
  weight = 10
  startX = 20
  startY = 6
  addToDefault = true
  frameCount = 4
  frameDist = 4
}.register()

tileBatch {
  id = Ids.TILE_BATCH_CLIFF_TRANSITION_SAND_PLAINS_WATER_ANIMATED
  layout = Ids.TILE_LAYOUT_CLIFF_TRANSITION
  textureMap = Ids.OVERWORLD_TILESET
  terrains = mapOf(
    "cliff1" to Ids.TERRAIN_SAND_TO_WATER,
    "cliff2" to Ids.TERRAIN_PLAINS_TO_WATER,
    "adjacentTileOuter1" to Ids.TERRAIN_SAND,
    "adjacentTileOuter2" to Ids.TERRAIN_PLAINS,
    "adjacentTileInner" to Ids.TERRAIN_WATER
  )
  weight = 10
  startX = 20
  startY = 8
  addToDefault = true
  frameCount = 4
  frameDist = 4
}.register()

// Base Tiles (migrated from Tiles.java)
baseTile {
  id = Ids.BASE_TILE_NULL
  terrain = Ids.TERRAIN_NULL
  textureMap = Ids.OVERWORLD_TILESET
  x = 3
  y = 0
  weight = 100
  addToBaseTiles = true
}

baseTile {
  id = Ids.BASE_TILE_WATER
  terrain = Ids.TERRAIN_WATER
  textureMap = Ids.OVERWORLD_TILESET
  x = 21
  y = 11
  weight = 300
  addToBaseTiles = true
  frameCount = 4
  frameDist = 5
  frameRate = 0.2f
}

baseTile {
  id = Ids.BASE_TILE_PLAINS
  terrain = Ids.TERRAIN_PLAINS
  textureMap = Ids.OVERWORLD_TILESET
  x = 1
  y = 5
  weight = 1000
  addToBaseTiles = true
}

baseTile {
  id = Ids.BASE_TILE_HILLS
  terrain = Ids.TERRAIN_HILLS
  textureMap = Ids.OVERWORLD_TILESET
  x = 1
  y = 11
  weight = 500
  addToBaseTiles = true
}

baseTile {
  id = Ids.BASE_TILE_SAND
  terrain = Ids.TERRAIN_SAND
  textureMap = Ids.OVERWORLD_TILESET
  x = 1
  y = 17
  weight = 200
  addToBaseTiles = true
}
