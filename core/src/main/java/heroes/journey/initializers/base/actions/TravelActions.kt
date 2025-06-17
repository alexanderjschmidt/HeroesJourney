package heroes.journey.initializers.base.actions

import heroes.journey.components.PositionComponent
import heroes.journey.components.character.ActionComponent
import heroes.journey.components.character.MapComponent
import heroes.journey.components.character.MovementComponent
import heroes.journey.entities.Position
import heroes.journey.entities.actions.*
import heroes.journey.entities.actions.results.ActionListResult
import heroes.journey.entities.actions.results.MultiStepResult
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.initializers.InitializerInterface
import heroes.journey.initializers.base.Map
import heroes.journey.initializers.utils.Utils
import heroes.journey.registries.FeatureManager
import heroes.journey.registries.RegionManager
import heroes.journey.tilemap.FogUtils
import heroes.journey.ui.HUD
import heroes.journey.utils.Direction
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing
import java.util.*

class TravelActions : InitializerInterface {
    // TODO Pilgrimage lose a turn but go anywhere?
    // TODO No direction explore that expands in a circle?
    override fun init() {
        explore = targetAction<Direction> {
            id = "explore"
            name = "Explore"
            description = "Explore in a direction"
            cost = Cost(5, 2, 0, 0)
            getTargets = { input ->
                Direction.getDirections().toList()
            }
            getTargetDisplayName = { input ->
                "Explore ${input.input}"
            }
            onSelectTargetFn = { input ->
                val gs = input.gameState
                val e = input.entityId
                val position = PositionComponent.get(gs.world, e)
                val mapComponent = MapComponent.get(gs.world, e)

                // TODO Based on Stamina/Body
                val revealDistance = 8 // How far the cone extends
                // TODO Based on Vision
                val baseWidth = 0 // Start width (0 = just 1 tile at origin)

                FogUtils.revealCone(
                    gs, mapComponent, position.x, position.y, revealDistance,
                    baseWidth, input.input
                )

                StringResult("You have explored the ${input.input}")
            }
        }.register()

        wayfare = targetAction<Int> {
            id = "wayfare"
            name = "Wayfare"
            description = "Travel to a connected location"
            cost = Cost(2, 0, 0, 0)
            getTargets = { input ->
                val regionId = Utils.getRegion(input)
                val region = RegionManager.get()[regionId]
                val wayfareLocations = ArrayList<Int>()
                for (connectionId in region!!.neighborRegionIds) {
                    val connection = RegionManager.getRegion(connectionId)
                    wayfareLocations.add(connection.id)
                }
                wayfareLocations
            }
            requirementsMetFn = { input ->
                val featureId = Utils.getLocation(input)
                val feature = FeatureManager.get()[featureId]
                if (feature!!.getType() === Map.KINGDOM || feature!!.getType() === Map.TOWN) ShowAction.YES else ShowAction.GRAYED
            }
            getTargetDisplayName = { input ->
                input.input.toString()
            }
            onHoverTargetFn = { input ->
                val region = RegionManager.getRegion(input.input)
                HUD.get()
                    .cursor
                    .setMapPointerLoc(Position(region.center.x, region.center.y))
            }
            onSelectTargetFn = { input ->
                val gs = input.gameState
                val e = input.entityId
                val region = RegionManager.getRegion(input.input)
                val positionComponent = PositionComponent.get(gs.world, e)
                val path = EntityCursorPathing().getPath(
                    gs.map, positionComponent.x,
                    positionComponent.y, region.center.x, region.center.y, e
                )

                val events: Queue<Runnable> = LinkedList()
                events.add(Runnable {
                    gs.world.edit(e).create(
                        MovementComponent::class.java
                    ).path(path.reverse())
                })
                events.add(Runnable {
                    BaseActions.popupMessage = "You have traveled to $input.input"
                    gs.world.edit(e).create<ActionComponent>(ActionComponent::class.java)
                        .action(BaseActions.popup)
                })
                MultiStepResult(events)
            }
            onSelectAITargetFn = { input ->
                val gs = input.gameState
                val e = input.entityId
                val region = RegionManager.getRegion(input.input)
                val positionComponent = PositionComponent.get(gs.world, e)
                val path = EntityCursorPathing().getPath(
                    gs.map, positionComponent.x,
                    positionComponent.y, region.center.x, region.center.y, e
                )

                val end = gs.entities.moveEntity(e, path)
                positionComponent.setPos(end.x, end.y)
                positionComponent.sync()
                StringResult("You have traveled to ${region.id}")
            }
        }.register()

        val travelActionOptions: MutableList<Action?> = ArrayList()
        travelActionOptions.add(explore)
        travelActionOptions.add(wayfare)
        travel = action {
            id = "travel"
            name = "Travel"
            description = "Choose a travel option"
            isReturnsActionList = true
            onSelectFn = { input ->
                ActionListResult(travelActionOptions)
            }
        }.register()
    }

    companion object {
        @JvmField
        var travel: Action? = null
        var wayfare: TargetAction<Int>? = null
        var explore: TargetAction<Direction>? = null
    }
}
