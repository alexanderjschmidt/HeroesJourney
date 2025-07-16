# 🛠️ **modlib Migration Plan**

*Goal: Decouple mods from core by exposing only interfaces and DSLs via modlib, so mods depend solely on modlib, not core. This will be achieved by introducing interfaces for all Registrable objects and their DSLs, and hiding implementation details from modders.*

---

## 📋 **Overview**

Currently, mods can access both `modlib` and `core`, allowing them to depend on internal implementation details. To improve maintainability and mod safety, we will:
- Expose only interfaces and DSLs for Registrable objects via `modlib`.
- Move or wrap all mod-facing DSLs into `modlib`.
- Hide all core implementation details from mods.
- Ensure all mod content is defined against interfaces, not concrete classes.

---

## 🧮 **Registrable Complexity Scores**

*Complexity is scored by the number of other objects in the tracking table that a Registrable references directly (as fields, constructor args, or in its DSL). Higher scores mean more dependencies and likely more migration effort.*

| Registrable Type | References | Complexity Score | Notes |
|------------------|------------|------------------|-------|
| Renderable       | 0          | 0                | Only references TextureRegion (not tracked); very simple |
| Buff             | 1 (Attributes) | 1           | Attributes is not a Registrable, so low complexity |
| Group            | 0          | 0                | Standalone, only has ID |
| Stat             | 1 (Group)  | 1                | References Group(s) in DSL and as field |
| ItemSubType      | 1 (ItemType) | 1              | ItemType is not a Registrable, so low complexity |
| Item             | 2 (ItemSubType, Attributes) | 2 | References ItemSubType and Attributes |
| Quest            | 1 (Attributes) | 1           | Uses Attributes for cost/reward |
| Challenge        | 1 (Stat)   | 1                | References Stat array for approaches |
| Biome            | 1 (FeatureType) | 1           | References FeatureType in featureGenerationData |
| FeatureType      | 0          | 0                | Standalone, only has ID and a generator function |
| Terrain          | 0          | 0                | Standalone, only has ID and terrainCost |
| TextureMap       | 0          | 0                | Standalone, only has ID and asset info |
| TileBatch        | 2 (TileLayout, Terrain) | 2   | References TileLayout and Terrain |
| TileLayout       | 0          | 0                | Standalone, only has ID, asset, and roles |
| Action           | 0-2+ (ShowAction, ActionResult, GameState, etc.) | 3+ | High: references enums, result types, and sometimes GameState; often used with other Registrables |

*Scoring: 0 = standalone, 1 = references 1 other, 2 = references 2, 3+ = references 3 or more or has complex dependencies.*

---

## 🏗️ **Step-by-Step Migration Plan**

### 1. **Catalog All Registrable Types**
- [ ] List all classes extending `Registrable` (e.g. Stat, Group, Item, Action, Buff, Quest, Challenge, Biome, FeatureType, Terrain, TileBatch, TileLayout, Renderable, etc.).
- [ ] For each, note if a DSL exists and where it is defined.

### 2. **Define Modlib Interfaces**
- [ ] For each Registrable type, define a public interface in `modlib` (e.g. `IStat`, `IGroup`, `IItem`, etc.).
- [ ] Interfaces should expose only what modders need (ID, registration, DSL properties, etc.).
- [ ] Add JavaDocs/KDoc to clarify intended usage for modders.

### 3. **Create/Move DSL Interfaces to Modlib**
- [ ] For each Registrable with a DSL, move the DSL builder and entrypoint function to `modlib` as an interface or abstract builder.
- [ ] If a DSL does not exist, create a minimal one in `core` and expose an interface in `modlib`.
- [ ] Ensure all DSLs only use modlib interfaces/types.

### 4. **Refactor Core to Implement Interfaces**
- [ ] Update all core Registrable implementations to implement the new modlib interfaces.
- [ ] Ensure all DSLs in core implement/extend the modlib DSL interfaces.

### 5. **Update Registries to Use Interfaces**
- [ ] Refactor all `Registries` and `Registry` types to use interface types for mod-facing APIs.
- [ ] Ensure registration and lookup return interface types to mods.

### 6. **Restrict Mod Access to Core**
- [ ] Update build scripts so mods only depend on `modlib`, not `core`.
- [ ] Move any remaining mod-facing helpers/utilities to `modlib`.

