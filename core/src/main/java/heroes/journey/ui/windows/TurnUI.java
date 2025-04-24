package heroes.journey.ui.windows;

import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.GameState;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.ui.UI;

public class TurnUI extends UI {

    public TurnUI() {
        super();
    }

    @Override
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        GameState gameState = GameState.global();
        PlayerComponent playerComponent = PlayerComponent.get(gameState.getWorld(),
            gameState.getCurrentEntity());
        String currentEntity = playerComponent == null ? "Opponent" : "Player";

        drawText(batch, "Day " + gameState.getTurn() + " " + currentEntity, 0, 0);
    }

}
