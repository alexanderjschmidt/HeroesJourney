import heroes.journey.GameState
import heroes.journey.components.NamedComponent
import heroes.journey.components.PositionComponent
import heroes.journey.components.RegionComponent
import heroes.journey.components.character.MovementComponent
import heroes.journey.entities.Position
import heroes.journey.entities.actions.action
import heroes.journey.entities.actions.targetAction
import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.results.StringResult
import heroes.journey.ui.HUD
import heroes.journey.utils.Lang
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing
import heroes.journey.utils.gamestate.Utils
import java.util.*

// Travel Actions - included by basegame mod

// Go Action
action {
    id = "travel_to"
    inputDisplayNameFn = { input ->
        val name =
            NamedComponent.get(GameState.global().world, UUID.fromString(input["target"]), "---")
        Lang.get("travel_to_description") + name
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

        gs.world.edit(e).create(
            MovementComponent::class.java
        ).path(path.reverse())
        StringResult("You are traveling to $input['target']")
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
