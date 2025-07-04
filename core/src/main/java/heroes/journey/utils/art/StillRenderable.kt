package heroes.journey.utils.art

import com.badlogic.gdx.graphics.g2d.TextureRegion

class StillRenderable(id: String, private val textureMap: TextureMap, private val x: Int, private val y: Int) :
    Renderable(id) {

    private var region: TextureRegion? = null

    override fun getRender(delta: Float): TextureRegion {
        if (region == null) {
            region = ResourceManager.get(textureMap, x, y)
        }
        return region!!
    }
}
