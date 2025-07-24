package challenges

import heroes.journey.modlib.Ids
import heroes.journey.modlib.misc.approach

// Approaches - included by basegame mod
// Define all the approaches for the challenge system

// BODY Approaches (2x BODY + 1x Other)
approach {
    id = Ids.APPROACH_MIGHT
    stat(Ids.STAT_BODY)
}.register()

approach {
    id = Ids.APPROACH_SKILL
    stat(Ids.STAT_BODY)
    stat(Ids.STAT_MIND)
}.register()

approach {
    id = Ids.APPROACH_EMPOWERMENT
    stat(Ids.STAT_BODY)
    stat(Ids.STAT_MAGIC)
}.register()

approach {
    id = Ids.APPROACH_CHIVALRY
    stat(Ids.STAT_BODY)
    stat(Ids.STAT_CHARISMA)
}.register()

// MIND Approaches (2x MIND + 1x Other)
approach {
    id = Ids.APPROACH_TECHNIQUE
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_BODY)
}.register()

approach {
    id = Ids.APPROACH_LOGIC
    stat(Ids.STAT_MIND)
}.register()

approach {
    id = Ids.APPROACH_CONCENTRATION
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_MAGIC)
}.register()

approach {
    id = Ids.APPROACH_CUNNING
    stat(Ids.STAT_MIND)
    stat(Ids.STAT_CHARISMA)
}.register()

// MAGIC Approaches (2x MAGIC + 1x Other)
approach {
    id = Ids.APPROACH_ENCHANTING
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_BODY)
}.register()

approach {
    id = Ids.APPROACH_ILLUSION
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_MIND)
}.register()

approach {
    id = Ids.APPROACH_SORCERY
    stat(Ids.STAT_MAGIC)
}.register()

approach {
    id = Ids.APPROACH_BEWITCHING
    stat(Ids.STAT_MAGIC)
    stat(Ids.STAT_CHARISMA)
}.register()

// CHARISMA Approaches (2x CHARISMA + 1x Other)
approach {
    id = Ids.APPROACH_BRAVADO
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_BODY)
}.register()

approach {
    id = Ids.APPROACH_PERSUASION
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_MIND)
}.register()

approach {
    id = Ids.APPROACH_MESMERISM
    stat(Ids.STAT_CHARISMA)
    stat(Ids.STAT_MAGIC)
}.register()

approach {
    id = Ids.APPROACH_CHARM
    stat(Ids.STAT_CHARISMA)
}.register()
