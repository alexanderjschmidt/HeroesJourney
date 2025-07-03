package heroes.journey.initializers.base;

import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.art.ResourceManager;

public class LoadTextures implements InitializerInterface {

    public static final String PLAYER_SPRITE = "player";
    public static final String CAPITAL_SPRITE = "capital";
    public static final String TOWN_SPRITE = "town";
    public static final String DUNGEON_SPRITE = "dungeon";

    @Override
    public void init() {
        ResourceManager.register(PLAYER_SPRITE, ResourceManager.get(ResourceManager.Sprites)[1][1]);
        ResourceManager.register(CAPITAL_SPRITE,
            ResourceManager.get(ResourceManager.OverworldTileset)[9][14]);
        ResourceManager.register(TOWN_SPRITE, ResourceManager.get(ResourceManager.OverworldTileset)[7][12]);
        ResourceManager.register(DUNGEON_SPRITE,
            ResourceManager.get(ResourceManager.OverworldTileset)[17][4]);
    }
}
