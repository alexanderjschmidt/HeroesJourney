import heroes.journey.GameState
import heroes.journey.components.NamedComponent
import heroes.journey.components.QuestsComponent
import heroes.journey.entities.Quest
import heroes.journey.entities.actions.ShowAction
import heroes.journey.entities.actions.action
import heroes.journey.entities.actions.results.EndTurnResult
import heroes.journey.entities.actions.results.StringResult
import heroes.journey.entities.actions.targetAction
import heroes.journey.modlib.Ids
import heroes.journey.registries.Registries
import heroes.journey.registries.Registries.QuestManager
import java.util.*

// Quest Actions - included by basegame mod

// Quest Action
action {
    id = "quest"
    inputDisplayNameFn = { input ->
        QuestManager.get(input["target"])!!.getName()
    }
    onSelectFn = { input ->
        val town: UUID = UUID.fromString(input["owner"])
        val factionsQuestsComponent = QuestsComponent.get(
            input.gameState.world,
            town
        )

        val quest: Quest = QuestManager.get(input["target"])!!
        if (factionsQuestsComponent != null) {
            factionsQuestsComponent.remove(quest)
            QuestsComponent.get(input.gameState.world, input.entityId)
                .addQuest(quest)
        }
        EndTurnResult()
    }
}.register()

// Quest Board
targetAction<Quest> {
    id = "quest_board"
    inputDisplayNameFn = { input ->
        val gs: GameState = GameState.global()
        "Quest Board for " + NamedComponent.get(gs.world, UUID.fromString(input["owner"]), "Unknown")
    }
    getTargets = { input ->
        val town = UUID.fromString(input["owner"])
        QuestsComponent.get(input.gameState.world, town).quests
    }
    targetAction = Ids.QUEST
}.register()

// Complete Quest Action (shows list of available quests)
targetAction<Quest> {
    id = "complete_quest"
    requirementsMetFn = { input ->
        val quests = QuestsComponent.get(input.gameState.world, input.entityId)
        if (quests != null && quests.quests.isNotEmpty()) {
            ShowAction.YES
        } else {
            ShowAction.NO
        }
    }
    getTargets = { input ->
        val quests = QuestsComponent.get(input.gameState.world, input.entityId)
        quests?.quests!!
    }
    targetAction = "complete_specific_quest"
}.register()

// Complete Specific Quest Action
action {
    id = "complete_specific_quest"
    inputDisplayNameFn = { input ->
        val questId = input["target"]
        val quest = Registries.QuestManager[questId]
        quest?.getName() ?: "Unknown Quest"
    }
    requirementsMetFn = { input ->
        val questId = input["target"]
        val quests = QuestsComponent.get(input.gameState.world, input.entityId)
        println(questId)
        println(quests?.quests)
        val quest = quests?.quests?.find { it.id == questId }

        if (quest != null && quest.canAfford(input)) {
            ShowAction.YES
        } else if (quest != null) {
            ShowAction.GRAYED // Can't afford
        } else {
            ShowAction.NO
        }
    }
    onSelectFn = { input ->
        val questId = input["target"]
        val quests = QuestsComponent.get(input.gameState.world, input.entityId)
        val quest = quests?.quests?.find { it.id == questId }

        if (quest != null) {
            val success = quest.onComplete(input)
            if (success) {
                quests.remove(quest)
                StringResult("Completed quest: ${quest.getName()}", false)
            } else {
                StringResult("Failed to complete quest: ${quest.getName()}", false)
            }
        } else {
            StringResult("Quest not found", false)
        }
    }
}.register()
