# 🏗️ **Registrable Implementation Guide**

This document outlines the complete pattern for implementing a new registrable type in the Heroes Journey modlib system. A registrable is any game object that can be defined by mods and stored in a registry (e.g., Items, Stats, Actions, Quests, etc.).

---

## 📋 **Overview**

Every registrable type requires **8 core components** to work properly:

1. **Interface** (modlib) - Public API for mods
2. **DSL Interface** (modlib) - Builder pattern for mod definitions  
3. **DSL Provider** (modlib) - Singleton to wire up the DSL
4. **Registry Definition** (modlib) - Storage and lookup
5. **Core Implementation** (core) - Concrete class implementing the interface
6. **DSL Implementation** (core) - Builder implementation
7. **Registry Registration** (core) - Wire up to modlib registry
8. **DSL Registration** (core) - Wire up DSL provider

---

## 🧩 **Component Breakdown**

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

## 📝 **Complete Example: Weapon Type**

Here's a complete example implementing a `Weapon` registrable:

### **1. Interface (modlib/src/main/java/heroes/journey/modlib/items/Weapon.kt)**

```kotlin
package heroes.journey.modlib.items

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Weapon, used for combat equipment.
 * Mods should only use this interface, not implementation classes.
 */
interface IWeapon : IRegistrable {
    val damage: Int
    val durability: Int
    val weaponType: WeaponType
    override fun register(): IWeapon
}

/**
 * Builder for defining a weapon in a natural DSL style.
 */
interface WeaponBuilder {
    var id: String
    var damage: Int
    var durability: Int
    var weaponType: WeaponType
}

/**
 * Interface for the weapon DSL implementation.
 */
interface WeaponDSL {
    fun weapon(init: WeaponBuilder.() -> Unit): IWeapon
}

/**
 * Singleton provider for the WeaponDSL implementation.
 */
object WeaponDSLProvider {
    lateinit var instance: WeaponDSL
}

/**
 * DSL entrypoint for defining a weapon.
 */
fun weapon(init: WeaponBuilder.() -> Unit): IWeapon = WeaponDSLProvider.instance.weapon(init)

enum class WeaponType {
    SWORD, AXE, BOW, STAFF
}
```

### **2. Registry Definition (modlib/src/main/java/heroes/journey/modlib/registries/Registries.kt)**

```kotlin
object Registries {
    // ... existing registries ...
    lateinit var WeaponManager: Registry<IWeapon>
}
```

### **3. Core Implementation (core/src/main/java/heroes/journey/entities/items/Weapon.kt)**

```kotlin
package heroes.journey.entities.items

import heroes.journey.modlib.items.IWeapon
import heroes.journey.modlib.items.WeaponType
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries

class Weapon(
    override val id: String,
    override val damage: Int,
    override val durability: Int,
    override val weaponType: WeaponType
) : Registrable(id), IWeapon {
    
    override fun register(): IWeapon {
        Registries.WeaponManager.register(this)
        return this
    }
}
```

### **4. DSL Implementation (core/src/main/java/heroes/journey/mods/items/WeaponDSLImpl.kt)**

```kotlin
package heroes.journey.mods.items

import heroes.journey.entities.items.Weapon
import heroes.journey.modlib.items.IWeapon
import heroes.journey.modlib.items.WeaponBuilder
import heroes.journey.modlib.items.WeaponDSL
import heroes.journey.modlib.items.WeaponType

class WeaponBuilderImpl : WeaponBuilder {
    override var id: String = ""
    override var damage: Int = 1
    override var durability: Int = 100
    override var weaponType: WeaponType = WeaponType.SWORD
}

class WeaponDSLImpl : WeaponDSL {
    override fun weapon(init: WeaponBuilder.() -> Unit): IWeapon {
        val builder = WeaponBuilderImpl()
        builder.init()
        return Weapon(builder.id, builder.damage, builder.durability, builder.weaponType)
    }
}
```

