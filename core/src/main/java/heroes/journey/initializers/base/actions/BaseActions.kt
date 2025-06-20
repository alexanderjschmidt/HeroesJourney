package heroes.journey.initializers.base.actions

import heroes.journey.Application
import heroes.journey.components.BuffsComponent
import heroes.journey.components.QuestsComponent
import heroes.journey.components.character.ActionComponent
import heroes.journey.entities.Quest
import heroes.journey.entities.actions.*
import heroes.journey.entities.actions.results.ActionListResult
import heroes.journey.entities.actions.results.EndTurnResult
import heroes.journey.entities.actions.results.NullResult
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.initializers.InitializerInterface
import heroes.journey.initializers.utils.StatsUtils
import heroes.journey.initializers.utils.Utils
import heroes.journey.registries.Registries
import heroes.journey.registries.Registries.QuestManager
import heroes.journey.ui.HUD
import heroes.journey.ui.screens.MainMenuScreen
import heroes.journey.ui.windows.ActionMenu

class BaseActions : InitializerInterface {
    override fun init() {
        openActionMenu = action {
            id = "open_action_menu"
            name = "THIS SHOULD NEVER BE DISPLAYED"
            description = ""
            isReturnsActionList = true
            requirementsMetFn = { ShowAction.NO }
            onSelectFn = { input ->
                ActionListResult(ActionMenu.getActionsFor(input.gameState, input.entityId))
            }
        }.register()

        exit_game = action {
            id = "exit_game"
            name = "Exit Game"
            description = "Return to main menu"
            onSelectFn = { input ->
                Application.get().screen = MainMenuScreen(Application.get())
                NullResult()
            }
        }.register()
        TeamActions.addTeamAction(exit_game)

        end_turn = action {
            id = "end_turn"
            name = "End Turn"
            description = "End your turn"
            onSelectFn = { input ->
                val entityId = input.gameState.currentEntity
                input.gameState
                    .world
                    .edit(entityId)
                    .create(ActionComponent::class.java)
                    .action(rest)
                HUD.get().revertToInitialState()
                NullResult()
            }
        }.register()
        TeamActions.addTeamAction(end_turn)

        save = action {
            id = "save"
            name = "Save"
            description = "Save Game"
            onSelectFn = { input ->
                input.gameState.save("save", true)
                StringResult("Your Game has been Saved!")
            }
        }.register()
        TeamActions.addTeamAction(save)

        popup = action {
            id = "popup"
            name = "THIS SHOULDNT DISPLAY"
            description = "THIS SHOULDNT DISPLAY"
            inputDisplayNameFn = { input ->
                input
            }
            onSelectFn = { input ->
                StringResult(input.input)
            }
        }.register()

        // Used in End Turn
        rest = action {
            id = "rest"
            name = "Rest"
            description = "Do nothing, resting your body and mind."
            onSelectFn = { input ->
                val buffsComponent = BuffsComponent.get(
                    input.gameState.world,
                    input.entityId
                )
                buffsComponent.add(Registries.BuffManager["rested"])
                EndTurnResult()
            }
        }.register()

        workout = cooldownAction {
            id = "workout"
            name = "Work out"
            description = "Lift weights, gain body"
            turnCooldown = 1
            factionCooldown = false
            onSelectFn = { input ->
                StatsUtils.adjustBody(input.gameState, input.entityId, 1)
            }
        }.register()

        study = cooldownAction {
            id = "study"
            name = "Study"
            description = "Expand your mind, increasing your potential"
            turnCooldown = 2
            factionCooldown = false
            onSelectFn = { input ->
                StatsUtils.adjustMind(input.gameState, input.entityId, 1)
            }
        }.register()

        quest = action {
            id = "quest"
            name = "Accept Quest"
            description = "Accept a quest to complete"
            inputDisplayNameFn = { input ->
                QuestManager[input]!!.getName()
            }
            onSelectFn = { input ->
                val town = Utils.getLocation(input)
                val factionsQuestsComponent = QuestsComponent.get(
                    input.gameState.world,
                    town
                )

                val quest: Quest? = QuestManager[input.input]
                if (factionsQuestsComponent != null) {
                    factionsQuestsComponent.remove(quest)
                    QuestsComponent.get(input.gameState.world, input.entityId)
                        .addQuest(quest)
                }
                EndTurnResult()
            }
        }.register()
        questBoard = targetAction<Quest> {
            id = "quest_board"
            name = "Quest Board"
            description = "See what the people need help with"
            getTargets = { input ->
                val town = Utils.getLocation(input)
                QuestsComponent.get(input.gameState.world, town).quests
            }
            targetAction = quest!!.id
        }.register()
    }

    companion object {

        @JvmField
        var openActionMenu: Action? = null
        var end_turn: Action? = null
        var exit_game: Action? = null
        var popup: Action? = null
        var save: Action? = null

        @JvmField
        var workout: CooldownAction? = null

        @JvmField
        var study: CooldownAction? = null

        @JvmField
        var rest: Action? = null

        var quest: Action? = null

        @JvmField
        var questBoard: TargetAction<Quest>? = null
    }
}
