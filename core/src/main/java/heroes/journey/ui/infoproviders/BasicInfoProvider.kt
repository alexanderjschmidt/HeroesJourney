package heroes.journey.ui.infoproviders

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.components.NamedComponent
import heroes.journey.entities.actions.ActionContext
import heroes.journey.modlib.actions.IActionContext
import java.util.*

open class BasicInfoProvider(private val context: ActionContext, private val entityId: UUID) : UIInfoProvider {

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
    }

    override fun getTitle(input: IActionContext): String {
        return NamedComponent.get(context.gameState.world, entityId, "---")
    }

    override fun getDescription(input: Map<String, String>): String {
        return "";
    }
}
