import heroes.journey.modlib.Ids
import heroes.journey.modlib.actions.booleanOptionAction

// Options Actions - included by basegame mod

// Debug Option
booleanOptionAction {
    id = Ids.DEBUG
    isTrue = false
}.register()

// Background Music Option
booleanOptionAction {
    id = Ids.MUSIC
    isTrue = false
}.register()
