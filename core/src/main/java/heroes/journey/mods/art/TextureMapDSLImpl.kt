package heroes.journey.mods.art

import heroes.journey.modlib.art.ITextureMap
import heroes.journey.modlib.art.TextureMapDSL
import heroes.journey.utils.art.TextureMap
import heroes.journey.modlib.art.TextureMapBuilder

class TextureMapDSLImpl : TextureMapDSL {
    override fun textureMap(init: TextureMapBuilder.() -> Unit): ITextureMap {
        val builder = TextureMapBuilder().apply(init)
        return TextureMap(builder.id, builder.location, builder.width, builder.height)
    }
}
