# 🏗️ **Registrable Implementation Guide**

This document outlines the complete pattern for implementing a new registrable type in the Heroes Journey modlib system. A registrable is any game object that can be defined by mods and stored in a registry (e.g., Items, Stats, Actions, Quests, etc.).

---

## 📋 **Overview**

The registrable system now supports **two implementation patterns**:

### **Basic Registrables** (Simple Data Containers)
For registrables that are purely data containers with no complex functions or game logic. These have a simplified 4-component pattern:

1. **Implementation** (modlib) - Concrete class directly in modlib
2. **DSL Builder** (modlib) - Builder class for DSL
3. **Registry Definition** (modlib) - Storage and lookup
4. **Registry Initialization** (modlib) - Initialize in modlib startup

### **Advanced Registrables** (Complex Objects with Functions)
For registrables that have complex game logic, functions, or need to hide implementation details. These use the full 8-component pattern:

1. **Interface** (modlib) - Public API for mods
2. **DSL Interface** (modlib) - Builder pattern for mod definitions  
3. **DSL Provider** (modlib) - Singleton to wire up the DSL
4. **Registry Definition** (modlib) - Storage and lookup
5. **Core Implementation** (core) - Concrete class implementing the interface
6. **DSL Implementation** (core) - Builder implementation
7. **Registry Registration** (core) - Wire up to modlib registry
8. **DSL Registration** (core) - Wire up DSL provider

---

## 🎯 **Classification Guide**

### **Basic Registrables** (Move implementation to modlib)
These are simple data containers with no complex functions:

- **Group** - Just an ID, no functions
- **Buff** - Simple data container (turns, attributes)
- **ItemSubType** - Simple data container (parent type)
- **Terrain** - Simple data container (cost)
- **Biome** - Simple data container (base terrain, features)
- **FeatureType** - Simple data container (render ID)
- **TileLayout** - Simple data container (path, terrain roles)
- **TileBatch** - Simple data container (layout, texture, terrains)
- **TextureMap** - Simple data container (location, dimensions)
- **Renderable** - Simple data container (texture coordinates, animation data)

### **Advanced Registrables** (Keep current 8-component pattern)
These have complex game logic, functions, or need implementation hiding:

- **Action** - Complex with multiple functions, cooldowns, cost deduction
- **Stat** - Complex with formula calculations, min/max logic
- **Quest** - Complex with completion logic, cost/reward processing
- **Challenge** - Complex with power tier calculations
- **Approach** - Complex with requirement checking logic
- **Item** - Complex with subtype references and attribute processing

---

## 🧩 **Basic Registrable Pattern (4 Components)**

### **1. Implementation (modlib)**

Define the concrete class directly in modlib. Place in `modlib/src/main/java/heroes/journey/modlib/[category]/[Type].kt`:

```kotlin
package heroes.journey.modlib.[category]

import heroes.journey.modlib.registries.Registrable

/**
 * A [Type], used for [description].
 * This is a simple data container with no complex functions.
 */
class [Type](
    override val id: String,
    val property1: String,
    val property2: Int
) : Registrable(id) {
    
    override fun register(): [Type] {
        Registries.[Type]Manager.register(this)
        return this
    }
}
```

### **2. DSL Builder (modlib)**

Define the builder class for the DSL. Same file:

```kotlin
/**
 * Builder for defining a [type] in a natural DSL style.
 */
class [Type]Builder {
    var id: String = ""
    var property1: String = ""
    var property2: Int = 0
    // Add other properties as needed
}

/**
 * DSL entrypoint for defining a [type].
 */
fun [type](init: [Type]Builder.() -> Unit): [Type] {
    val builder = [Type]Builder()
    builder.init()
    return [Type](builder.id, builder.property1, builder.property2)
}
```

### **3. Core Integration (core)**

Update core files to reference the modlib registry:

**In `core/src/main/java/heroes/journey/mods/Registries.java`:**
```java
public static Registry<[Type]> [Type]Manager = 
    heroes.journey.modlib.registries.Registries.INSTANCE.get[Type]Manager();
```

**Note**: Kotlin automatically creates getters for `open val` properties, so `get[Type]Manager()` is available.

### **4. Registry Definition (modlib)**

Add to `modlib/src/main/java/heroes/journey/modlib/registries/Registries.kt`:

```kotlin
object Registries {
    // ... existing registries ...
    open val [Type]Manager: Registry<[Type]> = Registry()
}
```

**Note**: Using `open val` with direct initialization eliminates the need for separate initialization.

---

## 🧩 **Advanced Registrable Pattern (8 Components)**

### **1. Interface (modlib)**

Define the public interface that mods will use. Place in `modlib/src/main/java/heroes/journey/modlib/[category]/[Type].kt`:

```kotlin
package heroes.journey.modlib.misc

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a [Type], used for [description].
 * Mods should only use this interface, not implementation classes.
 */
interface I[Type] : IRegistrable {
    // Define properties that mods need to access
    val property1: String
    val property2: Int
    
    // Define functions that mods need to call
    fun complexFunction(input: Any): Result
    
    // Override register() to return the interface type
    override fun register(): I[Type]
}
```

