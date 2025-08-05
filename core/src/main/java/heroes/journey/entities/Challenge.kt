package heroes.journey.entities

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries
import heroes.journey.ui.infoproviders.UIInfoProvider

class Challenge(
    id: String,
    override val render: String,
    override val stats: List<IStat>,
    override val powerTier: Int,
    override val rewards: IAttributes
) : Registrable(id), IChallenge, UIInfoProvider {

    override fun register(): Challenge {
        return Registries.ChallengeManager.register(this)
    }

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        // Default implementation - can be overridden by subclasses if needed
    }
}
