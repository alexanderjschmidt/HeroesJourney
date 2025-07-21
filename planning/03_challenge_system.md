# ⚔️ **Challenge System**

## **Overview**

The challenge system features **135 unique challenges** organized into 9 categories, each offering approaches based on character stats. Challenges are the primary method for gaining renown and fame.

## **Approach System (Updated)**

### **Default Approaches**
- By default, each player can only use one approach per challenge type:
  - **BODY**: Might
  - **MIND**: Logic
  - **MAGIC**: Sorcery
  - **CHARISMA**: Charm

### **Unlocking Sub-Approaches**
- When you raise a stat to **3**, you unlock its sub-approach for use on other challenge types.
  - Example: If you have **MIND 3**, you can use **Skill** (the MIND sub-approach) on BODY challenges.
  - Sub-approaches for each stat:
    - **BODY**: Skill (unlocked at BODY 3)
    - **MIND**: Technique (unlocked at MIND 3)
    - **MAGIC**: Enchanting (unlocked at MAGIC 3)
    - **CHARISMA**: Bravado (unlocked at CHARISMA 3)
- Sub-approaches can be used on challenges of a different type, providing more flexibility and strategic options.

### **Approach Table Example**
| Challenge Type | Default Approach | Sub-Approach (unlocked at 3+) |
|---------------|------------------|-------------------------------|
| BODY          | Might            | Skill (if MIND 3+)            |
| MIND          | Logic            | Technique (if BODY 3+)        |
| MAGIC         | Sorcery          | Enchanting (if BODY 3+)       |
| CHARISMA      | Charm            | Bravado (if BODY 3+)          |

## **Renown Rewards (Updated)**

### **Main Approach (Default)**
- When you use the main approach (e.g., Might for BODY), you gain renown equal to your stat in that type.
  - Example: BODY 2, use Might → **2 Valor**

### **Sub-Approach (Unlocked)**
- When you use a sub-approach (e.g., Skill for BODY), you gain:
  - **(Stat - 1)** of the main renown (e.g., BODY - 1 Valor)
  - **1** of the secondary renown (e.g., 1 Insight for Skill)
  - Example: BODY 2, use Skill → **1 Valor, 1 Insight**

### **Stat 5+ Bonus**
- If your stat is **5 or higher**, you gain an additional secondary renown when using a sub-approach.
  - Example: BODY 5, use Skill → **4 Valor, 2 Insight**

### **Reward Table Example**
| Approach Used | Stat | Main Renown | Secondary Renown |
|--------------|------|-------------|------------------|
| Might        | 2    | 2 Valor     | -                |
| Skill        | 2    | 1 Valor     | 1 Insight        |
| Skill        | 5    | 4 Valor     | 2 Insight        |

## **Challenge Categories**

### **135 Unique Challenges** (15 per category)
* **Demon** - Fiendish creatures and dark magic
* **Dragon** - Ancient wyrms and draconic beings
* **Holy** - Divine beings and sacred guardians
* **Humanoid** - Goblins, halflings, and civilized races
* **Humanoid II** - Elves, merfolk, and advanced civilizations
* **Magical** - Elementals, fey, and magical constructs
* **Monster** - Ogres, trolls, and monstrous creatures
* **Undead** - Skeletons, vampires, and deathly beings
* **Vermin** - Insects, rodents, and small creatures

## **Challenge Type System (Expanded)**

Challenges are organized by base stat type, automatically determining available approaches. Each challenge type has a default approach and several sub-approaches that can be unlocked by raising other stats to 3 or higher. Sub-approaches provide a mix of main and secondary renown.

### **BODY Challenges**
- **Primary Stat**: BODY
- **Available Approaches:**
  - **Might** *(Default)* — [BODY] — Main renown: Valor
  - **Skill** *(Unlocked at MIND 3+)* — [BODY+MIND] — Main: Valor, Secondary: Insight
  - **Empowerment** *(Unlocked at MAGIC 3+)* — [BODY+MAGIC] — Main: Valor, Secondary: Arcanum
  - **Chivalry** *(Unlocked at CHARISMA 3+)* — [BODY+CHARISMA] — Main: Valor, Secondary: Influence
