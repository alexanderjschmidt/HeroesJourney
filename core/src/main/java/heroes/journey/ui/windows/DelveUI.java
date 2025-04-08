package heroes.journey.ui.windows;

import static heroes.journey.utils.worldgen.BSPDungeon.generateDungeon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import heroes.journey.ui.UI;

public class DelveUI extends UI {

    private int[][] map = generateDungeon();
    ShapeRenderer shapeRenderer;
    public int tileSize = 8;

    public DelveUI() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 1) {
                    // Flip y-axis for libGDX coordinates (optional)
                    shapeRenderer.rect(x * tileSize, (map.length - y - 1) * tileSize, tileSize, tileSize);
                }
            }
        }

        shapeRenderer.end();
    }
}
