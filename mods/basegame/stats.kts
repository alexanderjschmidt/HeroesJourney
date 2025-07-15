import heroes.journey.entities.tagging.group
import heroes.journey.entities.tagging.stat
import heroes.journey.modlib.Ids
import heroes.journey.registries.Registries

// --- GROUPS ---
group(Ids.GROUP_BODY).register()
group(Ids.GROUP_MIND).register()
group(Ids.GROUP_MAGIC).register()
group(Ids.GROUP_CHARISMA).register()
group(Ids.GROUP_BASESTATS).register()
group(Ids.GROUP_RENOWN).register()

// --- BASE STATS ---
stat(Ids.STAT_BODY) {
    group(Ids.GROUP_BASESTATS)
    group(Ids.GROUP_BODY)
}.register()
stat(Ids.STAT_MIND) {
    group(Ids.GROUP_BASESTATS)
    group(Ids.GROUP_MIND)
}.register()
stat(Ids.STAT_MAGIC) {
    group(Ids.GROUP_BASESTATS)
    group(Ids.GROUP_MAGIC)
}.register()
stat(Ids.STAT_CHARISMA) {
    group(Ids.GROUP_BASESTATS)
    group(Ids.GROUP_CHARISMA)
}.register()

// --- RENOWN STATS ---
stat(Ids.STAT_VALOR) {
    min = 0
    group(Ids.GROUP_RENOWN)
}.register()
stat(Ids.STAT_INSIGHT) {
    min = 0
    group(Ids.GROUP_RENOWN)
}.register()
stat(Ids.STAT_ARCANUM) {
    min = 0
    group(Ids.GROUP_RENOWN)
}.register()
stat(Ids.STAT_INFLUENCE) {
    min = 0
    group(Ids.GROUP_RENOWN)
}.register()

// --- CONFLUENCE STATS (complex) ---
// BODY
stat(Ids.STAT_MIGHT) {
    group(Ids.GROUP_BODY)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_BODY)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_BODY)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_BODY))) / 3 + it.getDirect(Ids.STAT_MIGHT)
    }
}.register()
stat(Ids.STAT_SKILL) {
    group(Ids.GROUP_BODY)
    group(Ids.GROUP_MIND)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_BODY)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_BODY)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_MIND))) / 3 + it.getDirect(Ids.STAT_SKILL)
    }
}.register()
stat(Ids.STAT_EMPOWERMENT) {
    group(Ids.GROUP_BODY)
    group(Ids.GROUP_MAGIC)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_BODY)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_BODY)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_MAGIC))) / 3 + it.getDirect(Ids.STAT_EMPOWERMENT)
    }
}.register()
stat(Ids.STAT_CHIVALRY) {
    group(Ids.GROUP_BODY)
    group(Ids.GROUP_CHARISMA)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_BODY)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_BODY)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_CHARISMA))) / 3 + it.getDirect(Ids.STAT_CHIVALRY)
    }
}.register()
// MIND
stat(Ids.STAT_TECHNIQUE) {
    group(Ids.GROUP_MIND)
    group(Ids.GROUP_BODY)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_MIND)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_MIND)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_BODY))) / 3 + it.getDirect(Ids.STAT_TECHNIQUE)
    }
}.register()
stat(Ids.STAT_LOGIC) {
    group(Ids.GROUP_MIND)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_MIND)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_MIND)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_MIND))) / 3 + it.getDirect(Ids.STAT_LOGIC)
    }
}.register()
stat(Ids.STAT_CONCENTRATION) {
    group(Ids.GROUP_MIND)
    group(Ids.GROUP_MAGIC)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_MIND)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_MIND)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_MAGIC))) / 3 + it.getDirect(Ids.STAT_CONCENTRATION)
    }
}.register()
stat(Ids.STAT_CUNNING) {
    group(Ids.GROUP_MIND)
    group(Ids.GROUP_CHARISMA)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_MIND)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_MIND)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_CHARISMA))) / 3 + it.getDirect(Ids.STAT_CUNNING)
    }
}.register()
// MAGIC
stat(Ids.STAT_ENCHANTING) {
    group(Ids.GROUP_MAGIC)
    group(Ids.GROUP_BODY)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_MAGIC)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_MAGIC)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_BODY))) / 3 + it.getDirect(Ids.STAT_ENCHANTING)
    }
}.register()
stat(Ids.STAT_ILLUSION) {
    group(Ids.GROUP_MAGIC)
    group(Ids.GROUP_MIND)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_MAGIC)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_MAGIC)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_MIND))) / 3 + it.getDirect(Ids.STAT_ILLUSION)
    }
}.register()
stat(Ids.STAT_SORCERY) {
    group(Ids.GROUP_MAGIC)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_MAGIC)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_MAGIC)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_MAGIC))) / 3 + it.getDirect(Ids.STAT_SORCERY)
    }
}.register()
stat(Ids.STAT_BEWITCHING) {
    group(Ids.GROUP_MAGIC)
    group(Ids.GROUP_CHARISMA)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_MAGIC)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_MAGIC)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_CHARISMA))) / 3 + it.getDirect(Ids.STAT_BEWITCHING)
    }
}.register()
// CHARISMA
stat(Ids.STAT_BRAVADO) {
    group(Ids.GROUP_CHARISMA)
    group(Ids.GROUP_BODY)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_CHARISMA)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_CHARISMA)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_BODY))) / 3 + it.getDirect(Ids.STAT_BRAVADO)
    }
}.register()
stat(Ids.STAT_PERSUASION) {
    group(Ids.GROUP_CHARISMA)
    group(Ids.GROUP_MIND)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_CHARISMA)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_CHARISMA)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_MIND))) / 3 + it.getDirect(Ids.STAT_PERSUASION)
    }
}.register()
stat(Ids.STAT_MESMERISM) {
    group(Ids.GROUP_CHARISMA)
    group(Ids.GROUP_MAGIC)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_CHARISMA)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_CHARISMA)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_MAGIC))) / 3 + it.getDirect(Ids.STAT_MESMERISM)
    }
}.register()
stat(Ids.STAT_CHARM) {
    group(Ids.GROUP_CHARISMA)
    formula = {
        (it.getDirect(Registries.StatManager.get(Ids.STAT_CHARISMA)) + it.getDirect(
            Registries.StatManager.get(Ids.STAT_CHARISMA)
        ) + it.getDirect(Registries.StatManager.get(Ids.STAT_CHARISMA))) / 3 + it.getDirect(Ids.STAT_CHARM)
    }
}.register()

// --- CALCULATED STATS ---
stat(Ids.STAT_SPEED) {
    group(Ids.GROUP_BODY)
    formula = { it.getDirect(Registries.StatManager.get(Ids.STAT_BODY)) }
}.register()
stat(Ids.STAT_VISION) {
    group(Ids.GROUP_BODY)
    formula = { it.getDirect(Registries.StatManager.get(Ids.STAT_BODY)) + 3 }
}.register()
stat(Ids.STAT_CARRY_CAPACITY) {
    max = 100
    group(Ids.GROUP_BODY)
    formula = { it.getDirect(Registries.StatManager.get(Ids.STAT_BODY)) * 10 }
}.register()
