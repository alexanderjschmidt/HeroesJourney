package heroes.journey.utils.art

import com.badlogic.gdx.graphics.g2d.TextureRegion
import heroes.journey.modlib.art.IRenderable
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries.RenderableManager

abstract class Renderable(id: String) : Registrable(id), IRenderable {

    abstract fun getRender(deltaTime: Float): TextureRegion

    fun getRender(): TextureRegion {
        return getRender(0f)
    }

    override fun register(): Renderable {
        return RenderableManager.register(this)
    }
}
