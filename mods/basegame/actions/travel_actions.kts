import heroes.journey.GameState
import heroes.journey.Ids
import heroes.journey.components.NamedComponent
import heroes.journey.components.PositionComponent
import heroes.journey.components.RegionComponent
import heroes.journey.components.character.ActionComponent
import heroes.journey.components.character.MovementComponent
import heroes.journey.entities.Position
import heroes.journey.entities.actions.*
import heroes.journey.entities.actions.results.MultiStepResult
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.utils.gamestate.Utils
import heroes.journey.ui.HUD
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing
import heroes.journey.registries.Registries
import java.util.*

// Travel Actions - included by basegame mod

// Go Action
action {
    id = "travel_to"
    name = "Travel to"
    description = "Travel to "
    inputDisplayNameFn = { input ->
        val name =
            NamedComponent.get(GameState.global().world, UUID.fromString(input["target"]), "---")
        description + name
    }
    onHoverFn = { input ->
        val pos: PositionComponent =
            PositionComponent.get(input.gameState.getWorld(), UUID.fromString(input["target"]))
        HUD.get()
            .cursor
            .setMapPointerLoc(Position(pos.x, pos.y))
    }
    onSelectFn = { input ->
        val gs = input.gameState
        val e = input.entityId
        val pos: PositionComponent =
            PositionComponent.get(input.gameState.getWorld(), UUID.fromString(input["target"]))
        val positionComponent = PositionComponent.get(gs.world, e)
        val path = EntityCursorPathing().getPath(
            gs.map, positionComponent.x,
            positionComponent.y, pos.x, pos.y, e
        )

        val events: Queue<Runnable> = LinkedList()
        events.add(Runnable {
            gs.world.edit(e).create(
                MovementComponent::class.java
            ).path(path.reverse())
        })
        events.add(Runnable {
            val inputs: HashMap<String, String> = HashMap()
            inputs["message"] = "You have traveled to $input['target']"
            gs.world.edit(e).create<ActionComponent>(ActionComponent::class.java)
                .action(Registries.ActionManager.get(Ids.POPUP), inputs)
        })
        MultiStepResult(events)
    }
    onSelectAIFn = { input ->
        val gs = input.gameState
        val e = input.entityId
        val pos: PositionComponent =
            PositionComponent.get(input.gameState.getWorld(), UUID.fromString(input["target"]))
        val positionComponent = PositionComponent.get(gs.world, e)
        val path = EntityCursorPathing().getPath(
            gs.map, positionComponent.x,
            positionComponent.y, pos.x, pos.y, e
        )

        val end = gs.entities.moveEntity(e, path)
        positionComponent.setPos(end.x, end.y)
        positionComponent.sync()
        val name =
            NamedComponent.get(GameState.global().world, UUID.fromString(input["target"]), "---")
        StringResult("You have traveled to ${name}")
    }
}.register()

// Travel Action
targetAction<UUID> {
    id = "travel"
    name = "Travel"
    description = "Travel to a connected location"
    getTargets = { input ->
        val regionId = Utils.getRegion(input)
        val region: RegionComponent = RegionComponent.get(input.gameState.getWorld(), regionId)
        val wayfareLocations = ArrayList<UUID>()
        for (connectionId in region.neighborRegionIds) {
            wayfareLocations.add(connectionId)
        }
        wayfareLocations
    }
    targetAction = Ids.TRAVEL_TO
}.register()
