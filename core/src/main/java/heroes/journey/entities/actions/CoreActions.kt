package heroes.journey.entities.actions

import heroes.journey.Application
import heroes.journey.GameState
import heroes.journey.components.character.ActionComponent
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.results.ActionListResult
import heroes.journey.modlib.actions.results.EndTurnResult
import heroes.journey.modlib.actions.results.NullResult
import heroes.journey.modlib.actions.results.StringResult
import heroes.journey.registries.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.screens.MainMenuScreen
import heroes.journey.ui.windows.ActionMenu
import heroes.journey.utils.input.Options

fun createCoreActions() {
    // Open Action Menu
    action {
        id = "open_action_menu"
        isReturnsActionList = true
        requirementsMetFn = { ShowAction.NO }
        onSelectFn = { input ->
            ActionListResult(ActionMenu.getActionsFor(input.gameState as GameState?, input.entityId))
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

    action {
        id = "wait"
        onSelectFn = { input ->
            EndTurnResult()
        }
    }.register()

    // End Turn
    action {
        id = "end_turn"
        onSelectFn = { input ->
            val entityId = (input.gameState as GameState).currentEntity
            // TODO fix this, for some reason its not doing anything after the call
            (input.gameState as GameState)
                .world
                .edit(entityId)
                .create(ActionComponent::class.java)
                .action(Registries.ActionManager["wait"])
            HUD.get().revertToInitialState()
            NullResult()
        }
    }.register().also { TeamActions.addTeamAction(it) }

    // Save Game
    action {
        id = "save"
        onSelectFn = { input ->
            (input.gameState as GameState).save("save", true)
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

    action {
        id = "options"
        isReturnsActionList = true
        onSelectFn = { input ->
            ActionListResult(Options.optionsList)
        }
    }.register().also { TeamActions.addTeamAction(it) }
}
