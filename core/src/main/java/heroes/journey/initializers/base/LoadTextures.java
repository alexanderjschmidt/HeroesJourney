package heroes.journey.initializers.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.art.AnimationRenderable;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.art.StillRenderable;

public class LoadTextures implements InitializerInterface {

    public static final String PLAYER_SPRITE = "player";
    public static final String CAPITAL_SPRITE = "capital", TOWN_SPRITE = "town", DUNGEON_SPRITE = "dungeon";

    public static final String LIGHT_FOG = "light_fog", DENSE_FOG = "dense_fog";

    public static final String RED = "red", LIGHT_BLUE = "light_blue", PURPLE = "purple", YELLOW = "yellow";

    public static final String CURSOR = "cursor", MAP_POINTER = "map_pointer";

    @Override
    public void init() {
        new StillRenderable(PLAYER_SPRITE, ResourceManager.Sprites, 1, 1).register();

        new StillRenderable(CAPITAL_SPRITE, ResourceManager.OverworldTileset, 9, 14).register();
        new StillRenderable(TOWN_SPRITE, ResourceManager.OverworldTileset, 7, 12).register();
        new StillRenderable(DUNGEON_SPRITE, ResourceManager.OverworldTileset, 17, 4).register();

        new StillRenderable(LIGHT_FOG, ResourceManager.Sprites, 0, 1).register();
        new StillRenderable(DENSE_FOG, ResourceManager.Sprites, 0, 0).register();

        new StillRenderable(RED, ResourceManager.UI, 2, 0).register();
        new StillRenderable(LIGHT_BLUE, ResourceManager.UI, 4, 1).register();
        new StillRenderable(PURPLE, ResourceManager.UI, 4, 0).register();
        new StillRenderable(YELLOW, ResourceManager.UI, 3, 1).register();

        TextureRegion[] frames = {ResourceManager.get(ResourceManager.UI)[0][0],
            ResourceManager.get(ResourceManager.UI)[0][0], ResourceManager.get(ResourceManager.UI)[1][0]};
        Animation<TextureRegion> ani = new Animation<>(.5f, new Array<>(frames), Animation.PlayMode.LOOP);
        new AnimationRenderable(CURSOR, ani).register();
        TextureRegion[] framesPointer = {ResourceManager.get(ResourceManager.UI)[3][3],
            ResourceManager.get(ResourceManager.UI)[3][3], ResourceManager.get(ResourceManager.UI)[4][3]};
        Animation<TextureRegion> mapPointer = new Animation<>(.5f, new Array<>(framesPointer), Animation.PlayMode.LOOP);
        new AnimationRenderable(MAP_POINTER, mapPointer).register();
    }
}
