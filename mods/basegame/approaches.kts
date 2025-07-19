import heroes.journey.modlib.Ids
import heroes.journey.modlib.misc.approach

// Approaches - included by basegame mod
// Define all the approaches for the challenge system

// BODY Approaches (2x BODY + 1x Other)
approach {
    id = Ids.APPROACH_MIGHT
    baseStatId = Ids.STAT_BODY
}.register()

approach {
    id = Ids.APPROACH_SKILL
    baseStatId = Ids.STAT_BODY
    secondaryStatId = Ids.STAT_MIND
}.register()

approach {
    id = Ids.APPROACH_EMPOWERMENT
    baseStatId = Ids.STAT_BODY
    secondaryStatId = Ids.STAT_MAGIC
}.register()

approach {
    id = Ids.APPROACH_CHIVALRY
    baseStatId = Ids.STAT_BODY
    secondaryStatId = Ids.STAT_CHARISMA
}.register()

// MIND Approaches (2x MIND + 1x Other)
approach {
    id = Ids.APPROACH_TECHNIQUE
    baseStatId = Ids.STAT_MIND
    secondaryStatId = Ids.STAT_BODY
}.register()

approach {
    id = Ids.APPROACH_LOGIC
    baseStatId = Ids.STAT_MIND
}.register()

approach {
    id = Ids.APPROACH_CONCENTRATION
    baseStatId = Ids.STAT_MIND
    secondaryStatId = Ids.STAT_MAGIC
}.register()

approach {
    id = Ids.APPROACH_CUNNING
    baseStatId = Ids.STAT_MIND
    secondaryStatId = Ids.STAT_CHARISMA
}.register()

// MAGIC Approaches (2x MAGIC + 1x Other)
approach {
    id = Ids.APPROACH_ENCHANTING
    baseStatId = Ids.STAT_MAGIC
    secondaryStatId = Ids.STAT_BODY
}.register()

approach {
    id = Ids.APPROACH_ILLUSION
    baseStatId = Ids.STAT_MAGIC
    secondaryStatId = Ids.STAT_MIND
}.register()

approach {
    id = Ids.APPROACH_SORCERY
    baseStatId = Ids.STAT_MAGIC
}.register()

approach {
    id = Ids.APPROACH_BEWITCHING
    baseStatId = Ids.STAT_MAGIC
    secondaryStatId = Ids.STAT_CHARISMA
}.register()

// CHARISMA Approaches (2x CHARISMA + 1x Other)
approach {
    id = Ids.APPROACH_BRAVADO
    baseStatId = Ids.STAT_CHARISMA
    secondaryStatId = Ids.STAT_BODY
}.register()

approach {
    id = Ids.APPROACH_PERSUASION
    baseStatId = Ids.STAT_CHARISMA
    secondaryStatId = Ids.STAT_MIND
}.register()

approach {
    id = Ids.APPROACH_MESMERISM
    baseStatId = Ids.STAT_CHARISMA
    secondaryStatId = Ids.STAT_MAGIC
}.register()

approach {
    id = Ids.APPROACH_CHARM
    baseStatId = Ids.STAT_CHARISMA
}.register()
