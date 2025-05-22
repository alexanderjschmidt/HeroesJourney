package heroes.journey.utils;

import heroes.journey.GameCamera;
import heroes.journey.GameState;

public class RenderBounds {

    public static final RenderBounds renderBounds = new RenderBounds();
    public int x0, y0, x1, y1;

    private RenderBounds() {
    }

    private RenderBounds update() {
        int xo = (int)(GameCamera.get().position.x / GameCamera.get().getSize());
        int yo = (int)(GameCamera.get().position.y / GameCamera.get().getSize());

        x0 = (int)Math.max(Math.floor(xo - GameCamera.get().getXLow()), 0);
        y0 = (int)Math.max(Math.floor(yo - GameCamera.get().getYLow()), 0);
        x1 = (int)Math.min(Math.floor(xo + GameCamera.get().getXHigh()), GameState.global().getWidth());
        y1 = (int)Math.min(Math.floor(yo + GameCamera.get().getYHigh()), GameState.global().getHeight());

        return this;
    }

    public static RenderBounds get() {
        return renderBounds.update();
    }
}
