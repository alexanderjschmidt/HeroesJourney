package heroes.journey.ui.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.input.KeyManager;
import heroes.journey.utils.worldgen.dungeon.DungeonGenerator;

public class DelveUI extends UI {

    private int[][] map;
    public int tileSize = 16;

    // TODO set the tileSize based on the map size (larger map gets shrunk more)
    public DelveUI() {
        init();
    }

    public void init() {
        DungeonGenerator generator = new DungeonGenerator(5, 5, 3, 7);
        map = generator.generateDungeon(5);
    }

    @Override
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        if (Gdx.input.isKeyJustPressed(KeyManager.ANY)) {
            init();
        }
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 1) {
                    // Flip y-axis for libGDX coordinates (optional)
                    batch.draw(ResourceManager.get(LoadTextures.UI)[3][0], getX() + (x * tileSize),
                        getY() + ((map.length - y - 1) * tileSize), tileSize, tileSize);
                } else {
                    batch.draw(ResourceManager.get(LoadTextures.UI)[4][0], getX() + (x * tileSize),
                        getY() + ((map.length - y - 1) * tileSize), tileSize, tileSize);
                }
            }
        }
    }
}
