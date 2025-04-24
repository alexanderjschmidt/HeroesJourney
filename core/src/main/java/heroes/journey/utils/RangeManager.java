package heroes.journey.utils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.ui.HUD;
import heroes.journey.utils.art.ResourceManager;
import lombok.Getter;

public class RangeManager {

    public enum RangeColor {
        NONE, BLUE, RED, GREEN, PURPLE, TEAL, YELLOW;
    }

    private GameState gameState;
    private int width, height;
    @Getter private RangeColor[][] range;
    private ArrayList<Integer> targets;
    private int target;

    public RangeManager(GameState gameState, int width, int height) {
        this.gameState = gameState;
        this.width = width;
        this.height = height;
        clearRange();
    }

    // This shouldnt be necessary since this is a purely visual class
    public RangeManager clone(GameState newGameState) {
        return new RangeManager(newGameState, width, height);
    }

    public ArrayList<Integer> updateTargets(
        Integer selectedId,
        boolean enemies,
        int[] ranges,
        RangeColor rangeType) {
        clearRange();
        PositionComponent position = PositionComponent.get(GameState.global().getWorld(), selectedId);
        setDistanceRangeAt(position.getX(), position.getY(), ranges, rangeType);
        targets = new ArrayList<Integer>();
        target = 0;
        for (int x = 0; x < range.length; x++) {
            for (int y = 0; y < range[0].length; y++) {
                // System.out.print(range[x][y]);
                if (range[x][y] == rangeType) {
                    Integer e = gameState.getEntities().get(x, y);
                    if (e != null) {
                        targets.add(e);
                    }
                }
            }
            // System.out.println();
        }
        return targets;
    }

    public void pointAtTarget(int increment) {
        target = (target + increment + targets.size()) % targets.size();
        PositionComponent position = PositionComponent.get(GameState.global().getWorld(),
            targets.get(target));
        HUD.get().getCursor().setPosition(position.getX(), position.getY());
    }

    public void setMoveAndAttackRange(Integer selectedId, int x, int y) {
        if (selectedId == null) {
            return;
        }
        clearRange();
        StatsComponent stats = StatsComponent.get(GameState.global().getWorld(), selectedId);
        int move = stats.getMoveDistance();
        floodfill(move, x, y, selectedId);
    }

    public void setMoveAndAttackRange(Integer selectedId) {
        PositionComponent position = PositionComponent.get(GameState.global().getWorld(), selectedId);
        setMoveAndAttackRange(selectedId, position.getX(), position.getY());
    }

    public void clearRange() {
        range = new RangeColor[width][height];
        target = 0;
    }

    private void floodfill(int dist, int x, int y, Integer selectedId) {
        if (x < 0 || y < 0 || x >= range.length || y >= range[0].length) {
            // System.out.println("out of bounds");
            return;
        }
        if ((dist < 0 || gameState.getEntities().get(x, y) != null) &&
            gameState.getEntities().get(x, y) != selectedId) {
            return;
        }
        range[x][y] = RangeColor.BLUE;
        setDistanceRangeAt(x, y, new int[] {1, 2}, RangeColor.RED);

        // System.out.println(terrainCost);
        floodfill(dist - gameState.getMap().getTerrainCost(x + 1, y, selectedId), x + 1, y, selectedId);
        floodfill(dist - gameState.getMap().getTerrainCost(x - 1, y, selectedId), x - 1, y, selectedId);
        floodfill(dist - gameState.getMap().getTerrainCost(x, y + 1, selectedId), x, y + 1, selectedId);
        floodfill(dist - gameState.getMap().getTerrainCost(x, y - 1, selectedId), x, y - 1, selectedId);

    }

    public void setDistanceRangeAt(int x, int y, int[] ranges, RangeColor type) {
        for (int r : ranges) {
            for (int i = 0; i < r; i++) {
                int j = r - i;
                if (x + i < range.length && y + j < range[0].length && isEmpty(x + i, y + j))
                    range[x + i][y + j] = type;
                if (x - j >= 0 && y + i < range[0].length && isEmpty(x - j, y + i))
                    range[x - j][y + i] = type;
                if (x + j < range.length && y - i >= 0 && isEmpty(x + j, y - i))
                    range[x + j][y - i] = type;
                if (x - i >= 0 && y - j >= 0 && isEmpty(x - i, y - j))
                    range[x - i][y - j] = type;
            }
        }
    }

    public void render(Batch batch, int turn) {
        if (range == null) {
            System.out.println("Range null");
            return;
        }
        for (int y = 0; y < range[0].length; y++) {
            for (int x = 0; x < range.length; x++) {
                if (range[x][y] == RangeColor.YELLOW)
                    batch.draw(ResourceManager.get(LoadTextures.UI)[3][1], x * GameCamera.get().getSize(),
                        y * GameCamera.get().getSize(), GameCamera.get().getSize(),
                        GameCamera.get().getSize());
                if (range[x][y] == RangeColor.TEAL)
                    batch.draw(ResourceManager.get(LoadTextures.UI)[4][1], x * GameCamera.get().getSize(),
                        y * GameCamera.get().getSize(), GameCamera.get().getSize(),
                        GameCamera.get().getSize());
                if (range[x][y] == RangeColor.PURPLE)
                    batch.draw(ResourceManager.get(LoadTextures.UI)[4][0], x * GameCamera.get().getSize(),
                        y * GameCamera.get().getSize(), GameCamera.get().getSize(),
                        GameCamera.get().getSize());
                if (range[x][y] == RangeColor.GREEN)// green
                    batch.draw(ResourceManager.get(LoadTextures.UI)[3][0], x * GameCamera.get().getSize(),
                        y * GameCamera.get().getSize(), GameCamera.get().getSize(),
                        GameCamera.get().getSize());
                if (range[x][y] == RangeColor.RED)// red
                    batch.draw(ResourceManager.get(LoadTextures.UI)[2][0], x * GameCamera.get().getSize(),
                        y * GameCamera.get().getSize(), GameCamera.get().getSize(),
                        GameCamera.get().getSize());
                if (range[x][y] == RangeColor.BLUE)// blue
                    batch.draw(ResourceManager.get(LoadTextures.UI)[2][1], x * GameCamera.get().getSize(),
                        y * GameCamera.get().getSize(), GameCamera.get().getSize(),
                        GameCamera.get().getSize());
            }
        }
    }

    public boolean isEmpty(int x, int y) {
        return range[x][y] == null || range[x][y] == RangeColor.NONE;
    }

}
