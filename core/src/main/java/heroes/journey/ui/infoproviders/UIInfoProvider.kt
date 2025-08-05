package heroes.journey.ui.infoproviders

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.modlib.registries.InfoProvider

/**
 * UI-specific extension of InfoProvider that adds UI rendering capabilities.
 * This interface is used by the core module for displaying custom UI content.
 */
interface UIInfoProvider : InfoProvider {
    fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>)
}
