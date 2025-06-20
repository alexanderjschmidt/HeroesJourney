package heroes.journey.initializers.base.actions

import heroes.journey.components.PositionComponent
import heroes.journey.components.character.ActionComponent
import heroes.journey.components.character.MovementComponent
import heroes.journey.entities.Position
import heroes.journey.entities.actions.*
import heroes.journey.entities.actions.results.MultiStepResult
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.initializers.InitializerInterface
import heroes.journey.initializers.utils.Utils
import heroes.journey.registries.Registries.RegionManager
import heroes.journey.tilemap.Region
import heroes.journey.ui.HUD
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing
import java.util.*

class TravelActions : InitializerInterface {
    // TODO Pilgrimage lose a turn but go anywhere?
    override fun init() {
        go = action {
            id = "travel_to"
            name = "Travel to"
            description = "Travel to "
            cost = Cost(2, 0, 0, 0)
            inputDisplayNameFn = { input ->
                val items: List<String> = input.split(',')
                val name = description + RegionManager[items[1]]!!.getName()
                name
            }
            onHoverFn = { input ->
                val region: Region = RegionManager[input.input]!!
                HUD.get()
                    .cursor
                    .setMapPointerLoc(Position(region.center.x, region.center.y))
            }
            onSelectFn = { input ->
                val gs = input.gameState
                val e = input.entityId
                val region: Region = RegionManager[input.input]!!
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
                    gs.world.edit(e).create<ActionComponent>(ActionComponent::class.java)
                        .action(BaseActions.popup, "You have traveled to $input.input")
                })
                MultiStepResult(events)
            }
            onSelectAIFn = { input ->
                val gs = input.gameState
                val e = input.entityId
                val region: Region = RegionManager[input.input]!!
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
        travel = targetAction<Region> {
            id = "travel"
            name = "Travel"
            description = "Travel to a connected location"
            getTargets = { input ->
                val regionId = Utils.getRegion(input)
                val region = RegionManager[regionId]
                val wayfareLocations = ArrayList<Region>()
                for (connectionId in region!!.neighborRegionIds) {
                    val connection = RegionManager[connectionId]!!
                    wayfareLocations.add(connection)
                }
                wayfareLocations
            }
            targetAction = go!!.id
        }.register()
    }

    companion object {
        @JvmField
        var travel: TargetAction<Region>? = null
        var go: Action? = null
    }
}
