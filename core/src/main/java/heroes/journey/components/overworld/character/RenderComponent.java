package heroes.journey.components.overworld.character;

import com.artemis.PooledComponent;
import com.artemis.World;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true, chain = true)
@Transient
public class RenderComponent extends PooledComponent {

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

}
