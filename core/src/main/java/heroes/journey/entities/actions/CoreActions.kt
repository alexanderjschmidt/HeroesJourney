package heroes.journey.entities.actions

import heroes.journey.Application
import heroes.journey.GameState
import heroes.journey.components.character.ActionComponent
import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.*
import heroes.journey.modlib.registries.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.screens.MainMenuScreen

fun createCoreActions() {
    // Open Action Menu
    action {
        id = "open_action_menu"
        isReturnsActionList = true
        requirementsMetFn = { ShowAction.NO }
        onSelectFn = { input ->
            val forbiddenTag = Registries.StatManager[Ids.GROUP_APPROACHES]
            val actions = if (forbiddenTag != null) {
                input.findActionsByTags(input.entityId!!, forbiddenTags = listOf(forbiddenTag))
            } else emptyList()
            val options = actions.map { a -> ActionEntry(a.id, input.getHashMapCopy()) }
            ActionListResult(options)
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
        onSelectFn = { input ->
            StringResult(input["message"]!!)
        }
    }.register()

    action {
        id = "options"
        isReturnsActionList = true
        onSelectFn = { input ->
            val optionTag = Registries.StatManager[Ids.GROUP_OPTION]
            val actions = if (optionTag != null) {
                input.findActionsByTags(requiredAllTags = listOf(optionTag))
            } else {
                emptyList()
            }
            val entries = actions.map { a -> ActionEntry(a.id, input.getHashMapCopy()) }
            ActionListResult(entries)
        }
    }.register().also { TeamActions.addTeamAction(it) }
}
