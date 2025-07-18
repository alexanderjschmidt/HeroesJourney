# Heroes Journey Modlib

Welcome to **modlib**, the official modding API for Heroes Journey! This library provides a safe, stable, and powerful interface for creating new game content—without ever touching the core game engine.

## What is modlib?

`modlib` is the bridge between your mod scripts and the Heroes Journey game engine. It exposes all the interfaces, DSLs (domain-specific languages), and registries you need to define new actions, items, stats, quests, challenges, world features, and more. Mods interact **only** with `modlib`—never with the core engine directly.

## Adding modlib as a Dependency

If you are building your own mod project, add the following to your `build.gradle`:

```groovy
dependencies {
    implementation project(":modlib")
    // or, if published to a repository:
    // implementation "com.heroesjourney:modlib:<version>"
}
```

## Getting Started: Creating a Mod

### 1. Create a `mod.kts` File

Every mod starts with a `mod.kts` file. Use the `gameMod` DSL to define your mod and include content scripts:

```kotlin
import heroes.journey.modlib.gameMod

gameMod("My Cool Mod") {
    includeScript("actions.kts")
    includeScript("items.kts")
    includeScriptsFromDirectory("challenges", "challenges")
}
```

### 2. Define Content Using DSLs

Use the provided DSLs to define new content. Here are examples for each major type:

#### Actions
```kotlin
import heroes.journey.modlib.actions.action
import heroes.journey.modlib.Ids

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

#### Items
```kotlin
import heroes.journey.modlib.items.item
import heroes.journey.modlib.items.ItemType
import heroes.journey.modlib.Ids

item(
    id = Ids.MY_ITEM,
    subType = ItemType.Misc,
    weight = 1
).register()
```

#### Stats & Groups
```kotlin
import heroes.journey.modlib.attributes.stat
import heroes.journey.modlib.attributes.group
import heroes.journey.modlib.Ids

group(Ids.MY_GROUP).register()
stat(
    id = Ids.MY_STAT,
    min = 0,
    max = 10,
    groups = listOf(group(Ids.MY_GROUP))
).register()
```

#### Buffs
```kotlin
import heroes.journey.modlib.misc.buff
import heroes.journey.modlib.Ids

buff(Ids.MY_BUFF, turnsBuffLasts = 3).register()
```

#### Quests
```kotlin
import heroes.journey.modlib.misc.quest
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.Ids

quest(
    id = Ids.MY_QUEST,
    cost = attributes(Ids.STAT_VALOR to 2),
    rewards = attributes(Ids.STAT_INSIGHT to 1),
    fameReward = 10
).register()
```

#### Challenges
```kotlin
import heroes.journey.modlib.misc.challenge
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.Ids

challenge(
    id = Ids.MY_CHALLENGE,
    render = Ids.PLAYER_SPRITE,
    approaches = listOf(Ids.STAT_MIGHT, Ids.STAT_LOGIC, Ids.STAT_CHARM),
    reward = attributes("valor" to 2)
).register()
```

#### Renderables & Texture Maps
```kotlin
import heroes.journey.modlib.art.stillRenderable
import heroes.journey.modlib.art.textureMap
import heroes.journey.modlib.Ids

val myTexture = textureMap("my_texture", "my_texture.png", 16, 16).register()
stillRenderable("my_sprite", myTexture.id, 0, 0).register()
```

#### Biomes & Terrain
```kotlin
import heroes.journey.modlib.worldgen.biome
import heroes.journey.modlib.worldgen.terrain
import heroes.journey.modlib.worldgen.featureGenerationData
import heroes.journey.modlib.Ids

biome(
    id = Ids.MY_BIOME,
    baseTerrain = Ids.TERRAIN_PLAINS,
    featureGenerationData = listOf(
        featureGenerationData(Ids.TOWN, minDist = 5, minInRegion = 1, maxInRegion = 3)
    )
).register()

terrain(Ids.MY_TERRAIN, terrainCost = 2).register()
```

### 3. Registering Content and Using IDs
- Always use IDs from `Ids.kt` or define your own unique string IDs.
- Call `.register()` on every object you define to add it to the game.

### 4. Accessing Registries and Localization
- Use `Registries` to look up objects by ID (e.g., `Registries.ItemManager[Ids.MY_ITEM]`).
- Use `Lang.instance.get("my_key")` for localization.

### 5. Best Practices
- **Only import from `heroes.journey.modlib.*`**—never from core or engine packages.
- Use the provided DSLs and interfaces for all content.
- Use `.register()` to ensure your content is available in-game.
- Use IDs consistently to avoid conflicts.
- Take advantage of hot-reloading for rapid iteration.
- Read the Javadocs for detailed API documentation (see source files or generated docs).

### 6. Hot-Reloading and Safety
- Mods can be reloaded at runtime without restarting the game.
- The modlib API ensures mods cannot break or corrupt the game engine.
- All content is validated and registered through safe interfaces.

---

## More Information
- See the source files in `modlib/src/main/java/heroes/journey/modlib/` for full API details and Javadocs.
- For advanced examples, see the basegame mod scripts in `mods/basegame/`.
- Join the community for help, tips, and sharing your mods!

Happy modding! 