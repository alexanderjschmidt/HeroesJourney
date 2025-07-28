import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.StringResult
import heroes.journey.modlib.actions.action
import java.util.*

// Delve Actions - included by basegame mod

// Delve Action
action {
    id = Ids.DELVE
    turnCooldown = 5
    factionCooldown = true
    onSelectFn = { input ->
        val e = input.entityId
        val dungeon = UUID.fromString(input["owner"])
        val log = StringBuilder()
        log.append("You have completed the ")
            .append(input.getName(dungeon))
            .append("!\nYour rewards are:\n")

        val inventory = input.getInventory(dungeon)
        if (inventory != null) {
            for (item in inventory.keys) {
                input.addItem(e!!, item, inventory[item]!!)
                log.append(inventory[item])
                    .append("x ")
                    .append(item)
                    .append("\n")
            }
        }
        input.adjustStat(e!!, Ids.STAT_FAME, 5)
        log.append("You have gained ").append(5).append(" fame")

        StringResult(log.toString())
    }
}.register()
