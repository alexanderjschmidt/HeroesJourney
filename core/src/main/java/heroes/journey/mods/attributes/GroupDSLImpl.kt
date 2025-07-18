package heroes.journey.mods.attributes

import heroes.journey.modlib.attributes.GroupDSL
import heroes.journey.modlib.attributes.IGroup
import heroes.journey.entities.tagging.Group
import heroes.journey.modlib.attributes.GroupBuilder

class GroupBuilderImpl : GroupBuilder {
    override var id: String = ""
}

class GroupDSLImpl : GroupDSL {
    override fun group(init: GroupBuilder.() -> Unit): IGroup {
        val builder = GroupBuilderImpl()
        builder.init()
        return Group(builder.id)
    }
}
