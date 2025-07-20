package challenges

import heroes.journey.modlib.Ids
import heroes.journey.modlib.misc.challengeType

// Challenge Types - included by basegame mod
// Define the four main challenge types with their approaches

// BODY Challenge Type
challengeType {
    id = Ids.CHALLENGE_TYPE_BODY
    primaryApproachId = Ids.APPROACH_MIGHT
    secondaryApproach(Ids.APPROACH_SKILL)
    secondaryApproach(Ids.APPROACH_EMPOWERMENT)
    secondaryApproach(Ids.APPROACH_CHIVALRY)
}.register()

// MIND Challenge Type
challengeType {
    id = Ids.CHALLENGE_TYPE_MIND
    primaryApproachId = Ids.APPROACH_LOGIC
    secondaryApproach(Ids.APPROACH_TECHNIQUE)
    secondaryApproach(Ids.APPROACH_CONCENTRATION)
    secondaryApproach(Ids.APPROACH_CUNNING)
}.register()

// MAGIC Challenge Type
challengeType {
    id = Ids.CHALLENGE_TYPE_MAGIC
    primaryApproachId = Ids.APPROACH_SORCERY
    secondaryApproach(Ids.APPROACH_ENCHANTING)
    secondaryApproach(Ids.APPROACH_ILLUSION)
    secondaryApproach(Ids.APPROACH_BEWITCHING)
}.register()

// CHARISMA Challenge Type
challengeType {
    id = Ids.CHALLENGE_TYPE_CHARISMA
    primaryApproachId = Ids.APPROACH_CHARM
    secondaryApproach(Ids.APPROACH_BRAVADO)
    secondaryApproach(Ids.APPROACH_PERSUASION)
    secondaryApproach(Ids.APPROACH_MESMERISM)
}.register()
