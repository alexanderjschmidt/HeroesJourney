# Related Stats Migration Guide

This document outlines the migration from the old parent stats system to the new automatic related stats system.

## Overview

The new system automatically creates related stats based on DSL inputs, eliminating the need for manual `relatedStat` calls and providing a more intuitive API.

## Key Changes

### 1. Automatic Related Stat Creation

**Before (Manual)**
```kotlin
stat {
    id = Ids.STAT_BODY
    defaultValue = 1
}.register()

// Manual relationship creation
relatedStat(Ids.STAT_MIND, Relation.MULT)
```

**After (Automatic)**
```kotlin
stat {
    id = Ids.STAT_BODY
    defaultValue = 1
    min = 1
    max = 10
}.register()
// Automatically creates:
// - STAT_BODY_min (with Relation.MIN)
// - STAT_BODY_max (with Relation.MAX)
// - STAT_BODY_mult (with Relation.MULTIPLICAND)
// - STAT_BODY_max_mult (with Relation.MULTIPLICAND to the max stat)
```

### 2. New Multiplier System

**Before (Old MULT relation)**
```kotlin
stat {
    id = Ids.STAT_BODY
    mult = 150  // 150% multiplier
}.register()
```

**After (New MULTIPLIER/MULTIPLICAND system)**
```kotlin
stat {
    id = Ids.STAT_BODY
    defaultValue = 1
}.register()
// Automatically creates STAT_BODY_mult with default 100 (100%)
// To modify multiplier: attributes.put(Ids.STAT_BODY_MULT, 150) // 150%
```

### 3. New Cap System

**Before (Old CAP relation)**
```kotlin
stat {
    id = Ids.STAT_STAMINA
    cap = 100  // Direct cap
}.register()
```

**After (New _max_max system)**
```kotlin
stat {
    id = Ids.STAT_STAMINA
    maxFormula = { it.getDirect(Ids.STAT_BODY)!! * 25 + 75 }
    cap = 200  // Caps the max at 200
}.register()
// Automatically creates:
// - STAT_STAMINA_max (from maxFormula)
// - STAT_STAMINA_max_max (from cap)
// - STAT_STAMINA_max_mult (auto-generated)
// To modify cap: attributes.put(Ids.STAT_STAMINA_MAX_MAX, 150)
```

### 4. Formula-Based Min/Max

**Before (Fixed values only)**
```kotlin
stat {
    id = Ids.STAT_STAMINA
    max = 100
}.register()
```

**After (Formula support)**
```kotlin
stat {
    id = Ids.STAT_STAMINA
    maxFormula = { it.getDirect(Ids.STAT_BODY)!! * 25 + 75 }
}.register()
```

### 5. Parent/Child Relationships

**Before (Groups)**
```kotlin
stat {
    id = Ids.STAT_STAMINA
    group(Ids.GROUP_RESOURCES)
}.register()
```

**After (Parent/Child)**
```kotlin
stat {
    id = Ids.STAT_STAMINA
    parent = Ids.STAT_BODY
}.register()
```

### 6. Explicit Relationships

**Before (Built into DSL)**
```
```

## Examples

### Basic Stat with Auto-Generated Multiplier
```kotlin
stat {
    id = Ids.STAT_BODY
    defaultValue = 1
    min = 1
    max = 10
}.register()
// Creates: STAT_BODY, STAT_BODY_min, STAT_BODY_max, STAT_BODY_mult
```

### Resource Stat with Formula-Based Max and Cap
```kotlin
stat {
    id = Ids.STAT_STAMINA
    min = 0
    defaultValue = 0
    maxFormula = { it.getDirect(Ids.STAT_BODY)!! * 25 + 75 }
    cap = 200  // Caps the calculated max at 200
}.register()
// Creates: STAT_STAMINA, STAT_STAMINA_min, STAT_STAMINA_max, STAT_STAMINA_mult, STAT_STAMINA_max_mult, STAT_STAMINA_max_max
```

### Stat with Fixed Max and Cap
```kotlin
stat {
    id = Ids.STAT_BODY
    defaultValue = 1
    min = 1
    max = 10
    cap = 8  // Caps the max at 8
}.register()
// Creates: STAT_BODY, STAT_BODY_min, STAT_BODY_max, STAT_BODY_mult, STAT_BODY_max_mult, STAT_BODY_max_max
```

### Parent/Child Relationship
```kotlin
stat {
    id = Ids.STAT_RACE
    defaultValue = 0
}.register()

stat {
    id = Ids.STAT_DEMON_RACE
    parent = Ids.STAT_RACE
    defaultValue = 0
}.register()
```

### Explicit Relationship
```kotlin
// Define stats
stat { id = Ids.STAT_STAMINA_REGEN }.register()
stat { id = Ids.STAT_STAMINA }.register()

// Define relationship
relate(Ids.STAT_STAMINA_REGEN, Relation.REGEN, Ids.STAT_STAMINA)
```