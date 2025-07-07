import heroes.journey.Application
import heroes.journey.components.BuffsComponent
import heroes.journey.components.StatsComponent
import heroes.journey.components.character.ActionComponent
import heroes.journey.entities.actions.ShowAction
import heroes.journey.entities.actions.TeamActions
import heroes.journey.entities.actions.action
import heroes.journey.entities.actions.results.*
import heroes.journey.entities.tagging.Stat
import heroes.journey.utils.gamestate.StatsUtils
import heroes.journey.registries.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.screens.MainMenuScreen
import heroes.journey.ui.windows.ActionMenu

// Core Actions - included by basegame mod

// Open Action Menu
action {
    id = "open_action_menu"
    name = "THIS SHOULD NEVER BE DISPLAYED"
    description = ""
    isReturnsActionList = true
    requirementsMetFn = { ShowAction.NO }
    onSelectFn = { input ->
        ActionListResult(ActionMenu.getActionsFor(input.gameState, input.entityId))
    }
}.register()

// Exit Game
action {
    id = "exit_game"
    name = "Exit Game"
    description = "Return to main menu"
    onSelectFn = { input ->
        Application.get().screen = MainMenuScreen(Application.get())
        NullResult()
    }
}.register().also { TeamActions.addTeamAction(it) }

// End Turn
action {
    id = "end_turn"
    name = "End Turn"
    description = "End your turn"
    onSelectFn = { input ->
        val entityId = input.gameState.currentEntity
        input.gameState
            .world
            .edit(entityId)
            .create(ActionComponent::class.java)
            .action(Registries.ActionManager.get(heroes.journey.initializers.Ids.REST))
        HUD.get().revertToInitialState()
        NullResult()
    }
}.register().also { TeamActions.addTeamAction(it) }

// Save Game
action {
    id = "save"
    name = "Save"
    description = "Save Game"
    onSelectFn = { input ->
        input.gameState.save("save", true)
        StringResult("Your Game has been Saved!")
    }
}.register().also { TeamActions.addTeamAction(it) }

// Popup
action {
    id = "popup"
    name = "THIS SHOULDNT DISPLAY"
    description = "THIS SHOULDNT DISPLAY"
    inputDisplayNameFn = { input ->
        input["message"]!!
    }
    onSelectFn = { input ->
        StringResult(input["message"])
    }
}.register()

// Rest (Used in End Turn)
action {
    id = "rest"
    name = "Rest"
    description = "Do nothing, resting your body and mind."
    onSelectFn = { input ->
        val buffsComponent = BuffsComponent.get(
            input.gameState.world,
            input.entityId
        )
        buffsComponent.add(Registries.BuffManager.get("rested"))
        EndTurnResult()
    }
}.register()

object TrainingOptions {
    val optionsList: MutableList<heroes.journey.entities.actions.Action> = ArrayList(4)
    fun addOption(option: heroes.journey.entities.actions.Action) {
        optionsList.add(option)
    }
}

// Workout Training Option
action {
    id = "workout_training"
    name = "Workout"
    description = "Intensive physical training to increase BODY (Cost: 5 VALOR)"
    requirementsMetFn = { input ->
        val stats = StatsComponent.get(input.gameState.world, input.entityId)
        if (stats.get(Stat.VALOR) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        val stats = StatsComponent.get(input.gameState.world, input.entityId)
        stats.add(Stat.VALOR, -5)
        StatsUtils.adjustBody(input.gameState, input.entityId, 1)
        StringResult("You completed an intense workout! BODY +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Study Training Option
action {
    id = "study_training"
    name = "Study"
    description = "Intensive mental training to increase MIND (Cost: 5 INSIGHT)"
    requirementsMetFn = { input ->
        val stats = StatsComponent.get(input.gameState.world, input.entityId)
        if (stats.get(Stat.INSIGHT) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        val stats = StatsComponent.get(input.gameState.world, input.entityId)
        stats.add(Stat.INSIGHT, -5)
        StatsUtils.adjustMind(input.gameState, input.entityId, 1)
        StringResult("You completed intensive study! MIND +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Practice Training Option
action {
    id = "practice_training"
    name = "Practice"
    description = "Intensive magical training to increase MAGIC (Cost: 5 ARCANUM)"
    requirementsMetFn = { input ->
        val stats = StatsComponent.get(input.gameState.world, input.entityId)
        if (stats.get(Stat.ARCANUM) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        val stats = StatsComponent.get(input.gameState.world, input.entityId)
        stats.add(Stat.ARCANUM, -5)
        StatsUtils.adjustMagic(input.gameState, input.entityId, 1)
        StringResult("You completed magical practice! MAGIC +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Socialize Training Option
action {
    id = "socialize_training"
    name = "Socialize"
    description = "Intensive social training to increase CHARISMA (Cost: 5 INFLUENCE)"
    requirementsMetFn = { input ->
        val stats = StatsComponent.get(input.gameState.world, input.entityId)
        if (stats.get(Stat.INFLUENCE) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        val stats = StatsComponent.get(input.gameState.world, input.entityId)
        stats.add(Stat.INFLUENCE, -5)
        StatsUtils.adjustCharisma(input.gameState, input.entityId, 1)
        StringResult("You completed social training! CHARISMA +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Main Training Action
action {
    id = "training"
    name = "Training"
    description = "Intensive training to improve your base stats"
    isReturnsActionList = true
    onSelectFn = { input ->
        ActionListResult(TrainingOptions.optionsList.map { action ->
            heroes.journey.entities.actions.ActionEntry(action.id, emptyMap())
        })
    }
}.register()
