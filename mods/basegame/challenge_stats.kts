import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.Relation
import heroes.journey.modlib.attributes.relate
import heroes.journey.modlib.attributes.stat
import heroes.journey.modlib.misc.powerLevels

// --- CHALLENGE POWER TIER (base stat) ---
stat {
    id = Ids.STAT_CHALLENGE_POWER_TIER
    min = 0
    max = 10
    defaultValue = 0
}.register()

// --- CHALLENGE HEALTH (base stat) ---
stat {
    id = Ids.STAT_CHALLENGE_HEALTH
    min = 0
    defaultValue = 0
    maxFormula = {
        it.getDirect(Ids.STAT_CHALLENGE_POWER_TIER)?.let { tier ->
            powerLevels[tier]
        }
    }
}.register()

// --- CHALLENGE HEALTH REGEN (base stat) ---
stat {
    id = Ids.STAT_CHALLENGE_HEALTH_REGEN
    min = 0
    defaultValue = 10
}.register()

// --- RELATIONSHIPS ---
relate(Ids.STAT_CHALLENGE_HEALTH, Relation.RESOURCE, Ids.STAT_CHALLENGE_POWER_TIER)
relate(Ids.STAT_CHALLENGE_HEALTH_REGEN, Relation.REGEN, Ids.STAT_CHALLENGE_HEALTH)

// --- RACE PARENT STAT (base stat) ---
stat {
    id = Ids.GROUP_RACE
    defaultValue = 0
}.register()

// --- RACE STATS (children of RACE parent) ---
stat {
    id = Ids.STAT_DEMON_RACE
    parent = Ids.GROUP_RACE
    min = 0
    max = 100
    defaultValue = 0
}.register()
stat {
    id = Ids.STAT_DRAGON_RACE
    parent = Ids.GROUP_RACE
    min = 0
    max = 100
    defaultValue = 0
}.register()
stat {
    id = Ids.STAT_HOLY_RACE
    parent = Ids.GROUP_RACE
    min = 0
    max = 100
    defaultValue = 0
}.register()
stat {
    id = Ids.STAT_HUMANOID_RACE
    parent = Ids.GROUP_RACE
    min = 0
    max = 100
    defaultValue = 0
}.register()
stat {
    id = Ids.STAT_MAGICAL_RACE
    parent = Ids.GROUP_RACE
    min = 0
    max = 100
    defaultValue = 0
}.register()
stat {
    id = Ids.STAT_MONSTER_RACE
    parent = Ids.GROUP_RACE
    min = 0
    max = 100
    defaultValue = 0
}.register()
stat {
    id = Ids.STAT_UNDEAD_RACE
    parent = Ids.GROUP_RACE
    min = 0
    max = 100
    defaultValue = 0
}.register()
stat {
    id = Ids.STAT_VERMIN_RACE
    parent = Ids.GROUP_RACE
    min = 0
    max = 100
    defaultValue = 0
}.register()

// --- DESCRIPTOR PARENT STAT (base stat) ---
stat {
    id = Ids.GROUP_DESCRIPTOR
    defaultValue = 0
}.register()

// --- DESCRIPTOR STATS (children of DESCRIPTOR parent) ---
stat {
    id = Ids.STAT_PHYSICAL
    parent = Ids.GROUP_DESCRIPTOR
}.register()
stat {
    id = Ids.STAT_INCORPOREAL
    parent = Ids.GROUP_DESCRIPTOR
}.register()
stat {
    id = Ids.STAT_FERAL
    parent = Ids.GROUP_DESCRIPTOR
}.register()
stat {
    id = Ids.STAT_SENTIENT
    parent = Ids.GROUP_DESCRIPTOR
}.register()
