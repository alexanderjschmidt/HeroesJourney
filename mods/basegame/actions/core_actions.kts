import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.*

// Core Actions - included by basegame mod

// Rest (Used in End Turn)
action {
    id = Ids.REST
    onSelectFn = { input ->
        input.addBuff(input.entityId!!, "rested")
        EndTurnResult()
    }
}.register()

object TrainingOptions {
    val optionsList: MutableList<ActionEntry> = ArrayList(4)
    fun addOption(option: Action) {
        optionsList.add(ActionEntry(option.id, hashMapOf()))
    }
}

// Workout Training Option
action {
    id = Ids.WORKOUT_TRAINING
    requirementsMetFn = { input ->
        if (input.getStat(input.entityId!!, Ids.STAT_STAMINA) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        input.adjustStat(input.entityId!!, Ids.STAT_STAMINA, -5)
        input.adjustStat(input.entityId!!, Ids.STAT_BODY, 1)
        StringResult("You completed an intense workout! BODY +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Study Training Option
action {
    id = Ids.STUDY_TRAINING
    requirementsMetFn = { input ->
        if (input.getStat(input.entityId!!, Ids.STAT_FOCUS) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        input.adjustStat(input.entityId!!, Ids.STAT_FOCUS, -5)
        input.adjustStat(input.entityId!!, Ids.STAT_MIND, 1)
        StringResult("You completed intensive study! MIND +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Practice Training Option
action {
    id = Ids.PRACTICE_TRAINING
    requirementsMetFn = { input ->
        if (input.getStat(input.entityId!!, Ids.STAT_MANA) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        input.adjustStat(input.entityId!!, Ids.STAT_MANA, -5)
        input.adjustStat(input.entityId!!, Ids.STAT_MAGIC, 1)
        StringResult("You completed magical practice! MAGIC +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Socialize Training Option
action {
    id = Ids.SOCIALIZE_TRAINING
    requirementsMetFn = { input ->
        if (input.getStat(input.entityId!!, Ids.STAT_MOXIE) >= 5) {
            ShowAction.YES
        } else {
            ShowAction.GRAYED
        }
    }
    onSelectFn = { input ->
        input.adjustStat(input.entityId!!, Ids.STAT_MOXIE, -5)
        input.adjustStat(input.entityId!!, Ids.STAT_CHARISMA, 1)
        StringResult("You completed social training! CHARISMA +1")
    }
}.register().also { TrainingOptions.addOption(it) }

// Main Training Action
action {
    id = Ids.TRAINING
    onSelectFn = { input ->
        ActionListResult(TrainingOptions.optionsList)
    }
}.register()
