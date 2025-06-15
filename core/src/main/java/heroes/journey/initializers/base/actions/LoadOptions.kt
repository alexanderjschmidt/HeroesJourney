package heroes.journey.initializers.base.actions

import heroes.journey.entities.actions.booleanOptionAction
import heroes.journey.entities.actions.options.BooleanOptionAction
import heroes.journey.initializers.InitializerInterface
import heroes.journey.utils.input.Options

class LoadOptions : InitializerInterface {
    override fun init() {
        debugOption = booleanOptionAction {
            id = "debug"
            name = "Debug"
            isTrue = false
        }.register()
        Options.addOption(debugOption!!)

        backgroundMusic = booleanOptionAction {
            id = "music"
            name = "Music"
            isTrue = false
        }.register()
        Options.addOption(backgroundMusic!!)
    }

    companion object {
        @JvmField
        var debugOption: BooleanOptionAction? = null

        @JvmField
        var backgroundMusic: BooleanOptionAction? = null
    }
}
