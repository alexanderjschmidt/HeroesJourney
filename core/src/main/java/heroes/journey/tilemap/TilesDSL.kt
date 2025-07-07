package heroes.journey.tilemap

import heroes.journey.registries.Registries
import heroes.journey.tilemap.wavefunctiontiles.Terrain
import heroes.journey.tilemap.wavefunctiontiles.Tile
import heroes.journey.utils.art.ResourceManager
import heroes.journey.utils.art.ResourceManager.TextureManager

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

class TileLayoutBuilder {
    var id: String = ""
    var asset: String = ""
    var terrainRoles: List<String> = emptyList()

    fun build(): TileLayout {
        return TileLayout(id, asset, terrainRoles)
    }
}

fun tileLayout(init: TileLayoutBuilder.() -> Unit): TileLayout {
    val builder = TileLayoutBuilder()
    builder.init()
    return builder.build()
}

class TileBatchBuilder {
    var id: String = ""
    var layout: String = ""
    var textureMap: String = ""
    var terrains: Map<String, String> = emptyMap()
    var weight: Int = 1
    var startX: Int = 0
    var startY: Int = 0
    var addToDefault: Boolean = true
    var frameCount: Int = 0
    var frameDist: Int = 0

    fun build(): TileBatch {
        val isAnimated = frameDist > 0
        return TileBatch(id, layout, textureMap, terrains, weight, startX, startY, addToDefault, isAnimated, frameCount, frameDist)
    }
}

fun tileBatch(init: TileBatchBuilder.() -> Unit): TileBatch {
    val builder = TileBatchBuilder()
    builder.init()
    return builder.build()
}

