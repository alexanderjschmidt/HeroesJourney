package heroes.journey.ui.infoproviders

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.entities.actions.ActionContext
import java.util.*

class EntityInfoProvider(private val context: ActionContext, private val entityId: UUID) :
    BasicInfoProvider(context, entityId) {

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        
    }

}
