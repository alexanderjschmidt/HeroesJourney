import heroes.journey.Application
import heroes.journey.GameState
import heroes.journey.components.BuffsComponent
import heroes.journey.components.character.ActionComponent
import heroes.journey.entities.actions.*
import heroes.journey.entities.actions.results.ActionListResult
import heroes.journey.entities.actions.results.EndTurnResult
import heroes.journey.entities.actions.results.NullResult
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.initializers.utils.StatsUtils
import heroes.journey.registries.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.screens.MainMenuScreen
import heroes.journey.ui.windows.ActionMenu
import heroes.journey.entities.actions.TeamActions

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
            .action(Registries.ActionManager.get("rest"))
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

// Workout
cooldownAction {
    id = "workout"
    name = "Work out"
    description = "Lift weights, gain body"
    turnCooldown = 1
    factionCooldown = false
    onSelectFn = { input ->
        StatsUtils.adjustBody(input.gameState, input.entityId, 1)
    }
}.register()

// Study
cooldownAction {
    id = "study"
    name = "Study"
    description = "Expand your mind, increasing your potential"
    turnCooldown = 2
    factionCooldown = false
    onSelectFn = { input ->
        StatsUtils.adjustMind(input.gameState, input.entityId, 1)
    }
}.register() 