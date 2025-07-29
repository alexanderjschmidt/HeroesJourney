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
stat {
    id = Ids.STAT_FAME
    defaultValue = 0
    group(Ids.GROUP_FAME)
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
    id = Ids.STAT_STAMINA_MAX
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_BODY)
    group(Ids.GROUP_MAX)
    formula = { it.getDirect(Ids.STAT_BODY)!! * 25 + 75 }
}.register()
stat {
    id = Ids.STAT_FOCUS_MAX
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_MIND)
    group(Ids.GROUP_MAX)
    formula = { it.getDirect(Ids.STAT_MIND)!! * 25 + 75 }
}.register()
stat {
    id = Ids.STAT_MANA_MAX
    group(Ids.GROUP_RESOURCES)
    group(Ids.GROUP_MAGIC)
    group(Ids.GROUP_MAX)
    formula = { it.getDirect(Ids.STAT_MAGIC)!! * 25 + 75 }
}.register()
stat {
    id = Ids.STAT_MOXIE_MAX
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
