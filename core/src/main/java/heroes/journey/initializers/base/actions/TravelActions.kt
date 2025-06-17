package heroes.journey.initializers.base.actions

import heroes.journey.components.PositionComponent
import heroes.journey.components.character.ActionComponent
import heroes.journey.components.character.MovementComponent
import heroes.journey.entities.Position
import heroes.journey.entities.actions.Cost
import heroes.journey.entities.actions.ShowAction
import heroes.journey.entities.actions.TargetAction
import heroes.journey.entities.actions.results.MultiStepResult
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.entities.actions.targetAction
import heroes.journey.initializers.InitializerInterface
import heroes.journey.initializers.base.Map
import heroes.journey.initializers.utils.Utils
import heroes.journey.registries.FeatureManager
import heroes.journey.registries.RegionManager
import heroes.journey.ui.HUD
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing
import java.util.*

class TravelActions : InitializerInterface {
    // TODO Pilgrimage lose a turn but go anywhere?
    override fun init() {
        travel = targetAction<Int> {
            id = "travel"
            name = "Travel"
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
    }

    companion object {
        @JvmField
        var travel: TargetAction<Int>? = null
    }
}
