import heroes.journey.modlib.Ids
import heroes.journey.modlib.IdsC
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Magical Challenges - included by basegame mod
// Organized by descriptor requirements (PHYSICAL, INCORPOREAL, SENTIENT, FERAL)
// Power tiers 5-8 (mid to high tier content)

challenge {
    id = IdsC.CHALLENGE_MAGICAL_FAIRY
    render = IdsC.RENDER_MAGICAL_FAIRY
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_ADEPT_NECROMANCER
    render = IdsC.RENDER_ADEPT_NECROMANCER
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_CORRUPTED_TREANT
    render = IdsC.RENDER_CORRUPTED_TREANT
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_DEFT_SORCERESS
    render = IdsC.RENDER_DEFT_SORCERESS
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_EARTH_ELEMENTAL
    render = IdsC.RENDER_EARTH_ELEMENTAL
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_EXPERT_DRUID
    render = IdsC.RENDER_EXPERT_DRUID
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 8 // 350 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 80)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_FIRE_ELEMENTAL
    render = IdsC.RENDER_FIRE_ELEMENTAL
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_FLUTTERING_PIXIE
    render = IdsC.RENDER_FLUTTERING_PIXIE
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_GLOWING_WISP
    render = IdsC.RENDER_GLOWING_WISP
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_GRIZZLED_TREANT
    render = IdsC.RENDER_GRIZZLED_TREANT
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_ICE_GOLEM
    render = IdsC.RENDER_ICE_GOLEM
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_IRON_GOLEM
    render = IdsC.RENDER_IRON_GOLEM
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 8 // 350 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 80)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_NOVICE_PYROMANCER
    render = IdsC.RENDER_NOVICE_PYROMANCER
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_VILE_WITCH
    render = IdsC.RENDER_VILE_WITCH
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_WATER_ELEMENTAL
    render = IdsC.RENDER_WATER_ELEMENTAL
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MAGICAL_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()