### **2. DSL Interface (modlib)**

Define the builder interface for the DSL. Same file as the interface:

```kotlin
/**
 * Builder for defining a [type] in a natural DSL style.
 */
interface [Type]Builder {
    var id: String
    var property1: String
    var property2: Int
    // Add other properties as needed
}

/**
 * Interface for the [type] DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface [Type]DSL {
    fun [type](init: [Type]Builder.() -> Unit): I[Type]
}
```

### **3. DSL Provider (modlib)**

Define the singleton provider. Same file:

```kotlin
/**
 * Singleton provider for the [Type]DSL implementation.
 * The core game must set this before any mods are loaded.
 */
object [Type]DSLProvider {
    lateinit var instance: [Type]DSL
}
```

### **4. Registry Definition (modlib)**

Add to `modlib/src/main/java/heroes/journey/modlib/registries/Registries.kt`:

```kotlin
object Registries {
    // ... existing registries ...
    lateinit var [Type]Manager: Registry<I[Type]>
}
```

### **5. Core Implementation (core)**

Create the concrete class in `core/src/main/java/heroes/journey/entities/[category]/[Type].kt`:

```kotlin
package heroes.journey.entities.[category]

import heroes.journey.modlib.misc.I[Type]
import heroes.journey.modlib.registries.Registrable

class [Type](
    override val id: String,
    override val property1: String,
    override val property2: Int
) : Registrable(id), I[Type] {
    
    override fun complexFunction(input: Any): Result {
        // Complex implementation logic here
        return Result()
    }
    
    override fun register(): I[Type] {
        Registries.[Type]Manager.register(this)
        return this
    }
}
```

### **6. DSL Implementation (core)**

Create in `core/src/main/java/heroes/journey/mods/[category]/[Type]DSLImpl.kt`:

```kotlin
package heroes.journey.mods.[category]

import heroes.journey.entities.[category].[Type]
import heroes.journey.modlib.misc.I[Type]
import heroes.journey.modlib.misc.[Type]Builder
import heroes.journey.modlib.misc.[Type]DSL

class [Type]BuilderImpl : [Type]Builder {
    override var id: String = ""
    override var property1: String = ""
    override var property2: Int = 0
}

class [Type]DSLImpl : [Type]DSL {
    override fun [type](init: [Type]Builder.() -> Unit): I[Type] {
        val builder = [Type]BuilderImpl()
        builder.init()
        return [Type](builder.id, builder.property1, builder.property2)
    }
}
```

### **7. Registry Registration (core)**

Add to `core/src/main/java/heroes/journey/mods/Registries.java`:

```java
public class Registries {
    // ... existing registries ...
    public static Registry<[Type]> [Type]Manager = new Registry<>();
}
```

### **8. DSL Registration (core)**

Add to `core/src/main/java/heroes/journey/mods/ModlibDSLSetup.kt`:

```kotlin
fun setupModlibDSLs() {
    // ... existing registrations ...
    
    // Wire up modlib registries to core implementations
    heroes.journey.modlib.registries.Registries.[Type]Manager = 
        Registries.[Type]Manager as Registry<I[Type]>
    
    // Wire up modlib DSLs
    [Type]DSLProvider.instance = [Type]DSLImpl()
}
```

---

## 📝 **Complete Example: Basic Weapon Type**

Here's a complete example implementing a `Weapon` registrable using the Basic pattern:

### **1. Implementation (modlib/src/main/java/heroes/journey/modlib/items/Weapon.kt)**

```kotlin
package heroes.journey.modlib.items

import heroes.journey.modlib.registries.Registrable

/**
 * A Weapon, used for combat equipment.
 * This is a simple data container with no complex functions.
 */
class Weapon(
    override val id: String,
    val damage: Int,
    val durability: Int,
    val weaponType: WeaponType
) : Registrable(id) {
    
    override fun register(): Weapon {
        Registries.WeaponManager.register(this)
        return this
    }
}

/**
 * Builder for defining a weapon in a natural DSL style.
 */
class WeaponBuilder {
    var id: String = ""
    var damage: Int = 1
    var durability: Int = 100
    var weaponType: WeaponType = WeaponType.SWORD
}

/**
 * DSL entrypoint for defining a weapon.
 */
fun weapon(init: WeaponBuilder.() -> Unit): Weapon {
    val builder = WeaponBuilder()
    builder.init()
    return Weapon(builder.id, builder.damage, builder.durability, builder.weaponType)
}

enum class WeaponType {
    SWORD, AXE, BOW, STAFF
}
```

### **2. Registry Definition (modlib/src/main/java/heroes/journey/modlib/registries/Registries.kt)**

```kotlin
object Registries {
    // ... existing registries ...
    lateinit var WeaponManager: Registry<Weapon>
}
```

### **3. Registry Initialization (modlib/src/main/java/heroes/journey/modlib/ModlibDSLSetup.kt)**

```kotlin
fun setupModlibRegistries() {
    // ... existing registrations ...
    
    // Initialize registries
    Registries.WeaponManager = Registry<Weapon>()
}
```

### **4. Usage in Mod Script**

