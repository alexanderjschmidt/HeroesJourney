import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.stat

// --- BASE STATS ---
stat {
    id = Ids.STAT_BODY
    defaultValue = 1
    group(Ids.GROUP_BODY)
}.register()
stat {
    id = Ids.STAT_MIND
    defaultValue = 1
    group(Ids.GROUP_MIND)
}.register()
stat {
    id = Ids.STAT_MAGIC
    defaultValue = 1
    group(Ids.GROUP_MAGIC)
}.register()
stat {
    id = Ids.STAT_CHARISMA
    defaultValue = 1
    group(Ids.GROUP_CHARISMA)
}.register()

// --- RESOURCES STATS ---
stat {
    id = Ids.STAT_STAMINA
    min = 0
    defaultValue = 0
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_BODY)
}.register()
stat {
    id = Ids.STAT_FOCUS
    min = 0
    defaultValue = 0
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_MIND)
}.register()
stat {
    id = Ids.STAT_MANA
    min = 0
    defaultValue = 0
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_MAGIC)
}.register()
stat {
    id = Ids.STAT_MOXIE
    min = 0
    defaultValue = 0
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_CHARISMA)
}.register()

// --- RESOURCE MAXES ---
stat {
    id = "stamina_max"
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_BODY)
    group(Ids.GROUP_MAX)
    formula = { it.getDirect(Ids.STAT_BODY)!! * 25 + 75 }
}.register()
stat {
    id = "focus_max"
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_MIND)
    group(Ids.GROUP_MAX)
    formula = { it.getDirect(Ids.STAT_MIND)!! * 25 + 75 }
}.register()
stat {
    id = "mana_max"
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_MAGIC)
    group(Ids.GROUP_MAX)
    formula = { it.getDirect(Ids.STAT_MAGIC)!! * 25 + 75 }
}.register()
stat {
    id = "moxie_max"
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_CHARISMA)
    group(Ids.GROUP_MAX)
    formula = { it.getDirect(Ids.STAT_CHARISMA)!! * 25 + 75 }
}.register()

// --- RESOURCE REGEN STATS ---
stat {
    id = Ids.STAT_STAMINA_REGEN
    defaultValue = 50
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_BODY)
    group(Ids.GROUP_REGEN)
}.register()
stat {
    id = Ids.STAT_FOCUS_REGEN
    defaultValue = 20
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_MIND)
    group(Ids.GROUP_REGEN)
}.register()
stat {
    id = Ids.STAT_MANA_REGEN
    defaultValue = 10
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_MAGIC)
    group(Ids.GROUP_REGEN)
}.register()
stat {
    id = Ids.STAT_MOXIE_REGEN
    defaultValue = 0
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_CHARISMA)
    group(Ids.GROUP_REGEN)
}.register()

// --- CALCULATED STATS ---
stat {
    id = Ids.STAT_SPEED
    formula = { it.getDirect(Ids.STAT_BODY)!! }
}.register()
stat {
    id = Ids.STAT_VISION
    formula = { it.getDirect(Ids.STAT_BODY)!! + 3 }
}.register()
stat {
    id = Ids.STAT_CARRY_CAPACITY
    max = 100
    formula = { it.getDirect(Ids.STAT_BODY)!! * 10 }
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

// MIND
// TODO these seem more like bonus on standard challenges to be added at gen time
/*
// stealth work worse
stat {
    id = Ids.STAT_AWARE
    group(Ids.GROUP_RACE)
}.register()*/
