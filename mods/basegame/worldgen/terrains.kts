import heroes.journey.modlib.worldgen.terrain
import heroes.journey.modlib.Ids

terrain { id = Ids.TERRAIN_NULL }.register()
terrain { id = Ids.TERRAIN_WATER }.register()
terrain { id = Ids.TERRAIN_PLAINS }.register()
terrain { id = Ids.TERRAIN_HILLS }.register()
terrain { id = Ids.TERRAIN_SAND }.register()
terrain { id = Ids.TERRAIN_PATH }.register()
terrain { id = Ids.TERRAIN_TREES }.register()
// Transition Terrains
terrain { id = Ids.TERRAIN_PLAINS_TO_HILL }.register()
terrain { id = Ids.TERRAIN_SAND_TO_HILL }.register()
terrain { id = Ids.TERRAIN_PLAINS_TO_SAND }.register()
terrain { id = Ids.TERRAIN_PLAINS_TO_WATER }.register()
terrain { id = Ids.TERRAIN_HILL_TO_WATER }.register()
terrain { id = Ids.TERRAIN_SAND_TO_WATER }.register()