### 7. **Migration and Testing**
- [ ] Migrate a sample mod to use only `modlib` interfaces and DSLs.
- [ ] Test for breakages, missing APIs, or usability issues.
- [ ] Iterate and document any pain points for modders.

### 8. **Create modlib README**
- [ ] Write a comprehensive `modlib/README.md` for modders, covering:
    - How to use modlib interfaces and DSLs
    - Example mod definitions
    - Best practices and gotchas
    - Link to generated Javadocs

---

## 🧩 **Example: Stat Migration**

1. Define `IStat` interface in `modlib` (ID, min/max, groups, formula, etc.).
2. Move or wrap the `stat { ... }` DSL in `modlib` as an interface/abstract builder.
3. Update `Stat` in core to implement `IStat`.
4. Ensure all mod stat definitions use only the interface and DSL from `modlib`.

---

## 📚 **Tracking Table**

| Registrable Type | Has DSL? | DSL in modlib? | Interface in modlib? | Core implements interface? |
|------------------|----------|---------------|----------------------|---------------------------|
| Stat             | Yes      | [x]           | [x]                 | [x]                      |
| Group            | Yes      | [x]           | [x]                 | [x]                      |
| ItemSubType      | Yes      | [x]           | [x]                 | [x]                      |
| Item             | Yes      | [x]           | [x]                 | [x]                      |
| Action           | Yes      | [ ]           | [ ]                 | [ ]                      |
| Buff             | Yes      | [x]           | [x]                 | [x]                      |
| Quest            | Partial  | [ ]           | [ ]                 | [ ]                      |
| Challenge        | Yes      | [ ]           | [ ]                 | [ ]                      |
| Biome            | Yes      | [ ]           | [ ]                 | [ ]                      |
| FeatureType      | Yes      | [ ]           | [ ]                 | [ ]                      |
| Terrain          | Yes      | [x]           | [x]                 | [x]                      |
| TileBatch        | Yes      | [x]           | [x]                 | [x]                      |
| TileLayout       | Yes      | [x]           | [x]                 | [x]                      |
| Renderable       | Yes      | [x]           | [x]                 | [x]                      |
| TextureMap       | Yes      | [x]           | [x]                 | [x]                      |

---

## 🚦 **Next Steps**
- **Start with the least used/least complex Registrable types** (e.g. Renderable, Buff, Biome, etc.).
- For each, follow the checklist above.
- Progress to more complex types (Stat, Group, Item, Action) only after simple ones are migrated.
- Regularly update this plan and the tracking table as progress is made.

---

## 📝 **Notes**
- All new interfaces and DSLs in `modlib` should be stable and well-documented.
- modlib should be well laid out with clear packages and namespaces for ease of use and discoverability by modders.
- Avoid exposing any implementation details or core-only APIs to mods.
- Use semantic versioning for `modlib` to communicate breaking changes to modders.
- The `modlib/README.md` should be kept up to date alongside Javadocs to provide a friendly entry point for modders. 

---

## 📝 **Reference: Group Migration Example**

This section documents the concrete steps taken to migrate the Group Registrable to the new modlib/core separation. Use this as a template for future Registrable migrations.

### 1. Define Interfaces and DSL in modlib (Kotlin)
- Created a single `Group.kt` file in `modlib` (Kotlin) containing:
    - `IGroup` interface (exposes `val id: String` and `fun register(): IGroup`)
    - `GroupDSL` interface (exposes `fun group(id: String): IGroup`)
    - `GroupDSLProvider` singleton
    - Top-level `fun group(id: String): IGroup` DSL entrypoint
- This keeps the modlib lightweight and idiomatic.

### 2. Implement the DSL in core
- Implemented `GroupDSLImpl` in `core/mods` package, returning a real `Group` (which implements `IGroup`).
- Created a new `ModlibDSLSetup.kt` file in `core/mods` to hold the `setupModlibDSLs()` function, which registers all modlib DSL providers in one place for future extensibility.

### 3. Wire up the provider before mod loading
- In `LoadingScreen.java`, called `setupModlibDSLs()` before any mod finding/loading begins. This ensures the modlib DSL always delegates to the core implementation at runtime.

### 4. Update mod scripts to use the modlib DSL
- Updated all mod scripts to import and use `group` from `modlib` (e.g., `import heroes.journey.modlib.group`).
- Removed any direct references to core DSLs or implementations from mod scripts.

