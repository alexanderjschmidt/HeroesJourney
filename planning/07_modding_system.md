# 🔧 **Modding System**

## **Mod Architecture**

Heroes Journey uses a robust modding architecture that cleanly separates game content (mods) from the underlying game engine and logic (core). This is achieved through the `modlib` module, which acts as a stable, safe API layer for all mod content.

### **Modlib as the API Layer**
* All interfaces, DSLs (domain-specific languages), and registries that mods need are exposed via `modlib`
* Mods never access core implementation details directly; they interact only with the interfaces and builders provided by `modlib`
* The `gameMod` entrypoint, all content DSLs (for actions, items, quests, etc.), and helpers (like localization and UI context) are provided by `modlib`

### **Mods as Content Packages**
* Mods are written as Kotlin scripts (`.kts`) that use only `modlib` imports
* Content such as actions, challenges, items, and quests are defined using the DSLs and interfaces from `modlib`
* Mods are loaded at runtime, registered, and validated through the modlib system, ensuring safety and stability

### **Core as the Implementation**
* The core game engine implements all the interfaces defined in `modlib` and wires up the actual logic and data structures
* At startup, the core registers its implementations with `modlib`, so all mod content is executed using the real game logic, but only through the safe API

### **Benefits**
* **Safety**: Mods cannot break or corrupt the game engine, as they have no access to internals
* **Flexibility**: New content and features can be added or hot-reloaded without changing the core codebase
* **Extensibility**: Third-party modders can create new content using only the documented, stable APIs in `modlib`
* **Maintainability**: The separation ensures that engine changes do not break mods, as long as the `modlib` API remains stable

## **Mod Loading System**

### **ScriptModLoader**
* **Intelligent mod discovery** - Automatically finds and loads mod files
* **`mod.kts` convention** - Only files named `mod.kts` are treated as main mod files
* **Fast discovery** - Skips non-mod files during initial scan for improved performance
* **Strict validation** - Ensures `mod.kts` files return proper `GameMod` objects
* **Parallel loading** - Optional parallel script loading for improved performance
* **Directory loading** - Load all `.kts` files from a directory with `includeScriptsFromDirectory()`

### **Mod Loading Features**
* **`includeScript(path)`** - Load a single script file
* **`includeScripts(paths..., parallel = false)`** - Load multiple scripts (sequential or parallel)
* **`includeScriptsFromDirectory(dir, parallel = false)`** - Load all `.kts` files from a directory
* **Parallel processing** - Optional multithreaded loading for improved performance
* **Error handling** - Comprehensive error reporting and timeout protection
* **Debug logging** - Detailed logging for mod loading process

## **Basegame Mod Structure**

The core mod containing all essential game content, structured as a unified mod that includes all content files:

### **Main Mod File (`mods/basegame/mod.kts`)**
* **`mod.kts`** - Main mod file that returns a `GameMod` object and includes all other content

### **Content Files (included by main mod)**
* **`actions/`** - Player actions and abilities organized by category:
  * `core_actions.kts` - Basic actions (workout, study, etc.)
  * `challenge_actions.kts` - Challenge-related actions
  * `travel_actions.kts` - Movement and exploration actions
  * `delve_actions.kts` - Dungeon and exploration actions
  * `options_actions.kts` - Menu and UI actions
* **`quests.kts`** - Quest definitions and completion logic
* **`items.kts`** - Item definitions, properties, and effects
* **`textures.kts`** - Animation and sprite definitions for all 135 challenges
* **`terrains.kts`** - Terrain type definitions and properties

### **Challenge Categories**
* **`challenges/`** - 9 subdirectories containing 135 total challenges:
  * `demon_challenges.kts` - 15 Demon challenges
  * `dragon_challenges.kts` - 15 Dragon challenges
  * `holy_challenges.kts` - 15 Holy challenges
  * `humanoid_challenges.kts` - 15 Humanoid challenges
  * `humanoid_ii_challenges.kts` - 15 Humanoid II challenges
  * `magical_challenges.kts` - 15 Magical challenges
  * `monster_challenges.kts` - 15 Monster challenges
  * `undead_challenges.kts` - 15 Undead challenges
  * `vermin_challenges.kts` - 15 Vermin challenges

## **Content DSLs**

### **Action DSL**
```kotlin
action {
    id = Ids.MY_ACTION
    requirementsMetFn = { ShowAction.YES }
    onHoverFn = { }
    onSelectFn = { input -> StringResult("You did the thing!") }
    inputDisplayNameFn = { "Do the Thing" }
    turnCooldown = 0
    factionCooldown = false
}.register()
```

### **Challenge DSL**
```kotlin
challenge {
    id = Ids.CHALLENGE_EXAMPLE
    render = Ids.CREATURE_SPRITE
    challengeType = Ids.CHALLENGE_TYPE_BODY
}.register()
```

