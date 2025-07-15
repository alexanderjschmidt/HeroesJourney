package heroes.journey.mods

import heroes.journey.modlib.GroupDSL
import heroes.journey.modlib.IGroup
import heroes.journey.entities.tagging.Group

class GroupDSLImpl : GroupDSL {
    override fun group(id: String): IGroup = Group(id)
} 