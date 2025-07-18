package heroes.journey.tilemap

import heroes.journey.tilemap.wavefunctiontiles.Terrain

class TerrainBuilder {
    var id: String = ""
    var terrainCost: Int = 1

    fun build(): Terrain {
        return Terrain(id, terrainCost)
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
        return TileBatch(
            id,
            layout,
            textureMap,
            terrains,
            weight,
            startX,
            startY,
            addToDefault,
            isAnimated,
            frameCount,
            frameDist
        )
    }
}

fun tileBatch(init: TileBatchBuilder.() -> Unit): TileBatch {
    val builder = TileBatchBuilder()
    builder.init()
    return builder.build()
}

class BaseTileDef(
    val id: String,
    val terrain: String,
    val textureMap: String,
    val x: Int,
    val y: Int,
    val weight: Int,
    val addToBaseTiles: Boolean,
    val frameCount: Int,
    val frameDist: Int,
    val frameRate: Float
)

class BaseTileBuilder {
    var id: String = ""
    var terrain: String = ""
    var textureMap: String = ""
    var x: Int = 0
    var y: Int = 0
    var weight: Int = 1
    var addToBaseTiles: Boolean = true
    var frameCount: Int = 0
    var frameDist: Int = 0
    var frameRate: Float = 0.2f

    fun build(): BaseTileDef {
        return BaseTileDef(
            id,
            terrain,
            textureMap,
            x,
            y,
            weight,
            addToBaseTiles,
            frameCount,
            frameDist,
            frameRate
        )
    }
}

fun baseTile(init: BaseTileBuilder.() -> Unit) {
    val builder = BaseTileBuilder()
    builder.init()
    val def = builder.build()
    TileManager.addBaseTileDef(def)
}

