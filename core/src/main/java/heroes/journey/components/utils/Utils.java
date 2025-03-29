package heroes.journey.components.utils;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;

public class Utils {

    public static Entity getLocationsFaction(GameState gameState, Entity entity) {
        if (entity == null)
            return null;
        PositionComponent positionComponent = PositionComponent.get(entity);
        if (positionComponent == null)
            return null;
        return gameState.getEntities().getFaction(positionComponent.getX(), positionComponent.getY());
    }

}
