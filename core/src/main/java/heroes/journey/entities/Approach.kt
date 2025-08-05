package heroes.journey.entities

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries
import heroes.journey.ui.infoproviders.UIInfoProvider

class Approach(
    id: String,
    override val stats: List<IStat>,
    override val cost: IAttributes?,
    override val requiredAllTags: List<IStat>,
    override val requiredAnyTags: List<IStat>,
    override val forbiddenTags: List<IStat>
) : Registrable(id), IApproach, UIInfoProvider {

    override fun register(): IApproach {
        return Registries.ApproachManager.register(this)
    }

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        // Default implementation - can be overridden by subclasses if needed
    }
}
