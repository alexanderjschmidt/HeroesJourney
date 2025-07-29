package heroes.journey.tilemap.wavefunctiontiles;

import static heroes.journey.mods.Registries.TerrainManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.GameCamera;
import heroes.journey.modlib.worldgen.Terrain;

public class BaseTile extends Tile {

    private final TextureRegion texture;

    public BaseTile(Terrain terrain, int weight, boolean addToDefaultTiles, TextureRegion texture) {
        super(terrain, weight, addToDefaultTiles);
        this.texture = texture;
    }

    public BaseTile(String terrain, int weight, boolean addToDefaultTiles, TextureRegion texture) {
        this(TerrainManager.get(terrain), weight, addToDefaultTiles, texture);
    }

    public BaseTile(Terrain terrain, int weight, TextureRegion texture) {
        super(terrain, weight, true);
        this.texture = texture;
    }

    public BaseTile(String terrain, int weight, TextureRegion texture) {
        this(TerrainManager.get(terrain), weight, texture);
    }

    public void render(SpriteBatch batch, float elapsed, int x, int y) {
        batch.draw(texture, x * GameCamera.get().getSize(), y * GameCamera.get().getSize(),
            GameCamera.get().getSize(), GameCamera.get().getSize());
    }
}
