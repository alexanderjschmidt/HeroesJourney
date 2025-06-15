package heroes.journey.initializers.base;

import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.art.ResourceManager;

public class LoadTextures implements InitializerInterface {

    public static final String PLAYER_SPRITE = "player";

    @Override
    public void init() {
        ResourceManager.register(PLAYER_SPRITE, ResourceManager.get(ResourceManager.Sprites)[1][1]);
    }
}
