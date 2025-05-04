package heroes.journey.components.character;

import com.artemis.annotations.Transient;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Setter
@Getter
@Accessors(fluent = true, chain = true)
@Transient
public class RenderComponent extends PooledClonableComponent<RenderComponent> {

    private TextureRegion sprite;

    public RenderComponent() {
    }

    public static RenderComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(RenderComponent.class, entityId);
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
