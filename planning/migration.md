# Modlib Migration: Final Core Dependency Removal

This document tracks the final steps needed to fully decouple mods from core code, ensuring all mod scripts depend only on `modlib` APIs.

---

## Registry/Registrable Migration Plan

To decouple mods from core while maintaining full power in core, all registry and registrable abstractions should live in `modlib`, but core registries should store and return the core types. Mods interact only with interface types. This is achieved by:

1. **Move Registry and Registrable to modlib**
   - Generic over the interface type (e.g., `IQuest`).
2. **Core Registry Implements/Extends Modlib Registry**
   - Core registry is typed to the core type (e.g., `Quest`).
   - Core can override registration/lookup for core-specific logic.
3. **Registration Pattern**
   - Mods use the interface/DSL, which calls into the modlib registry.
   - Core registry maps interface types to core types as needed.
4. **Lookup Pattern**
   - Mods get back the interface type (`IQuest`), core can use the core type directly.
   - Registry can provide a method for core to get the implementation type if needed (e.g., `getCore(id): Quest`).
5. **At startup, core wires up the modlib registry singleton to the core implementation.**

**Benefits:**
- Mods only see and use the interface, never the core type.
- Core can use the full implementation type for all internal logic.
- Registries are unified and extensible.

---

## Outstanding Core Dependencies in Mods

### 1. Attributes (mods/basegame/items.kts)
- **Current:** `import heroes.journey.entities.tagging.Attributes`
- **Solution:** Use the `attributes` DSL from `modlib` for all attribute construction and manipulation. Ensure all usages are via the DSL and not the core class.
- **Action:**
  - [x] Refactor all usages of `Attributes` in mod scripts to use the `attributes` DSL.
  - [x] Remove direct imports of `Attributes` from mods.

### 2. Stat (mods/basegame/actions/challenge_actions.kts)
- **Current:** `import heroes.journey.entities.tagging.Stat`
- **Solution:** Provide an `IStat` interface and a stat DSL in `modlib` if not already present. Ensure all stat references in mods use the interface/DSL. Use the registry pattern below for stat lookup.
- **Action:**
  - [x] Create or verify `IStat` and stat DSL in `modlib`.
  - [x] Refactor mod usages to use only the interface/DSL.

### 3. StatManager (mods/basegame/actions/challenge_actions.kts)
- **Current:** `import heroes.journey.mods.Registries.StatManager`
- **Solution:** Move the stat registry to `modlib` using the Registry/Registrable migration plan above. Core registry stores core Stat, mods use IStat.
- **Action:**
  - [x] Move stat registry to `modlib` as interface-driven.
  - [x] Wire up core registry at startup.
  - [x] Refactor mod usages.

### 4. HUD and Lang (mods/basegame/actions/travel_actions.kts)
- **Current:** `import heroes.journey.ui.HUD`, `import heroes.journey.utils.Lang`
- **Solution:** Abstract UI and localization helpers into `modlib` interfaces or utility functions.
- **Action:**
  - [ ] Add `IHud` and `ILang` or equivalent helpers to `modlib`.
  - [ ] Refactor mod usages.

### 5. Registries/QuestManager (mods/basegame/actions/quest_actions.kts)
- **Current:** `import heroes.journey.mods.Registries`, `import heroes.journey.mods.Registries.QuestManager`
- **Solution:** Move the quest registry to `modlib` using the Registry/Registrable migration plan above. Core registry stores core Quest, mods use IQuest.
- **Action:**
  - [x] Move quest registry to `modlib` as interface-driven.
  - [x] Wire up core registry at startup.
  - [x] Refactor mod usages.

### 6. Options (mods/basegame/actions/options_actions.kts)
- **Current:** `import heroes.journey.utils.input.Options`
- **Solution:** Move or abstract the options system into `modlib`.
- **Action:**
  - [x] Add options system to `modlib`.
  - [x] Refactor mod usages.

### 7. gameMod (mods/basegame/mod.kts)
- **Current:** `import heroes.journey.mods.gameMod`
- **Solution:** Move the `gameMod` entrypoint to `modlib` if possible, or provide a modlib interface/DSL for mod registration.
- **Action:**
  - [ ] Move or abstract `gameMod` to `modlib`.
  - [ ] Refactor mod usages.

---

## Progress Checklist
- [ ] All mod scripts import only from `heroes.journey.modlib.*`
- [ ] All DSLs, registries, and helpers needed by mods are available in `modlib`
- [ ] No direct references to core classes or utilities remain in mods 
