package heroes.journey.initializers.base.actions;

import heroes.journey.components.InventoryComponent;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.components.place.DungeonComponent;
import heroes.journey.components.utils.DefaultContainer;
import heroes.journey.components.utils.FightUtils;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.entities.items.Item;
import heroes.journey.initializers.InitializerInterface;

import java.util.UUID;

public class DelveAction implements InitializerInterface {
    public static CooldownAction delve;

    static {
        DelveAction.delve = CooldownAction.builder()
            .name("Delve")
            .turnCooldown(5)
            .factionCooldown(true)
            .onSelect((gs, e) -> {
                UUID dungeon = Utils.getLocation(gs, e);
                DungeonComponent dungeonComponent = DungeonComponent.get(gs.getWorld(), dungeon);
                DefaultContainer<String> explorationLog = new DefaultContainer<>();
                boolean conscious = true;
                for (UUID room : dungeonComponent.layout()) {
                    if (room == null) {
                        explorationLog.add("Empty");
                        continue;
                    }
                    if (FightUtils.fight(gs, e, room)) {
                        explorationLog.add(NamedComponent.get(gs.getWorld(), room, "Enemy"));
                    } else {
                        conscious = false;
                        FightUtils.faint(gs.getWorld(), e);
                        break;
                    }
                }

                StringBuilder log = new StringBuilder();

                for (String enemy : explorationLog.keySet()) {
                    log.append("You have defeated: ")
                        .append(explorationLog.get(enemy))
                        .append("x ")
                        .append(enemy)
                        .append("\n");
                }

                if (!conscious) {
                    log.append("You have lost too much health and fainted");
                } else {
                    InventoryComponent inventoryComponent = InventoryComponent.get(gs.getWorld(), dungeon);
                    log.append("You have completed the Dungeon!\nYour rewards are:\n");
                    if (inventoryComponent != null) {
                        for (Item item : inventoryComponent.getInventory().keySet()) {
                            Utils.addItem(gs, e, item, inventoryComponent.getInventory().get(item));
                            log.append(inventoryComponent.getInventory().get(item))
                                .append("x ")
                                .append(item.toString())
                                .append("\n");
                            ;
                        }
                    }
                    PlayerComponent playerComponent = PlayerComponent.get(gs.getWorld(), e);
                    if (playerComponent != null) {
                        playerComponent.fame(playerComponent.fame() + 5);
                        log.append("You have gained ").append(5).append(" fame");
                    }
                }
                return new StringResult(log.toString());
            })
            .build()
            .register();
    }
}
