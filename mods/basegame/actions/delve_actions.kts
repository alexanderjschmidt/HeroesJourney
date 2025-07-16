import heroes.journey.GameState
import heroes.journey.components.InventoryComponent
import heroes.journey.components.NamedComponent
import heroes.journey.components.StatsComponent
import heroes.journey.entities.actions.cooldownAction
import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.results.StringResult
import heroes.journey.registries.Registries.StatManager
import heroes.journey.utils.gamestate.FightUtils
import heroes.journey.utils.gamestate.Utils
import java.util.*

// Delve Actions - included by basegame mod

// Delve Action
cooldownAction {
    id = "delve"
    inputDisplayNameFn = { input ->
        val gs: GameState = GameState.global()
        val factionId: UUID = Utils.getLocation(GameState.global(), UUID.fromString(input["owner"]))
        "Delve " + NamedComponent.get(gs.world, factionId, "Unknown")
    }
    turnCooldown = 5
    factionCooldown = true
    onSelectFn = { input ->
        val gs = input.gameState
        val e = input.entityId
        val dungeon = UUID.fromString(input["owner"])
        val log = StringBuilder()
        if (FightUtils.struggle(gs, e, dungeon, StatManager[Ids.STAT_BODY])) {
            log.append("You have completed the ")
                .append(NamedComponent.get(gs.world, dungeon, "Dungeon"))
                .append("!\nYour rewards are:\n")

            val inventoryComponent = InventoryComponent.get(gs.world, dungeon)
            if (inventoryComponent != null) {
                for (item in inventoryComponent.inventory.keys) {
                    Utils.addItem(
                        input, item,
                        inventoryComponent.inventory[item]!!
                    )
                    log.append(inventoryComponent.inventory[item])
                        .append("x ")
                        .append(item.id)
                        .append("\n")
                }
            }
            StatsComponent.addFame(
                gs.getWorld(),
                e,
                5
            )
            log.append("You have gained ").append(5).append(" fame")
        } else {
            log.append("You have lost too much health and fainted")
            FightUtils.faint(gs.world, e)
        }

        StringResult(log.toString())
    }
}.register()
