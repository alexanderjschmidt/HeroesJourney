package heroes.journey.initializers.base.actions

import heroes.journey.GameState
import heroes.journey.components.InventoryComponent
import heroes.journey.components.NamedComponent
import heroes.journey.components.character.PlayerComponent
import heroes.journey.entities.actions.CooldownAction
import heroes.journey.entities.actions.cooldownAction
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.initializers.InitializerInterface
import heroes.journey.initializers.base.tags.Stats
import heroes.journey.initializers.utils.FightUtils
import heroes.journey.initializers.utils.Utils
import java.util.*

class DelveAction : InitializerInterface {
    override fun init() {
        delve = cooldownAction {
            id = "delve"
            name = "Delve"
            description = "Explore a dungeon"
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
                if (FightUtils.struggle(gs, e, dungeon, Stats.BODY)) {
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
                    val playerComponent = PlayerComponent.get(gs.world, e)
                    if (playerComponent != null) {
                        playerComponent.fame(playerComponent.fame() + 5)
                        log.append("You have gained ").append(5).append(" fame")
                    }
                } else {
                    log.append("You have lost too much health and fainted")
                    FightUtils.faint(gs.world, e)
                }

                StringResult(log.toString())
            }
        }.register()
    }

    companion object {
        @JvmField
        var delve: CooldownAction? = null
    }
}
