import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.StringResult
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.targetAction
import heroes.journey.modlib.attributes.attributes
import java.util.*

// Travel Actions - included by basegame mod

// Go Action
action {
    id = Ids.TRAVEL_TO
    customInfoProviderFn = { input ->
        input.getInfoProvider(UUID.fromString(input["target"]))
    }
    onHoverFn = { input ->
        val locationId = UUID.fromString(input["target"])
        val targetPos = input.getPosition(locationId)
        input.setMapPointer(targetPos)
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
    id = Ids.TRAVEL
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
    cost = attributes {
        stat(Ids.STAT_STAMINA, 40)
    }
}.register()
