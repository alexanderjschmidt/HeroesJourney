package heroes.journey.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import heroes.journey.GameState;
import heroes.journey.components.PlayerComponent;

public class TurnUI extends UI {

    public TurnUI() {
        super(0, 0, 8, 1, true, false);
    }

    public void update() {

    }

    @Override
    public void drawUI(Batch batch, float parentAlpha) {
        GameState gameState = GameState.global();
        PlayerComponent playerComponent = PlayerComponent.get(gameState.getCurrentEntity());
        String currentEntity = playerComponent == null ? "Opponent" : "Player";

        drawText(batch, "Day " + gameState.getTurn() + " " + currentEntity, 0, 0);
    }

}
