package heroes.journey.utils.art

import heroes.journey.registries.Registrable

class TextureMap(id: String, location: String, val width: Int, val height: Int) : Registrable(id) {
    val location: String = if (location.startsWith("Textures/")) location else "Textures/$location"

    override fun register(): TextureMap {
        ResourceManager.get().textureRegions[this] = null
        return ResourceManager.TextureManager.register(this)
    }
}
