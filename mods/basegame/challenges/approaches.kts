package challenges

import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.approach

// Approaches - included by basegame mod
// Define all the approaches for the challenge system

approach {
    id = Ids.APPROACH_FIGHT
    stat(Ids.STAT_BODY)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 30)
    }
}.register()

approach {
    id = Ids.APPROACH_TRICK
    stat(Ids.STAT_MIND)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 10)
        stat(Ids.STAT_FOCUS, 10)
    }
}.register()

approach {
    id = Ids.APPROACH_MAGIC_MISSILE
    stat(Ids.STAT_MAGIC)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 5)
        stat(Ids.STAT_MANA, 20)
    }
}.register()

approach {
    id = Ids.APPROACH_NEGOTIATE
    stat(Ids.STAT_MAGIC)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 10)
        stat(Ids.STAT_MOXIE, 10)
    }
}.register()
