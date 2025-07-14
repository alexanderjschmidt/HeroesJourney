import heroes.journey.Application
import heroes.journey.components.BuffsComponent
import heroes.journey.components.StatsComponent
import heroes.journey.components.character.ActionComponent
import heroes.journey.entities.actions.ShowAction
import heroes.journey.entities.actions.TeamActions
import heroes.journey.entities.actions.action
import heroes.journey.entities.actions.results.ActionListResult
import heroes.journey.entities.actions.results.EndTurnResult
import heroes.journey.entities.actions.results.NullResult
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids
import heroes.journey.registries.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.screens.MainMenuScreen
import heroes.journey.ui.windows.ActionMenu

// Core Actions - included by basegame mod

// Open Action Menu
action {
    id = "open_action_menu"
    isReturnsActionList = true
    requirementsMetFn = { ShowAction.NO }
    onSelectFn = { input ->
        ActionListResult(ActionMenu.getActionsFor(input.gameState, input.entityId))
    }
}.register()

// Exit Game
action {
    id = "exit_game"
    onSelectFn = { input ->
        Application.get().screen = MainMenuScreen(Application.get())
        NullResult()
    }
}.register().also { TeamActions.addTeamAction(it) }

// End Turn
action {
    id = "end_turn"
    onSelectFn = { input ->
        val entityId = input.gameState.currentEntity
        input.gameState
            .world
            .edit(entityId)
            .create(ActionComponent::class.java)
            .action(Registries.ActionManager.get(Ids.REST))
        HUD.get().revertToInitialState()
        NullResult()
    }
}.register().also { TeamActions.addTeamAction(it) }

// Save Game
action {
    id = "save"
    onSelectFn = { input ->
        input.gameState.save("save", true)
        StringResult("Your Game has been Saved!")
    }
}.register().also { TeamActions.addTeamAction(it) }

// Popup
action {
    id = "popup"
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
        StatsComponent.adjustStat(input.gameState.world, input.entityId, Stat.BODY, 1)
        StringResult("You completed an intense workout! BODY +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Study Training Option
action {
    id = "study_training"
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
        StatsComponent.adjustStat(input.gameState.world, input.entityId, Stat.MIND, 1)
        StringResult("You completed intensive study! MIND +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Practice Training Option
action {
    id = "practice_training"
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
        StatsComponent.adjustStat(input.gameState.world, input.entityId, Stat.MAGIC, 1)
        StringResult("You completed magical practice! MAGIC +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Socialize Training Option
action {
    id = "socialize_training"
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
        StatsComponent.adjustStat(input.gameState.world, input.entityId, Stat.CHARISMA, 1)
        StringResult("You completed social training! CHARISMA +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Main Training Action
action {
    id = "training"
    onSelectFn = { input ->
        StringResult("Training complete!")
    }
}.register()
