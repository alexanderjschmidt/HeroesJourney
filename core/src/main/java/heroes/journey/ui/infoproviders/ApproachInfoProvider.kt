package heroes.journey.ui.infoproviders

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.modlib.misc.Approach

/**
 * Info provider for Approach objects that builds UI content.
 */
class ApproachInfoProvider(private val approach: Approach) : RegistrableInfoProvider(approach) {

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        // Display cost if present
        if (approach.cost != null) {
            val costLabel = Label("Cost:", skin)
            table.add(costLabel).fill().row()

            val costText = StringBuilder()
            var first = true
            for ((stat, amount) in approach.cost!!) {
                if (!first) costText.append(", ")
                costText.append("${stat.getName()}: $amount")
                first = false
            }
            val costListLabel = Label(costText.toString(), skin)
            table.add(costListLabel).fill().row()
        }

        // Display required all tags
        if (approach.requiredAllTags.isNotEmpty()) {
            val requiredAllLabel = Label("Required All Tags:", skin)
            table.add(requiredAllLabel).fill().row()

            val requiredAllText = StringBuilder()
            for (i in approach.requiredAllTags.indices) {
                if (i > 0) requiredAllText.append(", ")
                requiredAllText.append(approach.requiredAllTags[i].getName())
            }
            val requiredAllListLabel = Label(requiredAllText.toString(), skin)
            table.add(requiredAllListLabel).fill().row()
        }

        // Display required any tags
        if (approach.requiredAnyTags.isNotEmpty()) {
            val requiredAnyLabel = Label("Required Any Tags:", skin)
            table.add(requiredAnyLabel).fill().row()

            val requiredAnyText = StringBuilder()
            for (i in approach.requiredAnyTags.indices) {
                if (i > 0) requiredAnyText.append(", ")
                requiredAnyText.append(approach.requiredAnyTags[i].getName())
            }
            val requiredAnyListLabel = Label(requiredAnyText.toString(), skin)
            table.add(requiredAnyListLabel).fill().row()
        }

        // Display forbidden tags
        if (approach.forbiddenTags.isNotEmpty()) {
            val forbiddenLabel = Label("Forbidden Tags:", skin)
            table.add(forbiddenLabel).fill().row()

            val forbiddenText = StringBuilder()
            for (i in approach.forbiddenTags.indices) {
                if (i > 0) forbiddenText.append(", ")
                forbiddenText.append(approach.forbiddenTags[i].getName())
            }
            val forbiddenListLabel = Label(forbiddenText.toString(), skin)
            table.add(forbiddenListLabel).fill().row()
        }
    }
}