### 5. Result
- Mods now use only the modlib DSL and interfaces, but always get the real core implementation at runtime.
- The architecture is clean, modular, and ready for future Registrable migrations using the same pattern.
- **Group has been fully migrated and tested.**
- **Renderable has been fully migrated and tested.**

--- 

## 📝 Reference: Renderable Migration Example

This section documents the concrete steps taken to migrate the Renderable Registrable to the new modlib/core separation. Use this as a template for future Registrable migrations.

### 1. Define Interfaces and DSL in modlib (Kotlin)
- Created a single `Renderable.kt` file in `modlib` (Kotlin) containing:
    - `IRenderable` interface (exposes `val id: String` and `fun register(): IRenderable`)
    - `RenderableDSL` interface (exposes `fun stillRenderable(...)` and `fun animationRenderable(...)`)
    - `RenderableDSLProvider` singleton
    - Top-level `fun stillRenderable(...)` and `fun animationRenderable(...)` DSL entrypoints
- The DSL only exposes IDs (e.g., `textureMapId: String`), not libGDX types or core classes.

### 2. Implement the DSL in core
- Implemented `RenderableDSLImpl` in `core/mods` package, returning real `StillRenderable` and `AnimationRenderable` (which implement `IRenderable`).
- Always looks up `TextureMap` by ID, and sets animation play mode to `LOOP`.

### 3. Wire up the provider before mod loading
- In `setupModlibDSLs()` (in `core/mods/ModlibDSLSetup.kt`), registered the `RenderableDSLProvider` with the core implementation, before any mod loading.

### 4. Update mod scripts to use the modlib DSL
- Updated all mod scripts to import and use `stillRenderable` and `animationRenderable` from `modlib` (e.g., `import heroes.journey.modlib.stillRenderable`).
- All usages now pass IDs explicitly, not core objects.

### 5. Result
- Mods now use only the modlib DSL and interfaces for Renderable, but always get the real core implementation at runtime.
- The architecture is clean, modular, and ready for future Registrable migrations using the same pattern.
- **Renderable has been fully migrated and tested.** 

---

## 📝 Reference: TextureMap Migration Example

This section documents the concrete steps taken to migrate the TextureMap Registrable to the new modlib/core separation. Use this as a template for future Registrable migrations.

### 1. Define Interfaces and DSL in modlib (Kotlin)
- Created a single `TextureMap.kt` file in `modlib` (Kotlin) containing:
    - `ITextureMap` interface (exposes `val id: String`, `val location: String`, `val width: Int`, `val height: Int`, and `fun register(): ITextureMap`)
    - `TextureMapDSL` interface (exposes `fun textureMap(...)`)
    - `TextureMapDSLProvider` singleton
    - Top-level `fun textureMap(...)` DSL entrypoint
- The DSL only exposes primitive types and IDs, not libGDX types or core classes.

### 2. Implement the DSL in core
- Implemented `TextureMapDSLImpl` in `core/mods` package, returning real `TextureMap` (which implements `ITextureMap`).

### 3. Wire up the provider before mod loading
- In `setupModlibDSLs()` (in `core/mods/ModlibDSLSetup.kt`), registered the `TextureMapDSLProvider` with the core implementation, before any mod loading.

### 4. Update mod scripts to use the modlib DSL
- Updated all mod scripts to import and use `textureMap` from `modlib` (e.g., `import heroes.journey.modlib.textureMap`).
- All usages now pass IDs and primitive types explicitly, not core objects.

### 5. Result
- Mods now use only the modlib DSL and interfaces for TextureMap, but always get the real core implementation at runtime.
- The architecture is clean, modular, and ready for future Registrable migrations using the same pattern.
- **TextureMap has been fully migrated and tested.** 

---

## 📝 Reference: Terrain Migration Example

This section documents the concrete steps taken to migrate the Terrain Registrable to the new modlib/core separation. Use this as a template for future Registrable migrations.

### 1. Define Interfaces and DSL in modlib (Kotlin)
- Created a single `Terrain.kt` file in `modlib` (Kotlin) containing:
    - `ITerrain` interface (exposes `val id: String`, `val terrainCost: Int`, and `fun register(): ITerrain`)
    - `TerrainDSL` interface (exposes `fun terrain(id: String, terrainCost: Int = 1): ITerrain`)
    - `TerrainDSLProvider` singleton
    - Top-level `fun terrain(id: String, terrainCost: Int = 1): ITerrain` DSL entrypoint
