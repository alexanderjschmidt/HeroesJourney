package heroes.journey.tilemap

import heroes.journey.GameState
import heroes.journey.entities.Position
import heroes.journey.registries.Registries.FeatureTypeManager
import java.util.*

class FeatureBuilder {
    var id: String = ""
    var minDist: Int = 2
    var minInRegion: Int = 0
    var maxInRegion: Int = 1
    fun build(): FeatureGenerationData =
        FeatureGenerationData(FeatureTypeManager[id]!!, minDist, minInRegion, maxInRegion)
}

class BiomeBuilder {
    var id: String = ""
    var baseTerrain: String = ""
    private val featuresList = mutableListOf<FeatureGenerationData>()
    fun feature(init: FeatureBuilder.() -> Unit) {
        val builder = FeatureBuilder()
        builder.init()
        featuresList.add(builder.build())
    }

    fun build(): Biome = Biome(id, baseTerrain, featuresList)
}

fun biome(init: BiomeBuilder.() -> Unit): Biome {
    val builder = BiomeBuilder()
    builder.init()
    return builder.build()
}

class FeatureTypeBuilder {
    var id: String = ""
    var onGenerate: ((GameState, Position) -> UUID)? = null
    fun build(): FeatureType {
        val idCopy = id
        val onGen = onGenerate
        return object : FeatureType(idCopy) {
            override fun generateFeatureInner(gs: GameState, pos: Position): UUID {
                return onGen?.invoke(gs, pos)
                    ?: throw IllegalStateException("No onGenerate lambda provided for FeatureType $idCopy")
            }
        }
    }
}

fun featureType(init: FeatureTypeBuilder.() -> Unit): FeatureType {
    val builder = FeatureTypeBuilder()
    builder.init()
    return builder.build()
}
