package heroes.journey.initializers.base;

import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.art.TextureMap;

public class LoadTextures implements InitializerInterface {

    public static final TextureMap UI = new TextureMap("Textures/UI/cursor.png", 32, 32);
    public static final TextureMap Sprites = new TextureMap("Textures/sprites.png", 16, 16);
    public static final TextureMap OverworldTileset = new TextureMap("Textures/Overworld_Tileset.png", 16,
        16);

    public static final String PLAYER_SPRITE = "player";

    static {
        ResourceManager.register(PLAYER_SPRITE, ResourceManager.get(LoadTextures.Sprites)[1][1]);
    }
}
