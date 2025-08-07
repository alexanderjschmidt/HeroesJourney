package heroes.journey.entities

import com.badlogic.gdx.scenes.scene2d.ui.Label
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
        // Display power tier
        val powerTierLabel = Label("Power Tier: $powerTier", skin)
        table.add(powerTierLabel).fill().row()

        // Display stats that can be used
        if (stats.isNotEmpty()) {
            val statsLabel = Label("Available Stats:", skin)
            table.add(statsLabel).fill().row()

            val statsText = StringBuilder()
            for (i in stats.indices) {
                if (i > 0) statsText.append(", ")
                statsText.append(stats[i].getName())
            }
            val statsListLabel = Label(statsText.toString(), skin)
            statsListLabel.wrap = true
            table.add(statsListLabel).fill().row()
        }

        // Display rewards
        if (rewards.isNotEmpty()) {
            val rewardsLabel = Label("Rewards:", skin)
            table.add(rewardsLabel).fill().row()

            val rewardsText = StringBuilder()
            var first = true
            for ((stat, amount) in rewards) {
                if (!first) rewardsText.append(", ")
                rewardsText.append("${stat.getName()}: $amount")
                first = false
            }
            val rewardsListLabel = Label(rewardsText.toString(), skin)
            table.add(rewardsListLabel).fill().row()
        }
    }
}
