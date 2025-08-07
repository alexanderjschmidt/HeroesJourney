package heroes.journey.entities

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries
import heroes.journey.ui.infoproviders.UIInfoProvider
import com.badlogic.gdx.scenes.scene2d.ui.Label

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
        // Display cost if present
        if (cost != null) {
            val costLabel = Label("Cost:", skin)
            table.add(costLabel).fill().row()
            
            val costText = StringBuilder()
            var first = true
            for ((stat, amount) in cost!!) {
                if (!first) costText.append(", ")
                costText.append("${stat.getName()}: $amount")
                first = false
            }
            val costListLabel = Label(costText.toString(), skin)
            table.add(costListLabel).fill().row()
        }
        
        // Display required all tags
        if (requiredAllTags.isNotEmpty()) {
            val requiredAllLabel = Label("Required All Tags:", skin)
            table.add(requiredAllLabel).fill().row()
            
            val requiredAllText = StringBuilder()
            for (i in requiredAllTags.indices) {
                if (i > 0) requiredAllText.append(", ")
                requiredAllText.append(requiredAllTags[i].getName())
            }
            val requiredAllListLabel = Label(requiredAllText.toString(), skin)
            table.add(requiredAllListLabel).fill().row()
        }
        
        // Display required any tags
        if (requiredAnyTags.isNotEmpty()) {
            val requiredAnyLabel = Label("Required Any Tags:", skin)
            table.add(requiredAnyLabel).fill().row()
            
            val requiredAnyText = StringBuilder()
            for (i in requiredAnyTags.indices) {
                if (i > 0) requiredAnyText.append(", ")
                requiredAnyText.append(requiredAnyTags[i].getName())
            }
            val requiredAnyListLabel = Label(requiredAnyText.toString(), skin)
            table.add(requiredAnyListLabel).fill().row()
        }
        
        // Display forbidden tags
        if (forbiddenTags.isNotEmpty()) {
            val forbiddenLabel = Label("Forbidden Tags:", skin)
            table.add(forbiddenLabel).fill().row()
            
            val forbiddenText = StringBuilder()
            for (i in forbiddenTags.indices) {
                if (i > 0) forbiddenText.append(", ")
                forbiddenText.append(forbiddenTags[i].getName())
            }
            val forbiddenListLabel = Label(forbiddenText.toString(), skin)
            table.add(forbiddenListLabel).fill().row()
        }
    }
}