```kotlin
import heroes.journey.modlib.items.weapon
import heroes.journey.modlib.items.WeaponType
import heroes.journey.modlib.Ids

weapon {
    id = Ids.WEAPON_SWORD
    damage = 5
    durability = 100
    weaponType = WeaponType.SWORD
}.register()
```

---

## 🔧 **Migration Guide**

### **Converting Basic Registrables**

To convert an existing registrable from Advanced to Basic pattern:

1. **Move implementation to modlib** - Create `[Type].kt` in modlib (no interface, just concrete class)
2. **Remove core implementation** - Delete the core implementation class
3. **Remove core DSL implementation** - Delete the core DSL implementation
4. **Remove core registry registration** - Remove from core Registries.java
5. **Remove core DSL registration** - Remove from core ModlibDSLSetup.kt
6. **Update registry definition** - Change from `lateinit var` to `open val` with direct initialization
7. **Update core integration** - Point core registry to modlib registry via getter

### **Example: Converting Group to Basic**

```kotlin
// Before (Advanced pattern)
// core/src/main/java/heroes/journey/entities/tagging/Group.java
// core/src/main/java/heroes/journey/mods/attributes/GroupDSLImpl.kt
// core/src/main/java/heroes/journey/mods/Registries.java (registration)
// core/src/main/java/heroes/journey/mods/ModlibDSLSetup.kt (registration)

// After (Basic pattern)
// modlib/src/main/java/heroes/journey/modlib/attributes/Group.kt (concrete class + builder + DSL)
// modlib/src/main/java/heroes/journey/modlib/registries/Registries.kt (open val GroupManager)
// core/src/main/java/heroes/journey/mods/Registries.java (points to modlib via getter)
```

---

## ✅ **Checklist for New Registrable**

### **For Basic Registrables:**
- [ ] **Implementation** defined in modlib with proper documentation
- [ ] **DSL Builder** defined with builder pattern
- [ ] **Registry** added to modlib Registries object as `open val`
- [ ] **Core Integration** updated to point to modlib registry
- [ ] **Documentation** added to modlib README.md
- [ ] **Tests** created to verify registration and lookup
- [ ] **Example usage** in mod scripts

### **For Advanced Registrables:**
- [ ] **Interface** defined in modlib with proper documentation
- [ ] **DSL Interface** defined with builder pattern
- [ ] **DSL Provider** singleton created
- [ ] **Registry** added to modlib Registries object
- [ ] **Core Implementation** class created extending Registrable
- [ ] **DSL Implementation** created in core/mods package
- [ ] **Registry** added to core Registries.java
- [ ] **DSL Registration** added to core ModlibDSLSetup.kt
- [ ] **Documentation** added to modlib README.md
- [ ] **Tests** created to verify registration and lookup
- [ ] **Example usage** in mod scripts

---

## 🚨 **Common Pitfalls**

1. **Forgetting DSL Registration** - Always add to appropriate ModlibDSLSetup.kt
2. **Wrong Package Structure** - Follow the existing pattern exactly
3. **Missing Interface Properties** - Ensure all needed properties are exposed
4. **Direct Core References** - Use IDs and interfaces, never direct core classes
5. **Inconsistent Naming** - Follow the `I[Type]`, `[Type]Builder`, `[Type]DSL` pattern
6. **Missing Documentation** - Always add KDoc comments for modders
7. **Wrong Pattern Choice** - Use Basic for data containers, Advanced for complex objects

---

## 📚 **Reference Examples**

### **Basic Registrables:**
- **Group** - Simple ID-only registrable
- **Buff** - Simple data container with attributes
- **Terrain** - Simple data container with cost
- **Biome** - Simple data container with features
- **TextureMap** - Simple data container with dimensions

### **Advanced Registrables:**
- **Action** - Complex with multiple functions and game logic
- **Stat** - Complex with formula calculations and min/max logic
- **Quest** - Complex with completion logic and cost/reward processing
- **Challenge** - Complex with power tier calculations
- **Approach** - Complex with requirement checking logic

Each follows the appropriate pattern outlined above.

---

## 🔧 **Key Insights from Group Migration**

### **Registry Pattern**
- **Use `open val` with direct initialization**: `open val GroupManager: Registry<Group> = Registry()`
- **Eliminates need for separate initialization**: No `setupModlibRegistries()` needed
- **Automatic getter generation**: Kotlin creates `getGroupManager()` automatically

### **Constructor Pattern**
- **Avoid parameter shadowing**: Use `class Group(id: String)` not `class Group(override val id: String)`
- **Pass parameter directly**: `Registrable(id)` not `Registrable(this.id)`

### **Core Integration**
- **Point core to modlib**: `heroes.journey.modlib.registries.Registries.INSTANCE.getGroupManager()`
- **No DSL registration needed**: Basic registrables don't need provider pattern
- **Simplified imports**: Direct function calls instead of provider pattern

### **File Structure**
- **Single file in modlib**: Implementation, builder, and DSL all in one file
- **No core files needed**: Everything stays in modlib
- **No registration needed**: Direct function calls work immediately 
Each follows the same 8-component pattern outlined above. 