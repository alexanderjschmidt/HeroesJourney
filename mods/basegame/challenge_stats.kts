import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.stat
import heroes.journey.modlib.misc.powerLevels

stat {
    id = Ids.STAT_CHALLENGE_POWER_TIER
    min = 0
    group(Ids.GROUP_CHALLENGE)
}.register()

// --- RESOURCES STATS ---
stat {
    id = Ids.STAT_CHALLENGE_HEALTH
    min = 0
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_CHALLENGE)
}.register()

// --- RESOURCE MAXES ---
stat {
    id = Ids.STAT_CHALLENGE_HEALTH_MAX
    min = 0
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_CHALLENGE)
    group(Ids.GROUP_MAX)
    formula = { powerLevels[it.getDirect(Ids.STAT_CHALLENGE_POWER_TIER)!!] }
}.register()

// --- RESOURCE REGEN STATS ---
stat {
    id = Ids.STAT_CHALLENGE_HEALTH_REGEN
    min = 0
    defaultValue = 10
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_CHALLENGE)
    group(Ids.GROUP_REGEN)
}.register()

// --- RACE STATS ---
stat {
    id = Ids.STAT_DEMON_RACE
    min = 0
    max = 100
    group(Ids.GROUP_RACE)
}.register()
stat {
    id = Ids.STAT_DRAGON_RACE
    min = 0
    max = 100
    group(Ids.GROUP_RACE)
}.register()
stat {
    id = Ids.STAT_HOLY_RACE
    min = 0
    max = 100
    group(Ids.GROUP_RACE)
}.register()
stat {
    id = Ids.STAT_HUMANOID_RACE
    min = 0
    max = 100
    group(Ids.GROUP_RACE)
}.register()
stat {
    id = Ids.STAT_MAGICAL_RACE
    min = 0
    max = 100
    group(Ids.GROUP_RACE)
}.register()
stat {
    id = Ids.STAT_MONSTER_RACE
    min = 0
    max = 100
    group(Ids.GROUP_RACE)
}.register()
stat {
    id = Ids.STAT_UNDEAD_RACE
    min = 0
    max = 100
    group(Ids.GROUP_RACE)
}.register()
stat {
    id = Ids.STAT_VERMIN_RACE
    min = 0
    max = 100
    group(Ids.GROUP_RACE)
}.register()

// --- Descriptors ---
// BODY or MAGIC
stat {
    id = Ids.STAT_PHYSICAL
    group(Ids.GROUP_DESCRIPTOR)
}.register()
// MAGIC
stat {
    id = Ids.STAT_INCORPOREAL
    group(Ids.GROUP_DESCRIPTOR)
}.register()
// MIND (trick wont work)
stat {
    id = Ids.STAT_FERAL
    group(Ids.GROUP_DESCRIPTOR)
}.register()
// CHARISMA
stat {
    id = Ids.STAT_SENTIENT
    group(Ids.GROUP_DESCRIPTOR)
}.register()
