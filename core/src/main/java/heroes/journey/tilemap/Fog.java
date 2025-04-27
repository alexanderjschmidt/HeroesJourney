package heroes.journey.tilemap;

import com.badlogic.gdx.graphics.g2d.Batch;
import heroes.journey.GameCamera;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.utils.art.ResourceManager;

public enum Fog {

    DENSE, LIGHT, NONE;

    public void render(Batch batch, int x, int y) {
        if (this == NONE) {
            return;
        }
        if (this == LIGHT) {
            batch.draw(ResourceManager.get(LoadTextures.Sprites)[0][1], x * GameCamera.get().getSize(), y * GameCamera.get().getSize(), GameCamera.get().getSize(), GameCamera.get().getSize());
        } else {
            batch.draw(ResourceManager.get(LoadTextures.Sprites)[0][0], x * GameCamera.get().getSize(), y * GameCamera.get().getSize(), GameCamera.get().getSize(), GameCamera.get().getSize());
        }
    }
}
