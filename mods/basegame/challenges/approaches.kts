package challenges

import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.approach

// Approaches - included by basegame mod
// Define all the approaches for the challenge system

approach {
    id = Ids.APPROACH_FIGHT
    stat(Ids.STAT_BODY)
    requiresAll(Ids.STAT_PHYSICAL)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 30)
    }
}.register()

approach {
    id = Ids.APPROACH_TRICK
    stat(Ids.STAT_MIND)
    requiresAll(Ids.STAT_PHYSICAL)
    forbids(Ids.STAT_FERAL)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 10)
        stat(Ids.STAT_FOCUS, 10)
    }
}.register()

approach {
    id = Ids.APPROACH_MAGIC_MISSILE
    stat(Ids.STAT_MAGIC)
    requiresAny(Ids.STAT_PHYSICAL, Ids.STAT_INCORPOREAL)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 5)
        stat(Ids.STAT_MANA, 20)
    }
}.register()

approach {
    id = Ids.APPROACH_NEGOTIATE
    stat(Ids.STAT_CHARISMA)
    requiresAll(Ids.STAT_SENTIENT)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 10)
        stat(Ids.STAT_MOXIE, 10)
    }
}.register()
