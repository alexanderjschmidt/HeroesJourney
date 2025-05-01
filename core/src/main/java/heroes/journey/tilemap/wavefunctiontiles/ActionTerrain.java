package heroes.journey.tilemap.wavefunctiontiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import heroes.journey.entities.actions.Action;

public class ActionTerrain extends Terrain {

    private final List<Action> actions;

    public ActionTerrain(String name, int terrainCost, Action... actions) {
        super(name, terrainCost);
        this.actions = new ArrayList<>(Arrays.asList(actions));
    }

    public List<Action> getActions() {
        return actions;
    }

}
