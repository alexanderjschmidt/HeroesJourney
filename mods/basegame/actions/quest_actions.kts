import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.*
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.IQuest
import heroes.journey.modlib.registries.Registries
import java.util.*

// Quest Actions - included by basegame mod

// Quest Action
action {
    id = Ids.QUEST
    inputDisplayNameFn = { input ->
        Registries.QuestManager[input["target"]]!!.getName()
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
targetAction<IQuest> {
    id = Ids.QUEST_BOARD
    inputDisplayNameFn = { input ->
        val locName: String = input.getName(UUID.fromString(input["owner"]))
        "Quest Board for " + locName
    }
    getTargets = { input ->
        val town = UUID.fromString(input["owner"])
        input.getQuests(town)
    }
    targetAction = Ids.QUEST
}.register()

// Complete Quest Action (shows list of available quests)
targetAction<IQuest> {
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
    inputDisplayNameFn = { input ->
        val questId = input["target"]
        val quest = Registries.QuestManager[questId]
        quest?.getName() ?: "Unknown Quest"
    }
    requirementsMetFn = { input ->
        val questId = input["target"]
        val quests = input.getQuests(input.entityId!!)
        val quest = quests.find { it.id == questId }

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
        val quests = input.getQuests(input.entityId!!)
        val quest = quests.find { it.id == questId }

        if (quest != null) {
            val success = quest.onComplete(input)
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
