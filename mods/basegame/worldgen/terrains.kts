import heroes.journey.tilemap.terrain

terrain {
    id = "null"
    name = "NULL"
    terrainCost = 0
}.register()
terrain {
    id = "water"
    name = "Water"
    terrainCost = 50
}.register()
terrain {
    id = "plains"
    name = "Plains"
    terrainCost = 2
}.register()
terrain {
    id = "hills"
    name = "Hills"
    terrainCost = 2
}.register()
terrain {
    id = "sand"
    name = "Sand"
    terrainCost = 3
}.register()
terrain {
    id = "path"
    name = "Path"
    terrainCost = 1
}.register()
terrain {
    id = "trees"
    name = "Trees"
    terrainCost = 1
}.register()
// Transition Terrains
terrain {
    id = "plains_to_hill"
    name = "Cliff"
    terrainCost = 10
}.register()
terrain {
    id = "sand_to_hill"
    name = "Cliff"
    terrainCost = 10
}.register()
terrain {
    id = "plains_to_sand"
    name = "Sand"
    terrainCost = 3
}.register()
terrain {
    id = "plains_to_water"
    name = "Water"
    terrainCost = 50
}.register()
terrain {
    id = "hill_to_water"
    name = "Cliff"
    terrainCost = 50
}.register()
terrain {
    id = "sand_to_water"
    name = "Water"
    terrainCost = 50
}.register()
