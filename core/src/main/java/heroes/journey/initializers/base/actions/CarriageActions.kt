package heroes.journey.initializers.base.actions

import heroes.journey.components.NamedComponent
import heroes.journey.components.PositionComponent
import heroes.journey.entities.actions.TargetAction
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.entities.actions.targetAction
import heroes.journey.initializers.InitializerInterface
import heroes.journey.initializers.base.Map
import heroes.journey.initializers.utils.Utils
import heroes.journey.registries.FeatureManager
import heroes.journey.tilemap.Feature

class CarriageActions : InitializerInterface {
    override fun init() {
        carriage = targetAction<Feature> {
            id = "carriage"
            name = "Carriage"
            description = "Travel to a kingdom by carriage"
            getTargets = { input ->
                val currentLocation = Utils.getLocation(input)
                val featuresWithCarriage = ArrayList<Feature>()
                for (feature in FeatureManager.get().values) {
                    if (feature.getType() !== Map.KINGDOM || currentLocation === feature.entityId) {
                        continue
                    }
                    featuresWithCarriage.add(feature)
                }
                featuresWithCarriage
            }
            onSelectTargetFn = { input ->
                val town = input.gameState
                    .entities
                    .getLocation(
                        input.input.location.x,
                        input.input.location.y
                    )
                val positionComponentLocation = PositionComponent.get(
                    input.gameState.world, town
                )
                val locationName = NamedComponent.get(
                    input.gameState.world, town,
                    "Unknown Location"
                )
                val positionComponent = PositionComponent.get(
                    input.gameState.world, input.entityId
                )
                positionComponent.setPos(positionComponentLocation.x, positionComponentLocation.y)
                StringResult("You have arrived at $locationName on a carriage")
            }
        }.register()
    }

    companion object {
        @JvmField
        var carriage: TargetAction<Feature>? = null
    }
}