- **Themes**: Physical combat, strength-based challenges, martial prowess

### **MIND Challenges**
- **Primary Stat**: MIND
- **Available Approaches:**
  - **Logic** *(Default)* — [MIND] — Main renown: Insight
  - **Technique** *(Unlocked at BODY 3+)* — [MIND+BODY] — Main: Insight, Secondary: Valor
  - **Concentration** *(Unlocked at MAGIC 3+)* — [MIND+MAGIC] — Main: Insight, Secondary: Arcanum
  - **Cunning** *(Unlocked at CHARISMA 3+)* — [MIND+CHARISMA] — Main: Insight, Secondary: Influence
- **Themes**: Intellectual puzzles, strategic thinking, mental discipline

### **MAGIC Challenges**
- **Primary Stat**: MAGIC
- **Available Approaches:**
  - **Sorcery** *(Default)* — [MAGIC] — Main renown: Arcanum
  - **Enchanting** *(Unlocked at BODY 3+)* — [MAGIC+BODY] — Main: Arcanum, Secondary: Valor
  - **Illusion** *(Unlocked at MIND 3+)* — [MAGIC+MIND] — Main: Arcanum, Secondary: Insight
  - **Bewitching** *(Unlocked at CHARISMA 3+)* — [MAGIC+CHARISMA] — Main: Arcanum, Secondary: Influence
- **Themes**: Magical creatures, spellcasting, arcane mysteries

### **CHARISMA Challenges**
- **Primary Stat**: CHARISMA
- **Available Approaches:**
  - **Charm** *(Default)* — [CHARISMA] — Main renown: Influence
  - **Bravado** *(Unlocked at BODY 3+)* — [CHARISMA+BODY] — Main: Influence, Secondary: Valor
  - **Persuasion** *(Unlocked at MIND 3+)* — [CHARISMA+MIND] — Main: Influence, Secondary: Insight
  - **Mesmerism** *(Unlocked at MAGIC 3+)* — [CHARISMA+MAGIC] — Main: Influence, Secondary: Arcanum
- **Themes**: Social interactions, diplomacy, leadership

**Note:**
- *Default approaches* are always available for their challenge type.
- *Sub-approaches* require the associated stat to be at least 3 and can be used on challenges of a different type.
- *Main renown* is always equal to your stat in the challenge's primary type (or stat - 1 for sub-approaches), and *secondary renown* is 1 (or 2 if the stat is 5+) of the sub-approach's associated type.

## **Strategic Considerations (Updated)**

- **Stat Investment**: Raising a stat to 3 unlocks sub-approaches, increasing your flexibility in challenges.
- **Reward Optimization**: Using the main approach maximizes primary renown; sub-approaches provide a mix of rewards, and investing to 5+ in a stat increases secondary rewards.
- **Approach Selection**: Choose between maximizing main renown or diversifying with secondary rewards, depending on your build and goals.
- **Challenge Planning**: Plan stat growth to unlock the sub-approaches that best fit your strategy and the challenges you expect to face.

## **Implementation Notes**
- The challenge system now encourages focused stat development and rewards specialization, while still allowing for hybrid builds through sub-approach unlocking.
- Renown rewards are now directly tied to your stat values, making stat increases immediately impactful in gameplay. 

## **Realm's Attention: Shared Renown Pool**

Each turn, the game provides a limited pool of each renown type—**Valor, Insight, Arcanum, Influence**—known as the **realm's attention**. When players earn renown from challenges, it is drawn from this pool. If a pool is depleted, players cannot earn more of that type until the next turn.

This system creates competition for specific renown types and encourages players to diversify their approach selection. If the renown you want is gone, you may need to pursue a different type or choose to **rest**. Resting allows a player to act earlier in the next turn, giving them priority access to the realm's attention and first pick of available renown.

This mechanic adds a tactical layer to challenge selection, approach choice, and turn order, rewarding players who can anticipate opponents' moves and adapt to the shifting availability of resources. 