package heroes.journey.ui.windows;

import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.entities.actions.ActionContext;
import heroes.journey.modlib.actions.Action;
import heroes.journey.modlib.actions.ActionEntry;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.UI;
import heroes.journey.ui.infoproviders.ActionInfoProvider;

import java.util.List;
import java.util.UUID;

public class ActionMenu extends UI {

    private final ScrollPane<ActionEntry> actions;

    public ActionMenu(InfoUI infoUI) {
        super();
        this.setVisible(false);
        actions = new ActionScrollPane();
        mainTable.add(actions).expandX().fillX().row();
        mainTable.add().expandY().row();

        pack();
    }

    public void open(List<ScrollPaneEntry<ActionEntry>> options) {
        actions.open(options);
    }

    public void handleInputs() {
        actions.handleInput();
    }

    private class ActionScrollPane extends ScrollPane<ActionEntry> {

        @Override
        public void open(List<ScrollPaneEntry<ActionEntry>> options) {
            if (options.isEmpty()) {
                System.out.println("Options Empty");
                HUD.get().getCursor().clearSelected();
                HUD.get().revertToPreviousState();
                return;
            }
            super.open(options);
        }

        @Override
        public String getText(ActionEntry option) {
            return heroes.journey.modlib.registries.Registries.INSTANCE.getActionManager().get(option.getActionId())
                .getTitle(new ActionContext(GameState.global(), GameState.global().getCurrentEntity(), false,
                    option.getInput()));
        }

        @Override
        public void select() {
            ActionEntry action = getSelected().entry();
            UUID selectedEntity = HUD.get().getCursor().getSelected();
            System.out.println(
                "Selected " + action.getActionId() + " " + selectedEntity + " " + action.getInput());
            if (selectedEntity == null) {
                selectedEntity = PlayerInfo.get().getPlayersHero();
            }
            GameState.global().getWorld().edit(selectedEntity).create(ActionComponent.class).action(action);
        }

        @Override
        public void onHover() {
            ActionEntry actionEntry = actions.getSelected().entry();
            ActionContext input = new ActionContext(GameState.global(), HUD.get().getCursor().getSelected(),
                false, actionEntry.getInput());
            Action action = heroes.journey.modlib.registries.Registries.INSTANCE.getActionManager().get(actionEntry.getActionId());
            heroes.journey.entities.actions.ActionUtils.onHover(action, input);
            HUD.get().getActionDetailedUI().showInfo(new ActionInfoProvider(action), actionEntry.getInput());
        }
    }
}
