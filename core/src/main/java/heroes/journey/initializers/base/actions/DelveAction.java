package heroes.journey.initializers.base.actions;

import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.components.place.DungeonComponent;
import heroes.journey.components.utils.DefaultContainer;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.entities.items.Item;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.utils.FightUtils;
import heroes.journey.initializers.utils.Utils;

import java.util.UUID;

public class DelveAction implements InitializerInterface {
    public static CooldownAction delve;

    @Override
    public void init() {
        DelveAction.delve = new CooldownAction("Delve", "Delve", "Explore a dungeon", false, null, 5, true) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                GameState gs = input.getGameState();
                UUID e = input.getEntityId();
                UUID dungeon = Utils.getLocation(input);
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
                            Utils.addItem(input, item, inventoryComponent.getInventory().get(item));
                            log.append(inventoryComponent.getInventory().get(item))
                                .append("x ")
                                .append(item.toString())
                                .append("\n");
                        }
                    }
                    PlayerComponent playerComponent = PlayerComponent.get(gs.getWorld(), e);
                    if (playerComponent != null) {
                        playerComponent.fame(playerComponent.fame() + 5);
                        log.append("You have gained ").append(5).append(" fame");
                    }
                }
                return new StringResult(log.toString());
            }
        };
    }
}
