package heroes.journey.initializers.base;

import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.art.TextureMap;

public class LoadTextures implements InitializerInterface {

    public static final TextureMap UI = new TextureMap("UI/cursor.png", 32, 32);
    public static final TextureMap Sprites = new TextureMap("sprites.png", 16, 16);
    public static final TextureMap OverworldTileset = new TextureMap("Overworld_Tileset.png", 16,
        16);
    public static final TextureMap WangCornerTileLayout = new TextureMap("wangcorner.png", 3,
        3);

    public static final String PLAYER_SPRITE = "player";

    @Override
    public void init() {
        ResourceManager.register(PLAYER_SPRITE, ResourceManager.get(LoadTextures.Sprites)[1][1]);
    }
}
