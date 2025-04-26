package heroes.journey.initializers.base.actions;

import heroes.journey.Application;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.character.NamedComponent;
import heroes.journey.components.place.DungeonComponent;
import heroes.journey.components.utils.DefaultContainer;
import heroes.journey.components.utils.FightUtils;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.entities.items.Item;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.Items;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.ui.screens.MainMenuScreen;

import java.util.List;

public class BaseActions implements InitializerInterface {

    public static Action openActionMenu, wait, end_turn, exit_game, attack;
    public static CooldownAction workout, study;
    public static CooldownAction delve;
    public static Action chopTrees;
    public static Action questBoard;

    static {
        openActionMenu = Action.builder()
            .name("THIS SHOULD NEVER BE DISPLAYED")
            .terminal(false)
            .requirementsMet((gs, e) -> ShowAction.NO)
            .onSelect((gs, e) -> {
                HUD.get().getActionMenu().open();
                return null;
            })
            .build()
            .register();
        exit_game = Action.builder().name("Exit Game").terminal(false).onSelect((gs, e) -> {
            Application.get().setScreen(new MainMenuScreen(Application.get()));
            return null;
        }).build().register();
        TeamActions.addTeamAction(exit_game);
        end_turn = Action.builder().name("End Turn").terminal(false).onSelect((gs, e) -> {
            Integer entityId = gs.getCurrentEntity();
            gs.getWorld().edit(entityId).create(ActionComponent.class).action(BaseActions.wait);
            HUD.get().revertToInitialState();
            return null;
        }).build().register();
        TeamActions.addTeamAction(end_turn);

        wait = Action.builder().name("Wait").build().register();
        workout = CooldownAction.builder()
            .name("Work out")
            .turnCooldown(1)
            .onSelect((gs, e) -> Utils.adjustBody(gs, e, 1))
            .build()
            .register();
        study = CooldownAction.builder()
            .name("Study")
            .turnCooldown(2)
            .onSelect(((gs, e) -> Utils.adjustMind(gs, e, 1)))
            .build()
            .register();
        delve = CooldownAction.builder()
            .name("Delve")
            .turnCooldown(5)
            .factionCooldown(true)
            .onSelect((gs, e) -> {
                Integer dungeon = Utils.getLocation(gs, e);
                DungeonComponent dungeonComponent = DungeonComponent.get(gs.getWorld(), dungeon);
                DefaultContainer<String> explorationLog = new DefaultContainer<>();
                boolean conscious = true;
                for (Integer room : dungeonComponent.layout()) {
                    if (room == null) {
                        explorationLog.add("Empty");
                        continue;
                    }
                    if (FightUtils.fight(gs.getWorld(), e, room)) {
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
                }
                return log.toString();
            })
            .build()
            .register();
        chopTrees = Action.builder()
            .name("Chop Trees")
            .onSelect((gs, e) -> Utils.addItem(gs, e, Items.wood, 1))
            .build()
            .register();
        questBoard = Action.builder().name("Quest Board").terminal(false).onSelect((gs, e) -> {
            Integer town = Utils.getLocation(gs, e);
            List<Action> questActions = Utils.getQuestClaimActions(gs, town);
            List<ScrollPaneEntry<Action>> options = Utils.convertToScrollEntries(questActions);
            HUD.get().setState(new ActionSelectState(options));
            return null;
        }).requirementsMet((gs, e) -> {
            Integer town = Utils.getLocation(gs, e);
            QuestsComponent questsComponent = QuestsComponent.get(gs.getWorld(), town);
            return questsComponent.getQuests().isEmpty() ? ShowAction.GRAYED : ShowAction.YES;
        }).build().register();
    }

}
