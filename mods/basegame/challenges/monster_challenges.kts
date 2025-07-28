import heroes.journey.modlib.Ids
import heroes.journey.modlib.IdsC
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Monster Challenges - included by basegame mod
// Organized by descriptor requirements (PHYSICAL, INCORPOREAL, SENTIENT, FERAL)
// Power tiers 4-7 (mid tier content)

challenge {
    id = IdsC.CHALLENGE_BLINDED_GRIMLOCK
    render = IdsC.RENDER_BLINDED_GRIMLOCK
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 4 // 50 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 40)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_BLOODSHOT_EYE
    render = IdsC.RENDER_BLOODSHOT_EYE
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_BRAWNY_OGRE
    render = IdsC.RENDER_BRAWNY_OGRE
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_CRIMSON_SLAAD
    render = IdsC.RENDER_CRIMSON_SLAAD
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_CRUSHING_CYCLOPS
    render = IdsC.RENDER_CRUSHING_CYCLOPS
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_DEATH_SLIME
    render = IdsC.RENDER_DEATH_SLIME
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_FUNGAL_MYCONID
    render = IdsC.RENDER_FUNGAL_MYCONID
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 4 // 50 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 40)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_HUMONGOUS_ETTIN
    render = IdsC.RENDER_HUMONGOUS_ETTIN
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 7 // 200 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 70)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_MURKY_SLAAD
    render = IdsC.RENDER_MURKY_SLAAD
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_OCHRE_JELLY
    render = IdsC.RENDER_OCHRE_JELLY
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_OCULAR_WATCHER
    render = IdsC.RENDER_OCULAR_WATCHER
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_RED_CAP
    render = IdsC.RENDER_RED_CAP
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 4 // 50 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 40)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_SHRIEKER_MUSHROOM
    render = IdsC.RENDER_SHRIEKER_MUSHROOM
    tag(Ids.STAT_INCORPOREAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 4 // 50 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 40)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_STONE_TROLL
    render = IdsC.RENDER_STONE_TROLL
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 6 // 125 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 60)
    }
}.register()
challenge {
    id = IdsC.CHALLENGE_SWAMP_TROLL
    render = IdsC.RENDER_SWAMP_TROLL
    tag(Ids.STAT_PHYSICAL)
    tag(Ids.STAT_FERAL)
    tag(Ids.STAT_MONSTER_RACE)
    powerTier = 5 // 75 power
    rewards = attributes {
        stat(Ids.STAT_FAME, 50)
    }
}.register()
