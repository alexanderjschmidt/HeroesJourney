import heroes.journey.entities.actions.action
import heroes.journey.entities.actions.targetAction
import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.results.StringResult
import heroes.journey.ui.HUD
import heroes.journey.utils.Lang
import java.util.*

// Travel Actions - included by basegame mod

// Go Action
action {
    id = "travel_to"
    inputDisplayNameFn = { input ->
        val name = input.getName(UUID.fromString(input["target"]))
        Lang.get("travel_to_description") + name
    }
    onHoverFn = { input ->
        val locationId = UUID.fromString(input["target"])
        val targetPos = input.getPosition(locationId)
        HUD.get()
            .cursor
            .setMapPointerLoc(targetPos)
    }
    onSelectFn = { input ->
        val e = input.entityId!!
        val locationId = UUID.fromString(input["target"])
        val targetPos = input.getPosition(locationId)
        val targetName = input.getName(locationId)
        input.travelTo(e, targetPos)
        StringResult("You are traveling to " + targetName)
    }
}.register()

// Travel Action
targetAction<UUID> {
    id = "travel"
    getTargets = { input ->
        val regionId = input.getRegion(input.entityId!!)
        val neighbors = input.getNeighbors(regionId)
        val wayfareLocations = ArrayList<UUID>()
        for (connectionId in neighbors) {
            wayfareLocations.add(connectionId)
        }
        wayfareLocations
    }
    targetAction = Ids.TRAVEL_TO
}.register()
