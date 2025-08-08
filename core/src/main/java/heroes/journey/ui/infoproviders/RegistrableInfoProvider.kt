package heroes.journey.ui.infoproviders

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.registries.Registrable

open class RegistrableInfoProvider(private val registrable: Registrable) : UIInfoProvider {

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
    }

    override fun getTitle(input: IActionContext): String {
        return registrable.getName()
    }

    override fun getDescription(input: Map<String, String>): String {
        return registrable.getDescription()
    }
}
