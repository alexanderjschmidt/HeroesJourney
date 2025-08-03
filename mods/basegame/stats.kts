import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.Relation
import heroes.journey.modlib.attributes.relate
import heroes.journey.modlib.attributes.stat

// --- BASE STATS (no parent) ---
stat {
    id = Ids.GROUP_BASE_STATS
}.register()
stat {
    id = Ids.GROUP_RESOURCES
}.register()
stat {
    id = Ids.GROUP_REGEN
}.register()

stat {
    id = Ids.STAT_BODY
    parent = Ids.GROUP_BASE_STATS
    defaultValue = 1
    min = 1
    max = 10
}.register()
stat {
    id = Ids.STAT_MIND
    parent = Ids.GROUP_BASE_STATS
    defaultValue = 1
    min = 1
    max = 10
}.register()
stat {
    id = Ids.STAT_MAGIC
    parent = Ids.GROUP_BASE_STATS
    defaultValue = 1
    min = 1
    max = 10
}.register()
stat {
    id = Ids.STAT_CHARISMA
    parent = Ids.GROUP_BASE_STATS
    defaultValue = 1
    min = 1
    max = 10
}.register()
stat {
    id = Ids.STAT_FAME
    defaultValue = 0
}.register()

// --- RESOURCES STATS (base stats, no parent) ---
stat {
    id = Ids.STAT_STAMINA
    parent = Ids.GROUP_RESOURCES
    min = 0
    defaultValue = 0
    maxFormula = { it.getDirect(Ids.STAT_BODY)!! * 25 + 75 }
}.register()
stat {
    id = Ids.STAT_FOCUS
    parent = Ids.GROUP_RESOURCES
    min = 0
    defaultValue = 0
    maxFormula = { it.getDirect(Ids.STAT_MIND)!! * 25 + 75 }
}.register()
stat {
    id = Ids.STAT_MANA
    parent = Ids.GROUP_RESOURCES
    min = 0
    defaultValue = 0
    maxFormula = { it.getDirect(Ids.STAT_MAGIC)!! * 25 + 75 }
}.register()
stat {
    id = Ids.STAT_MOXIE
    parent = Ids.GROUP_RESOURCES
    min = 0
    defaultValue = 0
    maxFormula = { it.getDirect(Ids.STAT_CHARISMA)!! * 25 + 75 }
}.register()

// --- RESOURCE REGEN STATS (base stats, no parent) ---
stat {
    id = Ids.STAT_STAMINA_REGEN
    parent = Ids.GROUP_REGEN
    defaultValue = 50
}.register()
stat {
    id = Ids.STAT_FOCUS_REGEN
    parent = Ids.GROUP_REGEN
    defaultValue = 20
}.register()
stat {
    id = Ids.STAT_MANA_REGEN
    parent = Ids.GROUP_REGEN
    defaultValue = 10
}.register()
stat {
    id = Ids.STAT_MOXIE_REGEN
    parent = Ids.GROUP_REGEN
    defaultValue = 0
}.register()

// --- CALCULATED STATS (base stats, no parent) ---
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

// --- RELATIONSHIPS ---
// Regen relationships
relate(Ids.STAT_STAMINA_REGEN, Relation.REGEN, Ids.STAT_STAMINA)
relate(Ids.STAT_FOCUS_REGEN, Relation.REGEN, Ids.STAT_FOCUS)
relate(Ids.STAT_MANA_REGEN, Relation.REGEN, Ids.STAT_MANA)
relate(Ids.STAT_MOXIE_REGEN, Relation.REGEN, Ids.STAT_MOXIE)

relate(Ids.STAT_STAMINA, Relation.RESOURCE, Ids.STAT_BODY)
relate(Ids.STAT_FOCUS, Relation.RESOURCE, Ids.STAT_MIND)
relate(Ids.STAT_MANA, Relation.RESOURCE, Ids.STAT_MAGIC)
relate(Ids.STAT_MOXIE, Relation.RESOURCE, Ids.STAT_CHARISMA)
