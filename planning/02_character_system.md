# 👤 **Character System**

## **Base Stats**

Characters start with **one stat at 2, others at 1** and develop through focused progression:

### **Core Stats**
* **BODY** - Physical prowess and combat ability
* **MIND** - Intelligence and problem-solving
* **MAGIC** - Magical power and mystical understanding
* **CHARISMA** - Social skills and leadership

### **Character Progression**
* Characters gain **1 stat per 5 turns** (increasing one of the four stats by 1)
* **Focused development** - players specialize in specific stats rather than balanced growth
* **Strategic choice** - which stats to develop affects available approaches

## **Renown Stats (Victory Points)**

These are the primary resources used for quests and character advancement:

* **VALOR** - Combat and physical achievements (from BODY)
* **INSIGHT** - Knowledge and discovery achievements (from MIND)
* **ARCANUM** - Magical and mystical achievements (from MAGIC)
* **INFLUENCE** - Social and diplomatic achievements (from CHARISMA)

## **Calculated Stats**

These are derived from base stats and provide additional character capabilities:

* **SPEED** - How quickly a character can act (derived from BODY)
* **VISION** - How far a character can see (BODY + 3)
* **CARRY_CAPACITY** - How much weight a character can carry (BODY × 10, max 100)

## **Stat Groups**

Stats are organized into logical groups for easier management:

### **Base Stats Group**
* BODY, MIND, MAGIC, CHARISMA

### **Renown Group**
* VALOR, INSIGHT, ARCANUM, INFLUENCE

### **Individual Stat Groups**
* **BODY Group**: BODY, SPEED, VISION, CARRY_CAPACITY
* **MIND Group**: MIND
* **MAGIC Group**: MAGIC
* **CHARISMA Group**: CHARISMA

## **Stat Development Strategy**

### **Focused Builds**
Players must choose which stats to develop, creating distinct character archetypes:
* **Warrior** - High BODY, moderate CHARISMA
* **Scholar** - High MIND, moderate MAGIC
* **Mage** - High MAGIC, moderate MIND
* **Diplomat** - High CHARISMA, moderate MIND

### **Stat Interactions**
* **Primary stats** determine which approaches are available in challenges
* **Secondary stats** provide bonus rewards when used in approaches
* **Stat combinations** unlock different strategic options

## **Character Components**

### **StatsComponent**
* Stores all character attributes and calculated values
* Manages fame accumulation
* Handles stat modifications and buffs

### **BuffsComponent**
* Temporary stat modifications
* Duration-based effects
* Stacking and removal logic

### **QuestsComponent**
* Tracks active quests
* Manages quest completion and rewards
* Handles quest availability

### **InventoryComponent**
* Item storage and management
* Weight calculations
* Equipment effects

## **Progression Mechanics**

### **Stat Gain**
* **Automatic**: 1 stat point every 5 turns
* **Quest rewards**: Complete quests for stat bonuses
* **Training actions**: Spend renown to improve stats

### **Fame Accumulation**
* **Challenge completion**: Gain fame from successful challenges
* **Quest completion**: Fame rewards from quest objectives
* **Achievement milestones**: Bonus fame for specific accomplishments

### **Resource Management**
* **Renown spending**: Use renown for quests and training
* **Renown earning**: Gain renown through challenges
* **Strategic balance**: Manage earning vs. spending

## **Character Specialization**

### **Approach Optimization**
Different character builds excel at different challenge approaches:
* **BODY-focused**: Excel at MIGHT, SKILL, EMPOWERMENT, CHIVALRY
* **MIND-focused**: Excel at TECHNIQUE, LOGIC, CONCENTRATION, CUNNING
* **MAGIC-focused**: Excel at ENCHANTING, ILLUSION, SORCERY, BEWITCHING
* **CHARISMA-focused**: Excel at BRAVADO, PERSUASION, MESMERISM, CHARM

### **Reward Optimization**
Character stats directly influence reward calculations:
* **Higher base stats** provide more renown from challenges
* **Secondary stats** provide bonus rewards when used in approaches
* **Stat combinations** unlock more efficient reward paths 