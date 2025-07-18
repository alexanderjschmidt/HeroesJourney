import heroes.journey.modlib.worldgen.terrain
import heroes.journey.modlib.Ids

terrain(Ids.TERRAIN_NULL).register()
terrain(Ids.TERRAIN_WATER).register()
terrain(Ids.TERRAIN_PLAINS).register()
terrain(Ids.TERRAIN_HILLS).register()
terrain(Ids.TERRAIN_SAND).register()
terrain(Ids.TERRAIN_PATH).register()
terrain(Ids.TERRAIN_TREES).register()
// Transition Terrains
terrain(Ids.TERRAIN_PLAINS_TO_HILL).register()
terrain(Ids.TERRAIN_SAND_TO_HILL).register()
terrain(Ids.TERRAIN_PLAINS_TO_SAND).register()
terrain(Ids.TERRAIN_PLAINS_TO_WATER).register()
terrain(Ids.TERRAIN_HILL_TO_WATER).register()
terrain(Ids.TERRAIN_SAND_TO_WATER).register()
