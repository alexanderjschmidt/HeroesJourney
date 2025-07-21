# 🏆 **Quest System**

## **Overview**

The quest system provides structured objectives that players can complete by spending renown resources. Quests offer fame rewards and stat bonuses, creating strategic choices between immediate gains and long-term development.

## **Quest Structure**

### **Quest Components**
* **Cost** - Renown resources required to complete the quest
* **Rewards** - Renown and stat bonuses granted upon completion
* **Fame Reward** - Direct fame points earned from quest completion
* **Availability** - Conditions that determine when quests are available

### **Quest Definition**
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

## **Quest Types**

### **Training Quests**
* **Gain Physical Fitness** - Cost: 1 VALOR, Reward: 2 VALOR + 1 BODY
* **Study Ancient Texts** - Cost: 1 INSIGHT, Reward: 2 MIND + 1 INSIGHT
* **Master the Arcane** - Cost: 2 ARCANUM, Reward: 3 ARCANUM + 1 MAGIC
* **Perform a Noble Deed** - Cost: 2 INFLUENCE, Reward: 3 INFLUENCE + 1 CHARISMA

### **Exploration Quests**
* **Delve a Dungeon** - Cost: 2 VALOR, Reward: 1 INSIGHT
* **Find Lost Ruin** - Cost: 1 INSIGHT, Reward: 2 ARCANUM

### **Specialized Quests**
* **Knowledge Gathering** - Focus on INSIGHT and MIND development
* **Combat Training** - Focus on VALOR and BODY development
* **Arcane Research** - Focus on ARCANUM and MAGIC development
* **Diplomatic Missions** - Focus on INFLUENCE and CHARISMA development

## **Quest Mechanics**

### **Quest Availability**
* Quests are generated randomly in regions with quest boards
* Each region can have 0-3 active quests at a time
* New quests are automatically generated when quest slots are empty
* Quest types are distributed based on region characteristics

### **Quest Completion**
* Players must have sufficient renown to pay the quest cost
* Costs are deducted immediately upon quest acceptance
* Rewards are granted upon successful completion
* Fame rewards provide direct victory points

### **Quest Actions**
* **Quest Board** - View available quests in a region
* **Accept Quest** - Take on a quest (costs are paid)
* **Complete Quest** - Finish an accepted quest for rewards
* **Abandon Quest** - Give up on a quest (costs are lost)

## **Quest Components**

### **QuestsComponent**
* Tracks active quests for each entity
* Manages quest state and completion
* Handles quest availability and generation

### **Quest Actions**
* **Quest Board Action** - Displays available quests in a region
* **Complete Quest Action** - Processes quest completion and rewards
* **Quest Validation** - Checks if player can afford quest costs

## **Strategic Considerations**

### **Resource Management**
* **Cost vs. Reward** - Evaluate whether quest rewards justify the cost
* **Renown Flow** - Balance quest spending with challenge earning
* **Stat Development** - Use quests to accelerate character progression

### **Quest Timing**
* **Early Game** - Focus on quests that develop your starting stats
* **Mid Game** - Target quests that provide needed renown types
* **Late Game** - Prioritize quests with high fame rewards

### **Competitive Aspects**
* **Quest Scarcity** - Limited quests create competition between players
* **Optimal Positioning** - Move to regions with beneficial quests
* **Resource Denial** - Complete quests to deny them to opponents

## **Quest Examples**

### **Basic Training Quest**
```
Quest: Gain Physical Fitness
Cost: 1 VALOR
Rewards: 2 VALOR, 1 BODY stat
Fame: 15 points
```

### **Advanced Training Quest**
```
Quest: Master the Arcane
Cost: 2 ARCANUM
Rewards: 3 ARCANUM, 1 MAGIC stat
Fame: 35 points
```

### **Exploration Quest**
```
Quest: Find Lost Ruin
Cost: 1 INSIGHT
Rewards: 2 ARCANUM
Fame: 25 points
```

## **Quest Generation**

### **Regional Quest Generation**
* **RegionManagementSystem** handles quest generation
* Quests are added randomly when regions have empty quest slots
* Quest types are selected based on region characteristics
* Quest costs and rewards are balanced for game progression

### **Quest Balance**
* **Cost Scaling** - Quest costs increase with game progression
* **Reward Scaling** - Quest rewards scale with character development
* **Fame Distribution** - Fame rewards are balanced across quest types
* **Stat Bonuses** - Quest rewards provide strategic stat development

## **Future Development**

### **Dynamic Quest System**
* **Procedural Generation** - Quests generated based on world state
* **Player-Driven Quests** - Quests that respond to player actions
* **Multi-Step Quests** - Complex quests with multiple objectives
* **Faction Quests** - Quests tied to specific world factions

### **Quest Varieties**
* **Time-Limited Quests** - Quests that expire after a certain number of turns
* **Chain Quests** - Series of related quests with escalating rewards
* **Competitive Quests** - Quests that can only be completed by one player
* **Cooperative Quests** - Quests that require multiple players to complete 