### **5. Registry Registration (core/src/main/java/heroes/journey/mods/Registries.java)**

```java
public class Registries {
    // ... existing registries ...
    public static Registry<Weapon> WeaponManager = new Registry<>();
}
```

### **6. DSL Registration (core/src/main/java/heroes/journey/mods/ModlibDSLSetup.kt)**

```kotlin
fun setupModlibDSLs() {
    // ... existing registrations ...
    
    // Wire up modlib registries to core implementations
    heroes.journey.modlib.registries.Registries.WeaponManager = 
        Registries.WeaponManager as Registry<IWeapon>
    
    // Wire up modlib DSLs
    WeaponDSLProvider.instance = WeaponDSLImpl()
}
```

### **7. Usage in Mod Script**

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

## 🔧 **Advanced Patterns**

### **Complex Properties**

For properties that reference other registrables, use IDs in the DSL:

```kotlin
interface ItemBuilder {
    var id: String
    var subTypeId: String?  // Use ID, not direct reference
    var weight: Int
}

class ItemDSLImpl : ItemDSL {
    override fun item(init: ItemBuilder.() -> Unit): IItem {
        val builder = ItemBuilderImpl()
        builder.init()
        val coreSubType = builder.subTypeId?.let { 
            Registries.ItemSubTypeManager[it] as ItemSubType 
        }
        return Item(builder.id, coreSubType!!, builder.weight)
    }
}
```

### **Nested DSLs**

For complex objects with their own DSLs:

```kotlin
interface QuestBuilder {
    var id: String
    fun cost(init: AttributesBuilder.() -> Unit)
    fun rewards(init: AttributesBuilder.() -> Unit)
}

class QuestBuilderImpl : QuestBuilder {
    private var _cost: IAttributes? = null
    private var _rewards: IAttributes? = null
    
    override fun cost(init: AttributesBuilder.() -> Unit) {
        _cost = attributes(init)
    }
    
    override fun rewards(init: AttributesBuilder.() -> Unit) {
        _rewards = attributes(init)
    }
}
```

### **Enums and Constants**

Move enums to modlib for mod access:

```kotlin
// In modlib
enum class ItemType {
    WEAPON, ARMOR, MISC, CONSUMABLE
}

// In DSL
interface ItemSubTypeBuilder {
    var id: String
    var type: ItemType?  // Use enum directly
}
```

---

## ✅ **Checklist for New Registrable**

- [ ] **Interface** defined in modlib with proper documentation
- [ ] **DSL Interface** defined with builder pattern
- [ ] **DSL Provider** singleton created
- [ ] **Registry** added to modlib Registries object
- [ ] **Core Implementation** class created extending Registrable
- [ ] **DSL Implementation** created in core/mods package
- [ ] **Registry** added to core Registries.java
- [ ] **DSL Registration** added to ModlibDSLSetup.kt
- [ ] **Documentation** added to modlib README.md
- [ ] **Tests** created to verify registration and lookup
- [ ] **Example usage** in mod scripts

---

## 🚨 **Common Pitfalls**

1. **Forgetting DSL Registration** - Always add to `setupModlibDSLs()`
2. **Wrong Package Structure** - Follow the existing pattern exactly
3. **Missing Interface Properties** - Ensure all needed properties are exposed
4. **Direct Core References** - Use IDs and interfaces, never direct core classes
5. **Inconsistent Naming** - Follow the `I[Type]`, `[Type]Builder`, `[Type]DSL` pattern
6. **Missing Documentation** - Always add KDoc comments for modders

---

## 📚 **Reference Examples**

See these existing implementations for reference:
- **Simple**: `Group.kt` - Basic registrable with just an ID
- **Complex**: `Item.kt` - References other registrables and has nested DSLs
- **Action**: `Action.kt` - Complex with multiple builder types and lambdas
- **Worldgen**: `Biome.kt` - References other registrables and has complex data

Each follows the same 8-component pattern outlined above. 