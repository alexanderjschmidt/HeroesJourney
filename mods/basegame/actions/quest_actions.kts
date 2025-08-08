import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.*
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.Quest
import heroes.journey.modlib.registries.Registries
import java.util.*

// Quest Actions - included by basegame mod

// Quest Action
action {
    id = Ids.QUEST
    customInfoProviderFn = { input ->
        val questId = input["target"]
        Registries.QuestManager[questId]!!
    }
    onSelectFn = { input ->
        val town: UUID = UUID.fromString(input["owner"])

        input.removeQuest(town, input["target"]!!)
        input.addQuest(input.entityId!!, input["target"]!!)
        EndTurnResult()
    }
    cost = attributes {
        stat(Ids.STAT_STAMINA, 5)
        stat(Ids.STAT_FOCUS, 5)
    }
}.register()

// Quest Board
targetAction<Quest> {
    id = Ids.QUEST_BOARD
    getTargets = { input ->
        val town = UUID.fromString(input["owner"])
        input.getQuests(town)
    }
    targetAction = Ids.QUEST
}.register()

// Complete Quest Action (shows list of available quests)
targetAction<Quest> {
    id = Ids.COMPLETE_QUEST
    requirementsMetFn = { input ->
        val quests = input.getQuests(input.entityId!!)
        if (quests.isNotEmpty()) {
            ShowAction.YES
        } else {
            ShowAction.NO
        }
    }
    getTargets = { input ->
        input.getQuests(input.entityId!!)
    }
    targetAction = Ids.COMPLETE_SPECIFIC_QUEST
}.register()

// Complete Specific Quest Action
action {
    id = Ids.COMPLETE_SPECIFIC_QUEST
    customInfoProviderFn = { input ->
        val questId = input["target"]
        Registries.QuestManager[questId]!!
    }
    requirementsMetFn = { input ->
        val questId = input["target"]
        val quests = input.getQuests(input.entityId!!)
        val quest = quests.find { it.id == questId }

        if (quest != null && input.canAffordQuest(quest, input.entityId!!)) {
            ShowAction.YES
        } else if (quest != null) {
            ShowAction.GRAYED // Can't afford
        } else {
            ShowAction.NO
        }
    }
    onSelectFn = { input ->
        val questId = input["target"]
        val quests = input.getQuests(input.entityId!!)
        val quest = quests.find { it.id == questId }

        if (quest != null) {
            val success = input.completeQuest(quest, input.entityId!!)
            if (success) {
                input.removeQuest(input.entityId!!, questId!!)
                // TODO make this use registrables getName()
                StringResult("Completed quest: ${questId}", false)
            } else {
                StringResult("Failed to complete quest: ${questId}", false)
            }
        } else {
            StringResult("Quest not found", false)
        }
    }
}.register()
