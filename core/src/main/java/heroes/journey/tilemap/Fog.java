package heroes.journey.tilemap;

import com.badlogic.gdx.graphics.g2d.Batch;
import heroes.journey.GameCamera;

import static heroes.journey.initializers.base.LoadTextures.DENSE_FOG;
import static heroes.journey.initializers.base.LoadTextures.LIGHT_FOG;
import static heroes.journey.utils.art.ResourceManager.RenderableManager;

public enum Fog {

    DENSE, LIGHT, NONE;

    public static Fog fogFromChar(char ch) {
        return ch == 'D' ? DENSE : (ch == 'L' ? LIGHT : NONE);
    }

    public void render(Batch batch, int x, int y) {
        if (this == NONE) {
            return;
        }
        if (this == LIGHT) {
            batch.draw(RenderableManager.get(LIGHT_FOG).getRender(), x * GameCamera.get().getSize(),
                y * GameCamera.get().getSize(), GameCamera.get().getSize(), GameCamera.get().getSize());
        } else {
            batch.draw(RenderableManager.get(DENSE_FOG).getRender(), x * GameCamera.get().getSize(),
                y * GameCamera.get().getSize(), GameCamera.get().getSize(), GameCamera.get().getSize());
        }
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + "";
    }

}
