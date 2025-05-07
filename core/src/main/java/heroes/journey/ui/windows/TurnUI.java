package heroes.journey.ui.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import heroes.journey.GameState;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

public class TurnUI extends UI {

    private final Label turn;

    public TurnUI() {
        super();
        this.turn = new Label("", ResourceManager.get().skin);
        this.turn.setWrap(true);
        this.mainTable.add(turn).expand().row();

        pack();
    }

    @Override
    public void update() {
        GameState gameState = GameState.global();
        PlayerComponent playerComponent = PlayerComponent.get(gameState.getWorld(),
            gameState.getCurrentEntity());
        String currentEntity = playerComponent == null ? "Opponent" : "Player";

        turn.setText("Day " + gameState.getTurn() + " " + currentEntity);
    }

}