- This keeps the modlib lightweight and idiomatic.

### 2. Implement the DSL in core
- Implemented `TerrainDSLImpl` in `core/mods` package, returning a real `Terrain` (which implements `ITerrain`).

### 3. Wire up the provider before mod loading
- In `setupModlibDSLs()` (in `core/mods/ModlibDSLSetup.kt`), registered the `TerrainDSLProvider` with the core implementation, before any mod loading.

### 4. Update mod scripts to use the modlib DSL
- Updated all mod scripts to import and use `terrain` from `modlib` (e.g., `import heroes.journey.modlib.terrain`).
- All usages now pass IDs via `Ids` constants, not raw strings.

### 5. Result
- Mods now use only the modlib DSL and interfaces for Terrain, but always get the real core implementation at runtime.
- The architecture is clean, modular, and ready for future Registrable migrations using the same pattern.
- **Terrain has been fully migrated and tested.** 

---

## 📝 Reference: TileLayout Migration Example

This section documents the concrete steps taken to migrate the TileLayout Registrable to the new modlib/core separation. Use this as a template for future Registrable migrations.

### 1. Define Interfaces and DSL in modlib (Kotlin)
- Created a single `TileLayout.kt` file in `modlib` (Kotlin) containing:
    - `ITileLayout` interface (exposes `val id: String`, `val path: String`, `val terrainRoles: List<String>`, and `fun register(): ITileLayout`)
    - `TileLayoutDSL` interface (exposes `fun tileLayout(id: String, path: String, terrainRoles: List<String>): ITileLayout`)
    - `TileLayoutDSLProvider` singleton
    - Top-level `fun tileLayout(id: String, path: String, terrainRoles: List<String>): ITileLayout` DSL entrypoint
- This keeps the modlib lightweight and idiomatic.

### 2. Implement the DSL in core
- Implemented `TileLayoutDSLImpl` in `core/mods` package, returning a real `TileLayout` (which implements `ITileLayout`).

### 3. Wire up the provider before mod loading
- In `setupModlibDSLs()` (in `core/mods/ModlibDSLSetup.kt`), registered the `TileLayoutDSLProvider` with the core implementation, before any mod loading.

### 4. Update mod scripts to use the modlib DSL
- Updated all mod scripts to import and use `tileLayout` from `modlib` (e.g., `import heroes.journey.modlib.tileLayout`).
- All usages now pass IDs and paths explicitly, not via builder blocks.

### 5. Result
- Mods now use only the modlib DSL and interfaces for TileLayout, but always get the real core implementation at runtime.
- The architecture is clean, modular, and ready for future Registrable migrations using the same pattern.
- **TileLayout has been fully migrated and tested.** 

---

## 📝 Reference: TileBatch Migration Example

This section documents the concrete steps taken to migrate the TileBatch Registrable to the new modlib/core separation. Use this as a template for future Registrable migrations.

### 1. Define Interfaces and DSL in modlib (Kotlin)
- Created a single `TileBatch.kt` file in `modlib` (Kotlin) containing:
    - `ITileBatch` interface (exposes all properties needed for mod DSL and `fun register(): ITileBatch`)
    - `TileBatchDSL` interface (exposes `fun tileBatch(...)` with all required parameters)
    - `TileBatchDSLProvider` singleton
    - Top-level `fun tileBatch(...)` DSL entrypoint
- This keeps the modlib lightweight and idiomatic.

### 2. Implement the DSL in core
- Implemented `TileBatchDSLImpl` in `core/mods` package, returning a real `TileBatch` (which implements `ITileBatch`).

### 3. Wire up the provider before mod loading
- In `setupModlibDSLs()` (in `core/mods/ModlibDSLSetup.kt`), registered the `TileBatchDSLProvider` with the core implementation, before any mod loading.

### 4. Update mod scripts to use the modlib DSL
- Updated all mod scripts to import and use `tileBatch` from `modlib` (e.g., `import heroes.journey.modlib.tileBatch`).
- All usages now pass IDs, layout, textureMap, and terrains explicitly, not via builder blocks.

### 5. Result
- Mods now use only the modlib DSL and interfaces for TileBatch, but always get the real core implementation at runtime.
- The architecture is clean, modular, and ready for future Registrable migrations using the same pattern.
- **TileBatch has been fully migrated and tested.** 

---

