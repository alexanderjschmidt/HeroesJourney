package heroes.journey.tilemap

import heroes.journey.tilemap.wavefunctiontiles.Terrain

class TerrainBuilder {
    var id: String = ""
    var name: String = ""
    var terrainCost: Int = 1

    fun build(): Terrain {
        return Terrain(id, name, terrainCost)
    }
}

fun terrain(init: TerrainBuilder.() -> Unit): Terrain {
    val builder = TerrainBuilder()
    builder.init()
    return builder.build()
}

