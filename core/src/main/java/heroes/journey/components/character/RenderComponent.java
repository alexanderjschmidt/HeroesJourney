package heroes.journey.components.character;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.art.ResourceManager;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Setter
@Accessors(fluent = true, chain = true)
public class RenderComponent extends PooledClonableComponent<RenderComponent> {

    private String sprite;

    public RenderComponent() {
    }

    public static RenderComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(RenderComponent.class, entityId);
    }

    public TextureRegion sprite() {
        return ResourceManager.getSprite(sprite);
    }

    @Override
    protected void reset() {
        sprite = null;
    }

    @Override
    public void copy(RenderComponent from) {
        sprite = from.sprite;
    }

}
