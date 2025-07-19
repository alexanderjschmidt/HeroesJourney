# Challenge Type System

## Overview

The challenge system has been reorganized to group challenges by base stat type (BODY, MIND, MAGIC, CHARISMA). Each challenge now has a `challengeType` that automatically determines which approaches are available to players. Rewards are now determined dynamically by challenge actions based on character stats and chosen approaches.

## Challenge Types

### BODY Challenges
- **Primary Stat**: BODY
- **Available Approaches**: MIGHT, SKILL, EMPOWERMENT, CHIVALRY
- **Themes**: Physical combat, strength-based challenges, martial prowess
- **Rewards**: Determined by character's BODY stat and chosen approach

### MIND Challenges  
- **Primary Stat**: MIND
- **Available Approaches**: TECHNIQUE, LOGIC, CONCENTRATION, CUNNING
- **Themes**: Intellectual puzzles, strategic thinking, mental discipline
- **Rewards**: Determined by character's MIND stat and chosen approach

### MAGIC Challenges
- **Primary Stat**: MAGIC
- **Available Approaches**: ENCHANTING, ILLUSION, SORCERY, BEWITCHING
- **Themes**: Magical creatures, spellcasting, arcane mysteries
- **Rewards**: Determined by character's MAGIC stat and chosen approach

### CHARISMA Challenges
- **Primary Stat**: CHARISMA
- **Available Approaches**: BRAVADO, PERSUASION, MESMERISM, CHARM
- **Themes**: Social interactions, diplomacy, leadership
- **Rewards**: Determined by character's CHARISMA stat and chosen approach

## Approach Breakdown

### BODY Approaches (2x BODY + 1x Other)
- **MIGHT** (BODY + BODY + BODY) - Raw physical strength
- **SKILL** (BODY + BODY + MIND) - Technical combat ability  
- **EMPOWERMENT** (BODY + BODY + MAGIC) - Physical enhancement
- **CHIVALRY** (BODY + BODY + CHARISMA) - Noble combat

### MIND Approaches (2x MIND + 1x Other)
- **TECHNIQUE** (MIND + MIND + BODY) - Specialized fighting
- **LOGIC** (MIND + MIND + MIND) - Pure reasoning
- **CONCENTRATION** (MIND + MIND + MAGIC) - Focused mental effort
- **CUNNING** (MIND + MIND + CHARISMA) - Clever strategy

### MAGIC Approaches (2x MAGIC + 1x Other)
- **ENCHANTING** (MAGIC + MAGIC + BODY) - Object enchantment
- **ILLUSION** (MAGIC + MAGIC + MIND) - Deception magic
- **SORCERY** (MAGIC + MAGIC + MAGIC) - Raw magical force
- **BEWITCHING** (MAGIC + MAGIC + CHARISMA) - Charm magic

### CHARISMA Approaches (2x CHARISMA + 1x Other)
- **BRAVADO** (CHARISMA + CHARISMA + BODY) - Bold actions
- **PERSUASION** (CHARISMA + CHARISMA + MIND) - Convincing others
- **MESMERISM** (CHARISMA + CHARISMA + MAGIC) - Mind control
- **CHARM** (CHARISMA + CHARISMA + CHARISMA) - Personal appeal

## Implementation

### Challenge Definition
```kotlin
challenge {
    id = Ids.CHALLENGE_EXAMPLE
    render = Ids.CREATURE_SPRITE
    challengeType = Ids.CHALLENGE_TYPE_BODY  // Automatically provides BODY approaches
}.register()
```

### Reward System
Rewards are now determined dynamically by the challenge action system:
- **Primary Reward**: Based on character's base stat level and chosen approach
- **Secondary Reward**: Based on character's secondary stat level (if approach uses secondary stat)
- **Realm Attention**: Rewards are drawn from the realm's attention pool
- **Character Stats**: Higher stats provide more reward potential

### Action System
The action system automatically provides the appropriate approaches based on the challenge type:
- When a player faces a challenge, only approaches of the challenge's type are shown
- This creates clear strategic choices based on character development
- Players must develop the appropriate base stat to access more approaches
- Rewards are calculated based on character stats and realm attention availability

## Benefits

1. **Simplified Challenge Definition**: No need to specify individual approaches or rewards
2. **Dynamic Reward System**: Rewards scale with character development
3. **Clear Strategic Choices**: Players must choose which base stats to develop
4. **Thematic Consistency**: Each challenge type has a clear theme and approach focus
5. **Character Specialization**: Players can specialize in certain challenge types
6. **Balanced Progression**: Each stat has equal representation across challenges
7. **Reduced Maintenance**: Less configuration needed for each challenge
8. **Resource Management**: Realm attention system creates strategic depth

## Migration Notes

- All existing challenges have been updated to use the new system
- Individual approach specifications have been removed from all challenges
- Reward specifications have been removed from all challenges
- Challenge files are organized by type for easier maintenance
- The action system automatically handles approach filtering and reward calculation
- Rewards now scale with character development and realm attention availability 