import heroes.journey.modlib.Ids
import heroes.journey.modlib.IdsC
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Demon Challenges - included by basegame mod
// Organized by descriptor requirements (PHYSICAL, INCORPOREAL, SENTIENT, FERAL)
// Power tiers 4-9 (late game content)

// PHYSICAL-based demon challenges
challenge {
    id = IdsC.CHALLENGE_DEPRAVED_BLACKGUARD
    render = IdsC.RENDER_DEPRAVED_BLACKGUARD
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_PIT_BALOR
    render = IdsC.RENDER_PIT_BALOR
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 9 // 500 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 90)
    }
}.register()

// FERAL-based demon challenges
challenge {
    id = IdsC.CHALLENGE_ANTLERED_RASCAL
    render = IdsC.RENDER_ANTLERED_RASCAL
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 4 // 50 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 40)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_CRIMSON_IMP
    render = IdsC.RENDER_CRIMSON_IMP
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 4 // 50 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 40)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_GRINNING_GREMLIN
    render = IdsC.RENDER_GRINNING_GREMLIN
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_NEFARIOUS_SCAMP
    render = IdsC.RENDER_NEFARIOUS_SCAMP
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_TAINTED_SCOUNDREL
    render = IdsC.RENDER_TAINTED_SCOUNDREL
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()

// INCORPOREAL-based demon challenges
challenge {
    id = IdsC.CHALLENGE_CLAWED_ABOMINATION
    render = IdsC.RENDER_CLAWED_ABOMINATION
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_FLOATING_EYE
    render = IdsC.RENDER_FLOATING_EYE
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_FLEDGLING_DEMON
    render = IdsC.RENDER_FLEDGLING_DEMON
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 4 // 50 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 40)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_FOUL_GOUGER
    render = IdsC.RENDER_FOUL_GOUGER
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()

// SENTIENT-based demon challenges
challenge {
    id = IdsC.CHALLENGE_RASCALLY_DEMONLING
    render = IdsC.RENDER_RASCALLY_DEMONLING
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_SKEWERING_STALKER
    render = IdsC.RENDER_SKEWERING_STALKER
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 8 // 350 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 80)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_WARP_SKULL
    render = IdsC.RENDER_WARP_SKULL
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 8 // 350 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 80)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_POINTED_DEMONSPAWN
    render = IdsC.RENDER_POINTED_DEMONSPAWN
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DEMON_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()