### **Quest DSL**
```kotlin
quest {
    id = Ids.MY_QUEST
    fameReward = 10
    cost { 
        stat(Ids.STAT_VALOR, 2) 
    }
    rewards { 
        stat(Ids.STAT_INSIGHT, 1) 
    }
}.register()
```

### **Item DSL**
```kotlin
item {
    id = Ids.MY_ITEM
    subType = ItemType.Misc
    weight = 1
}.register()
```

### **Stat DSL**
```kotlin
stat {
    id = Ids.STAT_BODY
    min = 1
    max = 10
    group(Ids.GROUP_BASESTATS)
    group(Ids.GROUP_BODY)
}.register()
```

### **Biome DSL**
```kotlin
biome {
    id = Ids.BIOME_KINGDOM
    baseTerrain = Ids.TERRAIN_PLAINS
    feature { 
        featureTypeId = Ids.KINGDOM
        minDist = 0
        minInRegion = 1
        maxInRegion = 1 
    }
}.register()
```

## **Registry System**

### **Centralized Registration**
* **Registry objects** - Centralized storage for all game content
* **Type safety** - Compile-time checking of content types
* **Lookup efficiency** - Fast content retrieval by ID
* **Validation** - Content validation during registration

### **Registry Types**
* **ActionManager** - Player actions and abilities
* **ChallengeManager** - Challenge definitions and logic
* **QuestManager** - Quest definitions and completion
* **ItemManager** - Item definitions and properties
* **StatManager** - Character stats and attributes
* **BiomeManager** - World generation biomes
* **FeatureTypeManager** - World features and locations

### **Registry Usage**
```kotlin
// Register content
Registries.ActionManager.register(myAction)

// Lookup content
val action = Registries.ActionManager[actionId]

// Iterate content
Registries.ChallengeManager.values().forEach { challenge ->
    // Process challenge
}
```

## **Mod Development Workflow**

### **Content Creation**
* **Define content** - Use DSLs to create game content
* **Modular organization** - Separate content into logical files and directories
* **Unified loading** - Single main mod file includes all content files
* **Asset integration** - Link sprites and animations to game content

### **Balance Testing**
* **Iterate on approach usage** - Test different challenge approaches
* **Reward distribution** - Balance renown rewards across challenges
* **Thematic consistency** - Ensure descriptions match animations and approaches
* **Progression testing** - Verify character development paths

### **Mod Organization**
* **Content separation** - Game logic separate from content definitions
* **Easy iteration** - Quick changes to challenges, rewards, and balance
* **Performance optimized** - Fast mod discovery and parallel loading
* **Modular organization** - Clean separation of content by type and category

## **Modding Benefits**

### **Content Separation**
* Game logic separate from content definitions
* Easy iteration on game balance and content
* Modular organization by content type

### **Performance Optimization**
* Fast mod discovery and parallel loading
* Efficient content registration and lookup
* Optimized asset loading and caching

### **Community Expansion**
* Third-party mods for additional content
* Community-driven content creation
* Mod sharing and distribution

### **Testing Flexibility**
* Isolated testing of specific content categories
* Easy content validation and testing
* Rapid iteration on game balance

## **Advanced Modding Features**

### **Hot-Reloading**
* **Runtime updates** - Mods can be reloaded without restarting the game
* **Content updates** - Add new content during gameplay
* **Balance adjustments** - Modify existing content on the fly
* **Error recovery** - Graceful handling of mod errors

### **Parallel Loading**
* **Concurrent processing** - Load multiple mods simultaneously
* **Performance improvement** - Faster mod loading times
* **Resource optimization** - Efficient use of system resources
* **Error isolation** - Prevent one mod from blocking others

### **Directory Loading**
* **Bulk loading** - Load entire directories of scripts
* **Organized content** - Group related content in directories
* **Easy management** - Simple file organization for mods
* **Scalable structure** - Support for large mod collections

### **Error Handling**
* **Comprehensive validation** - Check mod content for errors
* **Detailed reporting** - Clear error messages and diagnostics
* **Graceful degradation** - Continue loading other mods if one fails
* **Debug information** - Detailed logging for troubleshooting

## **Modding Best Practices**

### **Content Organization**
* **Logical grouping** - Organize content by type and theme
* **Consistent naming** - Use clear, descriptive names for content
* **Documentation** - Include comments explaining complex content
* **Version control** - Track changes to mod content

### **Performance Considerations**
* **Efficient loading** - Minimize mod loading time
* **Memory usage** - Avoid excessive memory consumption
* **Asset optimization** - Optimize images and animations
* **Caching strategies** - Cache frequently used content

### **Compatibility**
* **API stability** - Use stable modlib APIs
* **Version checking** - Verify compatibility with game version
* **Dependency management** - Handle mod dependencies properly
* **Backward compatibility** - Support older mod versions

### **Testing and Validation**
* **Content testing** - Test all mod content thoroughly
* **Balance validation** - Ensure balanced gameplay
* **Performance testing** - Verify mod performance impact
* **Integration testing** - Test mod interactions 