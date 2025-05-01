package heroes.journey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import heroes.journey.initializers.base.Map;
import heroes.journey.ui.Cursor;
import heroes.journey.ui.HUD;
import lombok.Getter;

@Getter
public class GameCamera extends OrthographicCamera {

    private int size = 32;
    private int xLow, xHigh, yLow, yHigh;

    private static GameCamera camera;

    public static synchronized GameCamera get() {
        if (camera == null) {
            camera = new GameCamera();
        }
        return camera;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private GameCamera() {
        super();
    }

    public void updateGameCamera() {
        super.update();
        Cursor cursor = HUD.get().getCursor();
        if (cursor != null) {
            //Centered
            int cameraX = (int)(position.x / size);
            int cameraY = (int)(position.y / size);

            // Viewport size in tiles
            int tilesWide = Gdx.graphics.getWidth() / size;
            int tilesHigh = Gdx.graphics.getHeight() / size;

            // Outer thirds (in tile space, relative to camera center)
            int leftBound = cameraX - tilesWide / 2 + tilesWide / 5;
            int rightBound = cameraX + tilesWide / 2 - tilesWide / 5;
            int bottomBound = cameraY - tilesHigh / 2 + tilesHigh / 5;
            int topBound = cameraY + tilesHigh / 2 - tilesHigh / 5;

            // Move camera if cursor is outside the middle third (i.e., in outer thirds)
            if (cursor.x < leftBound) {
                setX(cameraX - 1);
            } else if (cursor.x > rightBound) {
                setX(cameraX + 1);
            }
            if (cursor.y < bottomBound) {
                setY(cameraY - 1);
            } else if (cursor.y > topBound) {
                setY(cameraY + 1);
            }
        }
    }

    private void setX(int x) {
        position.x = Math.min(Math.max(x * size, getXBound()), (size * Map.MAP_SIZE) - getXBound());
    }

    private void setY(int y) {
        position.y = Math.min(Math.max(y * size, getYBound()), (size * Map.MAP_SIZE) - getYBound());
    }

    public void center(int x, int y) {
        setX(x);
        setY(y);
    }

    private int getXBound() {
        return size * ((xLow + xHigh - 2) / 2);
    }

    private int getYBound() {
        return size * ((yLow + yHigh - 2) / 2);
    }

    public void setZoom() {
        Cursor cursor = HUD.get().getCursor();
        switch (size) {
            case 16:
                xLow = (int)(41);
                yLow = (int)(23);
                xHigh = (int)(41);
                yHigh = (int)(23);
                break;
            case 32:
                xLow = (int)(21);
                yLow = (int)(12);
                xHigh = (int)(21);
                yHigh = (int)(12);
                break;
            default:
                xLow = (int)(11);
                yLow = (int)(7);
                xHigh = (int)(11);
                yHigh = (int)(7);
        }
        center(cursor.x, cursor.y);
    }

    public void zoomIn() {
        if (size < 64) {
            size *= 2;
            setZoom();
        }
    }

    public void zoomOut() {
        if (size > 16) {
            size /= 2;
            setZoom();
        }
    }

    public boolean onCamera(int x, int y) {
        RenderBounds bounds = RenderBounds.get();

        return x >= bounds.x0 && x <= bounds.x1 && y >= bounds.y0 && y <= bounds.y1;
    }
}

