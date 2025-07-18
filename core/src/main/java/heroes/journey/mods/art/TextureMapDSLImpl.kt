package heroes.journey.mods.art

import heroes.journey.modlib.art.ITextureMap
import heroes.journey.modlib.art.TextureMapDSL
import heroes.journey.utils.art.TextureMap

class TextureMapDSLImpl : TextureMapDSL {
    override fun textureMap(id: String, location: String, width: Int, height: Int): ITextureMap {
        return TextureMap(id, location, width, height)
    }
}
