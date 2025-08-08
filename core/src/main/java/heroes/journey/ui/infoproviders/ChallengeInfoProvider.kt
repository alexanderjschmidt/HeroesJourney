package heroes.journey.ui.infoproviders

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.modlib.misc.Challenge

/**
 * Info provider for Challenge objects that builds UI content.
 */
class ChallengeInfoProvider(private val challenge: Challenge) : RegistrableInfoProvider(challenge) {

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        // Display power tier
        val powerTierLabel = Label("Power Tier: ${challenge.powerTier}", skin)
        table.add(powerTierLabel).fill().row()

        // Display stats that can be used
        if (challenge.stats.isNotEmpty()) {
            val statsLabel = Label("Available Stats:", skin)
            table.add(statsLabel).fill().row()

            val statsText = StringBuilder()
            for (i in challenge.stats.indices) {
                if (i > 0) statsText.append(", ")
                statsText.append(challenge.stats[i].getName())
            }
            val statsListLabel = Label(statsText.toString(), skin)
            statsListLabel.wrap = true
            table.add(statsListLabel).fill().row()
        }

        // Display rewards
        if (challenge.rewards.isNotEmpty()) {
            val rewardsLabel = Label("Rewards:", skin)
            table.add(rewardsLabel).fill().row()

            val rewardsText = StringBuilder()
            var first = true
            for ((stat, amount) in challenge.rewards) {
                if (!first) rewardsText.append(", ")
                rewardsText.append("${stat.getName()}: $amount")
                first = false
            }
            val rewardsListLabel = Label(rewardsText.toString(), skin)
            table.add(rewardsListLabel).fill().row()
        }
    }
}