## 📝 Reference: Stat Migration Example

This section documents the concrete steps taken to migrate the Stat Registrable to the new modlib/core separation. Use this as a template for future Registrable migrations.

### 1. Define Interfaces and DSL in modlib (Kotlin)
- Created a single `Stat.kt` file in `modlib` (Kotlin) containing:
    - `IStat` interface (exposes `val id: String`, `val min: Int`, `val max: Int`, `val groups: List<IGroup>`, `val formula: (IAttributes) -> Int`, and `fun register(): IStat`)
    - `StatDSL` interface (exposes `fun stat(...)`)
    - `StatDSLProvider` singleton
    - Top-level `fun stat(...)` DSL entrypoint
- Created a new `IAttributes.kt` file in `modlib` for the stat container interface, used in formulas and APIs.

### 2. Implement the DSL in core
- Implemented `StatDSLImpl` in `core/mods` package, returning a real `Stat` (which implements `IStat`).
- Updated the core `Attributes` class to implement `IAttributes`.
- Ensured all formula lambdas and APIs use only the interface types.

### 3. Wire up the provider before mod loading
- In `setupModlibDSLs()` (in `core/mods/ModlibDSLSetup.kt`), registered the `StatDSLProvider` with the core implementation, before any mod loading.

### 4. Update mod scripts to use the modlib DSL
- Updated all mod scripts to import and use `stat` from `modlib` (e.g., `import heroes.journey.modlib.stat`).
- All usages now pass IDs and interface types explicitly, not core objects.

### 5. Result
- Mods now use only the modlib DSL and interfaces for Stat, but always get the real core implementation at runtime.
- The architecture is clean, modular, and ready for future Registrable migrations using the same pattern.
- **Stat has been fully migrated and tested.** 

---

## 📝 Reference: Item & ItemSubType Migration Example

This section documents the concrete steps taken to migrate the Item and ItemSubType Registrables to the new modlib/core separation. Use this as a template for future Registrable migrations.

### 1. Define Interfaces and DSLs in modlib (Kotlin)
- Created `IItemSubType` and `IItem` interfaces in `modlib`, exposing only mod-facing properties and methods.
- Created `ItemSubTypeDSL` and `ItemDSL` interfaces, singleton providers, and top-level DSL functions (`itemSubType`, `item`).
- Moved `ItemType` enum to `modlib` for use in DSLs and interfaces.
- Added `Ids` constants for all item and item subtype IDs.
- Added `AttributesDSL` for concise attribute definitions.

### 2. Implement the DSLs in core
- Implemented `ItemSubTypeDSLImpl` and `ItemDSLImpl` in `core/mods`, returning real core objects and handling type conversions.
- Registered providers in `setupModlibDSLs`.
- Implemented `AttributesDSLImpl` for attribute creation.

### 3. Update mod scripts to use the new DSLs
- Updated all mod scripts to use `itemSubType` and `item` from `modlib`, passing IDs from `Ids` and using the new attributes DSL.
- For core API calls, used registry lookups to convert from interface to concrete types as needed.

### 4. Result
- Mods now use only the modlib DSLs and interfaces for Item and ItemSubType, but always get the real core implementation at runtime.
- The architecture is clean, modular, and ready for future Registrable migrations using the same pattern.
- **Item and ItemSubType have been fully migrated and tested.** 

---

## 📝 Reference: Buff Migration Example

This section documents the concrete steps taken to migrate the Buff Registrable to the new modlib/core separation. Use this as a template for future Registrable migrations.

### 1. Define Interface and DSL in modlib (Kotlin)
- Created `IBuff` interface in `modlib`, exposing only mod-facing properties and methods.
- Created `BuffDSL` interface, singleton provider, and top-level DSL function (`buff`).
- Used the existing `IAttributes` and `attributes` DSL for buff attributes.

### 2. Implement the DSL in core
- Implemented `BuffDSLImpl` in `core/mods`, returning real core `Buff` objects and handling type conversions.
- Registered the provider in `setupModlibDSLs`.

### 3. Update mod scripts to use the new DSL
- Updated all mod scripts to use `buff` from `modlib`, passing IDs and using the new attributes DSL.

### 4. Result
- Mods now use only the modlib DSL and interface for Buff, but always get the real core implementation at runtime.
- The architecture is clean, modular, and ready for future Registrable migrations using the same pattern.
- **Buff has been fully migrated and tested.** 