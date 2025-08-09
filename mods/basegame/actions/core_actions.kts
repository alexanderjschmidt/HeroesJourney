import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.*
import heroes.journey.modlib.registries.Registries

// Core Actions - included by basegame mod

// Rest (Used in End Turn)
action {
    id = Ids.REST
    tag(Ids.GROUP_MAIN_ACTION)
    onSelectFn = { input ->
        input.addBuff(input.entityId!!, "rested")
        EndTurnResult()
    }
}.register()

// Workout Training Option
action {
    id = Ids.WORKOUT_TRAINING
    tag(Ids.GROUP_TRAINING)
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
}.register()

// Study Training Option
action {
    id = Ids.STUDY_TRAINING
    tag(Ids.GROUP_TRAINING)
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
}.register()

// Practice Training Option
action {
    id = Ids.PRACTICE_TRAINING
    tag(Ids.GROUP_TRAINING)
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
}.register()

// Socialize Training Option
action {
    id = Ids.SOCIALIZE_TRAINING
    tag(Ids.GROUP_TRAINING)
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
}.register()

// Main Training Action
action {
    id = Ids.TRAINING
    onSelectFn = { input ->
        val trainingTag = Registries.StatManager[Ids.TRAINING]
        val actions = if (trainingTag != null) {
            input.findActionsByTags(input.entityId!!, requiredAllTags = listOf(trainingTag))
        } else emptyList()
        val trainings = actions.map { a -> ActionEntry(a.id, input.getHashMapCopy()) }
        ActionListResult(trainings)
    }
}.register()
