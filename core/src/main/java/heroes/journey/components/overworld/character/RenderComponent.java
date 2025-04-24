package heroes.journey.components.overworld.character;

import com.artemis.World;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.components.utils.PooledClonableComponent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true, chain = true)
@Transient
public class RenderComponent extends PooledClonableComponent<RenderComponent> {

    private TextureRegion sprite;

    public RenderComponent() {
    }

    public static RenderComponent get(World world, int entityId) {
        return world.getMapper(RenderComponent.class).get(entityId);
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
