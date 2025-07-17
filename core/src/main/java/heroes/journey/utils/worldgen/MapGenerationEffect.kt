package heroes.journey.utils.worldgen

import heroes.journey.GameState
import heroes.journey.modlib.Registrable
import heroes.journey.utils.worldgen.MapGenerator.MapGenerationManager
import lombok.Getter

@Getter
abstract class MapGenerationEffect(id: String) : Registrable(id) {

    val dependsOn: MutableList<String> = ArrayList()

    fun register(vararg dependencies: MapGenerationEffect): MapGenerationEffect {
        for (dependency in dependencies) {
            dependsOn.add(dependency.id)
        }
        return register()
    }

    override fun register(): MapGenerationEffect {
        return MapGenerationManager.register(this)
    }

    abstract fun apply(gameState: GameState?)
}
