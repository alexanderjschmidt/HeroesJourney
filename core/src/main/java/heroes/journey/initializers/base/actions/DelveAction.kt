package heroes.journey.initializers.base.actions

import heroes.journey.components.InventoryComponent
import heroes.journey.components.NamedComponent
import heroes.journey.components.character.PlayerComponent
import heroes.journey.components.place.DungeonComponent
import heroes.journey.components.utils.DefaultContainer
import heroes.journey.entities.actions.CooldownAction
import heroes.journey.entities.actions.cooldownAction
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.initializers.InitializerInterface
import heroes.journey.initializers.utils.FightUtils
import heroes.journey.initializers.utils.Utils

class DelveAction : InitializerInterface {
    override fun init() {
        delve = cooldownAction {
            id = "delve"
            name = "Delve"
            description = "Explore a dungeon"
            turnCooldown = 5
            factionCooldown = true
            onSelectFn = { input ->
                val gs = input.gameState
                val e = input.entityId
                val dungeon = Utils.getLocation(input)
                val dungeonComponent = DungeonComponent.get(gs.world, dungeon)
                val explorationLog = DefaultContainer<String>()
                var conscious = true
                for (room in dungeonComponent.layout()) {
                    if (room == null) {
                        explorationLog.add("Empty")
                        continue
                    }
                    if (FightUtils.fight(gs, e, room)) {
                        explorationLog.add(NamedComponent.get(gs.world, room, "Enemy"))
                    } else {
                        conscious = false
                        FightUtils.faint(gs.world, e)
                        break
                    }
                }

                val log = StringBuilder()

                for (enemy in explorationLog.keys) {
                    log.append("You have defeated: ")
                        .append(explorationLog[enemy])
                        .append("x ")
                        .append(enemy)
                        .append("\n")
                }

                if (!conscious) {
                    log.append("You have lost too much health and fainted")
                } else {
                    val inventoryComponent = InventoryComponent.get(gs.world, dungeon)
                    log.append("You have completed the Dungeon!\nYour rewards are:\n")
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
