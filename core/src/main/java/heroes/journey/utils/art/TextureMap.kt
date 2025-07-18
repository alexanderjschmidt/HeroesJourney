package heroes.journey.utils.art

import heroes.journey.modlib.art.ITextureMap
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries

class TextureMap(id: String, location: String, override val width: Int, override val height: Int) :
    Registrable(id), ITextureMap {
    override val location: String = if (location.startsWith("Textures/")) location else "Textures/$location"

    override fun register(): TextureMap {
        ResourceManager.get().textureRegions[this] = null
        return Registries.TextureManager.register(this)
    }
}
