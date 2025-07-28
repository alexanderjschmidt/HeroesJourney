import heroes.journey.modlib.Ids
import heroes.journey.modlib.IdsC
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Dragon Challenges - included by basegame mod
// Organized by descriptor requirements (PHYSICAL, INCORPOREAL, SENTIENT, FERAL)
// Power tiers 6-9 (high tier content)

// PHYSICAL-based dragon challenges
challenge {
    id = IdsC.CHALLENGE_AQUA_DRAKE
    render = IdsC.RENDER_AQUA_DRAKE
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_BABY_BRASS_DRAGON
    render = IdsC.RENDER_BABY_BRASS_DRAGON
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_BABY_COPPER_DRAGON
    render = IdsC.RENDER_BABY_COPPER_DRAGON
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()

// FERAL-based dragon challenges
challenge {
    id = IdsC.CHALLENGE_BABY_GREEN_DRAGON
    render = IdsC.RENDER_BABY_GREEN_DRAGON
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()

// INCORPOREAL-based dragon challenges
challenge {
    id = IdsC.CHALLENGE_BABY_WHITE_DRAGON
    render = IdsC.RENDER_BABY_WHITE_DRAGON
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_JUVENILE_BRONZE_DRAGON
    render = IdsC.RENDER_JUVENILE_BRONZE_DRAGON
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 8 // 350 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 80)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_MATURE_BRONZE_DRAGON
    render = IdsC.RENDER_MATURE_BRONZE_DRAGON
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 9 // 500 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 90)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_MUD_WYVERN
    render = IdsC.RENDER_MUD_WYVERN
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_POISON_DRAKE
    render = IdsC.RENDER_POISON_DRAKE
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()

// SENTIENT-based dragon challenges
challenge {
    id = IdsC.CHALLENGE_PYGMY_WYVERN
    render = IdsC.RENDER_PYGMY_WYVERN
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_VIRIDIAN_DRAKE
    render = IdsC.RENDER_VIRIDIAN_DRAKE
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_YOUNG_BRASS_DRAGON
    render = IdsC.RENDER_YOUNG_BRASS_DRAGON
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 8 // 350 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 80)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_YOUNG_RED_DRAGON
    render = IdsC.RENDER_YOUNG_RED_DRAGON
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 8 // 350 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 80)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_ADULT_GREEN_DRAGON
    render = IdsC.RENDER_ADULT_GREEN_DRAGON
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 9 // 500 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 90)
    }
}.register()

challenge {
    id = IdsC.CHALLENGE_ADULT_WHITE_DRAGON
    render = IdsC.RENDER_ADULT_WHITE_DRAGON
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_SENTIENT)
    tag(Ids.STAT_DRAGON_RACE)
    powerTier = 9 // 500 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 90)
    }
}.register()
