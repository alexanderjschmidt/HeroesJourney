package heroes.journey.ui.infoproviders

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.components.NamedComponent
import heroes.journey.entities.actions.ActionContext
import java.util.*

class LocationInfoProvider(private val context: ActionContext, private val entityId: UUID) :
    BasicInfoProvider(context, entityId) {

    private var region: Label? = null

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        if (region == null) {
            region = Label("", skin)
        }
        val regionId: UUID = context.getRegion(entityId)
        region!!.setText("Region: " + NamedComponent.get(context.gameState.world, regionId, "---"))
        table.add(region).fill().row()
    }
}
