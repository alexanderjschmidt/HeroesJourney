package heroes.journey.initializers.base.actions;

import heroes.journey.Application;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.components.place.DungeonComponent;
import heroes.journey.components.utils.DefaultContainer;
import heroes.journey.components.utils.FightUtils;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.EndTurnResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.entities.items.Item;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.ui.HUD;
import heroes.journey.ui.screens.MainMenuScreen;
import heroes.journey.ui.windows.ActionMenu;

import java.util.List;
import java.util.UUID;

public class BaseActions implements InitializerInterface {

    public static Action openActionMenu, wait, end_turn, exit_game, attack;
    public static CooldownAction workout, study;
    public static CooldownAction delve;
    public static Action questBoard;

    static {
        openActionMenu = Action.builder()
            .name("THIS SHOULD NEVER BE DISPLAYED")
            .returnsActionList(true)
            .requirementsMet((gs, e) -> ShowAction.NO)
            .onSelect((gs, e) -> new ActionListResult(ActionMenu.getActionsFor(gs, e)))
            .build()
            .register();
        exit_game = Action.builder().name("Exit Game").onSelect((gs, e) -> {
            Application.get().setScreen(new MainMenuScreen(Application.get()));
            return null;
        }).build().register();
        TeamActions.addTeamAction(exit_game);
        end_turn = Action.builder().name("End Turn").onSelect((gs, e) -> {
            UUID entityId = gs.getCurrentEntity();
            gs.getWorld().edit(entityId).create(ActionComponent.class).action(BaseActions.wait);
            HUD.get().revertToInitialState();
            return null;
        }).build().register();
        TeamActions.addTeamAction(end_turn);

        wait = Action.builder()
            .name("Wait")
            .description("Do Nothing")
            .onSelect((gs, e) -> new EndTurnResult())
            .build()
            .register();
        workout = CooldownAction.builder()
            .name("Work out")
            .description("Lift weights, gain body")
            .turnCooldown(1)
            .onSelect((gs, e) -> Utils.adjustBody(gs, e, 1))
            .build()
            .register();
        study = CooldownAction.builder()
            .name("Study")
            .description("Expand your mind, increasing your potential")
            .turnCooldown(2)
            .onSelect(((gs, e) -> Utils.adjustMind(gs, e, 1)))
            .build()
            .register();
        delve = CooldownAction.builder()
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
        questBoard = Action.builder()
            .name("Quest Board")
            .description("See what the people need help with")
            .returnsActionList(true)
            .onSelect((gs, e) -> {
                UUID town = Utils.getLocation(gs, e);
                List<Action> questActions = Utils.getQuestClaimActions(gs, town);
                return new ActionListResult(questActions);
            })
            .requirementsMet((gs, e) -> {
                UUID town = Utils.getLocation(gs, e);
                QuestsComponent questsComponent = QuestsComponent.get(gs.getWorld(), town);
                return questsComponent.getQuests().isEmpty() ? ShowAction.GRAYED : ShowAction.YES;
            })
            .build()
            .register();
    }

}
