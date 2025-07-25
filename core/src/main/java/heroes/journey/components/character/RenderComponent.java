package heroes.journey.components.character;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.art.Renderable;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

import static heroes.journey.mods.Registries.RenderableManager;

@Setter
@Accessors(fluent = true, chain = true)
public class RenderComponent extends PooledClonableComponent<RenderComponent> {

    private String sprite;

    public RenderComponent() {
    }

    public static RenderComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(RenderComponent.class, entityId);
    }

    public Renderable sprite() {
        Renderable render = RenderableManager.get(sprite);
        if (render == null) {
            throw new IllegalArgumentException("Renderable does not exist for id: " + sprite);
        }
        return render;
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
