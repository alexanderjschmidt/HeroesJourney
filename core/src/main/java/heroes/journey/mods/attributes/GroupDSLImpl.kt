package heroes.journey.mods.attributes

import heroes.journey.modlib.attributes.GroupDSL
import heroes.journey.modlib.attributes.IGroup
import heroes.journey.entities.tagging.Group

class GroupDSLImpl : GroupDSL {
    override fun group(id: String): IGroup = Group(id)
}
