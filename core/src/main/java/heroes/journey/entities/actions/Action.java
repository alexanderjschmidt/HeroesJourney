package heroes.journey.entities.actions;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.AIOnSelectNotFound;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.ui.HUD;
import heroes.journey.ui.windows.InfoProvider;
import lombok.Getter;
import lombok.NonNull;

import static heroes.journey.registries.Registries.ActionManager;

public abstract class Action implements InfoProvider {
    @NonNull
    @Getter
    protected final String name;
    protected final String displayName;
    @Getter
    protected final String description;
    @Getter
    protected final boolean returnsActionList;

    protected final Cost cost;

    public Action(String name, String displayName, String description, boolean returnsActionList, Cost cost) {
        this.name = name;
        this.displayName = displayName;
        this.description = description != null ? description : "";
        this.returnsActionList = returnsActionList;
        this.cost = cost != null ? cost : new Cost();
    }

    public Action(String name) {
        this(name, name, "", false, null);
    }

    public ShowAction internalRequirementsMet(ActionInput input) {
        return ShowAction.YES;
    }

    public void internalOnHover(ActionInput input) {
    }

    public abstract ActionResult internalOnSelect(ActionInput input);

    public ActionResult internalOnSelectAI(ActionInput input) {
        return new AIOnSelectNotFound();
    }

    public ShowAction requirementsMet(ActionInput input) {
        return internalRequirementsMet(input).and(cost.requirementsMet(input));
    }

    public void onHover(ActionInput input) {
        HUD.get().getCursor().setMapPointerLoc(null);
        internalOnHover(input);
    }

    public ActionResult onSelect(ActionInput input, boolean ai) {
        cost.onUse(input);
        if (ai) {
            ActionResult aiResult = internalOnSelectAI(input);
            if (!(aiResult instanceof AIOnSelectNotFound)) {
                return aiResult;
            }
        }
        return internalOnSelect(input);
    }

    public ActionResult onSelect(ActionInput input) {
        return onSelect(input, false);
    }

    @Override
    public String toString() {
        return displayName == null ? name : displayName;
    }

    public Action register() {
        return ActionManager.register(this);
    }

    @Override
    public String getTitle() {
        return toString();
    }

    @Override
    public void fillCustomContent(Table table, Skin skin) {
        table.add(cost.getDisplay())
            .center()
            .fill()
            .expand();
    }
}
