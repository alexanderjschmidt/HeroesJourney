package challenges

import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.approach

// Approaches - included by basegame mod
// Define all the approaches for the challenge system

// BODY Approaches (2x BODY + 1x Other)
approach {
    id = Ids.APPROACH_MIGHT
    stat(Ids.STAT_BODY)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 10)
    }
}.register()

approach {
    id = Ids.APPROACH_SKILL
    stat(Ids.STAT_BODY)
    stat(Ids.STAT_MIND)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 10)
        stat(Ids.STAT_FOCUS, 5)
    }
}.register()

approach {
    id = Ids.APPROACH_EMPOWERMENT
    stat(Ids.STAT_BODY)
    stat(Ids.STAT_MAGIC)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 1)
        stat(Ids.STAT_MANA, 1)
    }
}.register()

approach {
    id = Ids.APPROACH_CHIVALRY
    stat(Ids.STAT_BODY)
    stat(Ids.STAT_CHARISMA)
    cost = attributes {
        stat(Ids.STAT_STAMINA, 1)
        stat(Ids.STAT_MOXIE, 1)
    }
}.register()

// MIND Approaches (2x MIND + 1x Other)
approach {
    id = Ids.APPROACH_TECHNIQUE
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_BODY)
    cost = attributes {
        stat(Ids.STAT_FOCUS, 2)
    }
}.register()

approach {
    id = Ids.APPROACH_LOGIC
    stat(Ids.STAT_MIND)
    cost = attributes {
        stat(Ids.STAT_FOCUS, 3)
    }
}.register()

approach {
    id = Ids.APPROACH_CONCENTRATION
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_MAGIC)
    cost = attributes {
        stat(Ids.STAT_FOCUS, 1)
        stat(Ids.STAT_MANA, 1)
    }
}.register()

approach {
    id = Ids.APPROACH_CUNNING
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_CHARISMA)
    cost = attributes {
        stat(Ids.STAT_FOCUS, 1)
        stat(Ids.STAT_MOXIE, 1)
    }
}.register()

// MAGIC Approaches (2x MAGIC + 1x Other)
approach {
    id = Ids.APPROACH_ENCHANTING
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_BODY)
    cost = attributes {
        stat(Ids.STAT_MANA, 2)
    }
}.register()

approach {
    id = Ids.APPROACH_ILLUSION
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_MIND)
    cost = attributes {
        stat(Ids.STAT_MANA, 1)
        stat(Ids.STAT_FOCUS, 1)
    }
}.register()

approach {
    id = Ids.APPROACH_SORCERY
    stat(Ids.STAT_MAGIC)
    cost = attributes {
        stat(Ids.STAT_MANA, 3)
    }
}.register()

approach {
    id = Ids.APPROACH_BEWITCHING
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_CHARISMA)
    cost = attributes {
        stat(Ids.STAT_MANA, 1)
        stat(Ids.STAT_MOXIE, 1)
    }
}.register()

// CHARISMA Approaches (2x CHARISMA + 1x Other)
approach {
    id = Ids.APPROACH_BRAVADO
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_BODY)
    cost = attributes {
        stat(Ids.STAT_MOXIE, 2)
    }
}.register()

approach {
    id = Ids.APPROACH_PERSUASION
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_MIND)
    cost = attributes {
        stat(Ids.STAT_MOXIE, 1)
        stat(Ids.STAT_FOCUS, 1)
    }
}.register()

approach {
    id = Ids.APPROACH_MESMERISM
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_MAGIC)
    cost = attributes {
        stat(Ids.STAT_MOXIE, 1)
        stat(Ids.STAT_MANA, 1)
    }
}.register()

approach {
    id = Ids.APPROACH_CHARM
    stat(Ids.STAT_CHARISMA)
    cost = attributes {
        stat(Ids.STAT_MOXIE, 3)
    }
}.register()
