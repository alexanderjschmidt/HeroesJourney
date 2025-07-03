package heroes.journey.utils.art

import com.badlogic.gdx.graphics.g2d.TextureRegion

class StillRenderable : Renderable {
    private val image: TextureRegion

    constructor(id: String, image: TextureRegion) : super(id) {
        this.image = image
    }

    constructor(id: String, textureMap: TextureMap, x: Int, y: Int) : super(id) {
        this.image = ResourceManager.get(textureMap, x, y)
    }

    override fun getRender(delta: Float): TextureRegion {
        return image
    }
}
