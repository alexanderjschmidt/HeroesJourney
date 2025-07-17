import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.ActionEntry
import heroes.journey.modlib.actions.IAction
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.actions.results.ActionListResult
import heroes.journey.modlib.actions.results.EndTurnResult
import heroes.journey.modlib.actions.results.StringResult

// Core Actions - included by basegame mod

// Rest (Used in End Turn)
action {
    id = "rest"
    onSelectFn = { input ->
        input.addBuff(input.entityId!!, "rested")
        EndTurnResult()
    }
}.register()

object TrainingOptions {
    val optionsList: MutableList<ActionEntry> = ArrayList(4)
    fun addOption(option: IAction) {
        optionsList.add(ActionEntry(option.id, hashMapOf()))
    }
}

// Workout Training Option
action {
    id = "workout_training"
    requirementsMetFn = { input ->
        if (input.getStat(input.entityId!!, Ids.STAT_VALOR) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        input.addStat(input.entityId!!, Ids.STAT_VALOR, -5)
        input.adjustStat(input.entityId!!, Ids.STAT_BODY, 1)
        StringResult("You completed an intense workout! BODY +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Study Training Option
action {
    id = "study_training"
    requirementsMetFn = { input ->
        if (input.getStat(input.entityId!!, Ids.STAT_INSIGHT) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        input.addStat(input.entityId!!, Ids.STAT_INSIGHT, -5)
        input.adjustStat(input.entityId!!, Ids.STAT_MIND, 1)
        StringResult("You completed intensive study! MIND +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Practice Training Option
action {
    id = "practice_training"
    requirementsMetFn = { input ->
        if (input.getStat(input.entityId!!, Ids.STAT_ARCANUM) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        input.addStat(input.entityId!!, Ids.STAT_ARCANUM, -5)
        input.adjustStat(input.entityId!!, Ids.STAT_MAGIC, 1)
        StringResult("You completed magical practice! MAGIC +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Socialize Training Option
action {
    id = "socialize_training"
    requirementsMetFn = { input ->
        if (input.getStat(input.entityId!!, Ids.STAT_INFLUENCE) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        input.addStat(input.entityId!!, Ids.STAT_INFLUENCE, -5)
        input.adjustStat(input.entityId!!, Ids.STAT_CHARISMA, 1)
        StringResult("You completed social training! CHARISMA +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Main Training Action
action {
    id = "training"
    onSelectFn = { input ->
        ActionListResult(TrainingOptions.optionsList)
    }
}.register()
