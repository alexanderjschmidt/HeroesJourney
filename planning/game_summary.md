# 🛡️ **Heroes Journey** - Current State Summary

*A competitive, turn-based, boardgame-style RPG where heroes race to build the greatest legend through challenges, quests, and strategic character development.*

---

## 🎯 **Core Objective**

Players take turns acting as legendary heroes in a shared world, competing to earn the most **Fame** before the game ends. Fame is gained by completing challenges, quests, and achieving personal milestones. The player with the most Fame becomes the hero remembered in song and story.

---

## 🕰️ **Game Structure**

* **Turn-based** gameplay with approximately 1 hour for a full game
* Each turn represents a **full day of in-world time**
* **Turn order** is determined by character speed stats
* On a player's turn, they:
  1. **Optionally move** to a new region
  2. **Take one action** (challenge, quest, train, explore, etc.)

---

## 🌍 **World & Map System**

### **Tile-Based World**
* **Procedurally generated** tile-based world using libGDX
* **Multiple regions** with unique features and terrain types
* **Dynamic pathfinding** system for movement
* **Fog of war** system for exploration

### **Regions & Features**
* **Capital region** at the center (future Demon King arrival location)
* **Towns and cities** with quest boards and services
* **Dungeons, shrines, ruins** and other points of interest
* **Terrain types** - visual movement effects only, no strategic blocking

---

## 👤 **Character System**

### **Base Stats (Starting Values: One at 2, others at 1)**
* **BODY** - Physical prowess and combat ability
* **MIND** - Intelligence and problem-solving
* **MAGIC** - Magical power and mystical understanding
* **CHARISMA** - Social skills and leadership

### **Character Progression**
* Characters gain **1 stat per 5 turns** (increasing one of the four stats by 1)
* **Focused development** - players specialize in specific stats rather than balanced growth
* **Strategic choice** - which stats to develop affects available approaches

### **Renown Stats (Victory Points)**
* **VALOR** - Combat and physical achievements (from BODY)
* **INSIGHT** - Knowledge and discovery achievements (from MIND)
* **ARCANUM** - Magical and mystical achievements (from MAGIC)
* **INFLUENCE** - Social and diplomatic achievements (from CHARISMA)

---

## ⚔️ **Challenge System**

### **135 Unique Challenges**
Organized into 9 categories with 15 challenges each:
* **Demon** - Fiendish creatures and dark magic
* **Dragon** - Ancient wyrms and draconic beings
* **Holy** - Divine beings and sacred guardians
* **Humanoid** - Goblins, halflings, and civilized races
* **Humanoid II** - Elves, merfolk, and advanced civilizations
* **Magical** - Elementals, fey, and magical constructs
* **Monster** - Ogres, trolls, and monstrous creatures
* **Undead** - Skeletons, vampires, and deathly beings
* **Vermin** - Insects, rodents, and small creatures

### **16 Approach System**
Each challenge offers 3 different approaches from 16 total:

**PHYSICAL APPROACHES:**
* **MIGHT** (BODY+BODY+BODY) - Raw physical strength
* **SKILL** (BODY+BODY+MIND) - Technical combat ability
* **EMPOWERMENT** (BODY+BODY+MAGIC) - Physical enhancement
* **CHIVALRY** (BODY+BODY+CHARISMA) - Noble combat

**MENTAL APPROACHES:**
* **TECHNIQUE** (MIND+MIND+BODY) - Specialized fighting
* **LOGIC** (MIND+MIND+MIND) - Rational problem-solving
* **CONCENTRATION** (MIND+MIND+MAGIC) - Focused mental effort
* **CUNNING** (MIND+MIND+CHARISMA) - Clever strategy

**MAGICAL APPROACHES:**
* **ENCHANTING** (MAGIC+MAGIC+BODY) - Object enchantment
* **ILLUSION** (MAGIC+MAGIC+MIND) - Deception magic
* **SORCERY** (MAGIC+MAGIC+MAGIC) - Powerful spellcasting
* **BEWITCHING** (MAGIC+MAGIC+CHARISMA) - Charm magic

**SOCIAL APPROACHES:**
* **BRAVADO** (CHARISMA+CHARISMA+BODY) - Bold actions
* **PERSUASION** (CHARISMA+CHARISMA+MIND) - Convincing others
* **MESMERISM** (CHARISMA+CHARISMA+MAGIC) - Mind control
* **CHARM** (CHARISMA+CHARISMA+CHARISMA) - Personal appeal

---

## 🏆 **Reward System** *(Work in Progress)*

### **Current Implementation**
Each approach provides fixed rewards based on the approach chosen:

**PHYSICAL APPROACHES:**
* **MIGHT**: 3 VALOR
* **SKILL**: 2 VALOR + 1 INSIGHT
* **EMPOWERMENT**: 2 VALOR + 1 ARCANUM
* **CHIVALRY**: 2 VALOR + 1 INFLUENCE

**MENTAL APPROACHES:**
* **TECHNIQUE**: 2 INSIGHT + 1 VALOR
* **LOGIC**: 3 INSIGHT
* **CONCENTRATION**: 3 INSIGHT
* **CUNNING**: 2 INSIGHT + 1 INFLUENCE

**MAGICAL APPROACHES:**
* **ENCHANTING**: 2 ARCANUM + 1 VALOR
* **ILLUSION**: 2 ARCANUM + 1 INSIGHT
* **SORCERY**: 3 ARCANUM
* **BEWITCHING**: 2 ARCANUM + 1 INFLUENCE

**SOCIAL APPROACHES:**
* **BRAVADO**: 2 INFLUENCE + 1 VALOR
* **PERSUASION**: 2 INFLUENCE + 1 INSIGHT
* **MESMERISM**: 2 INFLUENCE + 1 ARCANUM
* **CHARM**: 3 INFLUENCE

### **Future Development**
* **Dynamic reward calculation** based on character stats and difficulty scaling
* **Realm attention overflow system** for scaling rewards
* **Progressive difficulty scaling** throughout the game

---

## 🎮 **Core Gameplay Loop**

1. **Choose Action** - Select from available actions:
   * **Face Challenge** - Choose approach and gain renown
   * **Complete Quest** - Spend renown to complete objectives
   * **Enhance Self** - Spend renown to improve stats (workout, study, etc.)
   * **Travel** - Move to neighboring region
   * **Other Actions** - Explore, gather information, etc.

2. **Execute Action** - Perform the chosen action:
   * **For Challenges**: Select approach (MIGHT, CHIVALRY, etc.) and gain renown
   * **For Quests**: Spend required renown to complete
   * **For Enhancement**: Spend renown to increase stats
   * **For Travel**: Move to adjacent region

3. **Manage Resources** - Balance renown spending vs. gaining
4. **Strategic Planning** - Position for future opportunities
5. **End Turn** - Pass control to next player

---

## 🏗️ **Technical Architecture**

### **Multi-Module Architecture**
* **`core`** - Main game logic, client-side rendering, and multiplayer client
* **`server`** - Dedicated server for lobby management and multiplayer functionality
* **`shared`** - Network messages, data models, and serialization shared between client/server
* **`lwjgl3`** - Desktop platform launcher using LWJGL3

### **Engine & Framework**
* **libGDX** - Cross-platform game development framework
* **Artemis-ODB** - Entity-Component-System (ECS) architecture
* **KryoNet** - Network communication library for multiplayer
* **Kotlin DSL** - Mod system for game content
* **Gradle** - Build system and dependency management

### **Client-Side Systems** (`core` module)
* **Game Engine** - Main game loop, entity management, and world simulation
* **Rendering Pipeline** - Sprite-based graphics with libGDX
* **UI Framework** - HUD, action menus, and resource displays using Scene2D
* **Input Handling** - Mouse and keyboard interaction
* **Audio System** - Music and sound effect management
* **Save/Load System** - JSON and Kryo serialization support
* **Multiplayer Client** - Network connection and lobby management
* **Mod System** - Content loading and hot-reloading

### **Server-Side Systems** (`server` module)
* **Lobby Management** - Create/join lobbies with up to 6 players
* **Connection Handling** - TCP/UDP server on ports 54555/54777
* **Player Session Management** - Track connected players and lobby assignments
* **Network Communication** - KryoNet-based message handling

### **Shared Systems** (`shared` module)
* **Network Messages** - Request/response classes for lobby operations
  * `CreateLobbyRequest` / `LobbyCreatedResponse`
  * `JoinLobbyRequest` / `LobbyJoinedResponse`
  * `UpdateLobbyPlayers`
* **Data Models** - Shared data structures
  * `Lobby` - Lobby state and player management
  * `LobbyInfo` - Lobby information for UI display
  * `MapData` - Map configuration (seed, size, teams, fog of war)
* **Serialization** - Kryo class registration for network transmission
* **Constants** - Server configuration (TCP/UDP ports)

---

## 🔧 **Modding System**

### **Mod Architecture**
* **Kotlin DSL** - Declarative content creation using domain-specific language
* **Script-based loading** - Mods loaded from `mods/` directory at runtime
* **Registry system** - Centralized registration for all game content
* **Hot-reloading** - Mods can be reloaded without restarting the game

### **Basegame Mod** (`mods/basegame/`)
The core mod containing all essential game content:

**Content Files:**
* **`actions.kts`** - Player actions and abilities (workout, study, quest board, etc.)
* **`quests.kts`** - Quest definitions and completion logic
* **`items.kts`** - Item definitions, properties, and effects
* **`textures.kts`** - Animation and sprite definitions for all 135 challenges
* **`terrains.kts`** - Terrain type definitions and properties

**Challenge Categories:**
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

### **Mod Development**
* **Content creation** - Define challenges, actions, items using Kotlin DSL
* **Asset integration** - Link sprites and animations to game content
* **Balance testing** - Iterate on approach usage and reward distribution
* **Thematic consistency** - Ensure descriptions match animations and approaches

### **Planned Code Reorganization**
**Moving from `initializers/` folder to proper mod structure:**

**Actions & Tiles → Basegame Mod:**
* **Actions** - Move from `initializers/actions/` to `mods/basegame/actions/`
* **Tiles** - Move from `initializers/tiles/` to `mods/basegame/tiles/`

**Maps → Map Generation Utils:**
* **Map generation** - Move from `initializers/maps/` to `utils/mapgen/`
* **Biome system** - Customizable biome definitions and generation rules
* **Procedural algorithms** - Terrain generation and feature placement

**IDs & Utils → Core Systems:**
* **IDs** - Move from `initializers/ids/` to `core/ids/` or `utils/ids/`
* **Utils** - Move from `initializers/utils/` to `utils/` or appropriate system folders

### **Modding Benefits**
* **Content separation** - Game logic separate from content definitions
* **Easy iteration** - Quick changes to challenges, rewards, and balance
* **Community expansion** - Third-party mods for additional content
* **Testing flexibility** - Isolated testing of specific content categories

---

## 📊 **Current Implementation Status**

### **✅ Completed Systems**
* **Core game engine** with libGDX and Artemis-ODB
* **Client-side rendering** with sprite-based graphics and UI framework
* **135 challenges** across 9 categories with balanced approach usage
* **16 approach system** with confluence stats
* **Character progression** with focused stat development
* **Fixed reward system** with predetermined approach rewards
* **Turn-based gameplay** with speed-based turn order
* **Tile-based world** with procedural generation
* **UI framework** with HUD, action menus, and resource displays
* **Save/load system** with multiple serialization formats
* **Mod system** for content creation and management
* **Basegame mod** with 135 challenges, actions, items, and textures
* **Multi-module architecture** with core, server, and shared modules

### **🔄 In Development**
* **Dynamic reward system** - Character stat-based calculations with difficulty scaling
* **Realm attention overflow** - Resource management for scaling rewards
* **Quest system** - Dynamic quest generation and completion
* **Demon King arrival** - Endgame trigger and final challenges
* **Game state synchronization** - Actual gameplay multiplayer (currently only lobby system implemented)
* **Action validation** - Server-side verification of player actions
* **World state management** - Centralized game state on server
* **Knowledge system** - Information gathering and rumors
* **Sound system** - Action sounds and music integration
* **Inventory system** - Item management and encumbrance

### **📋 Planned Features**
* **Events system** - Global events and world changes
* **Advanced AI** - Improved NPC behavior and decision-making
* **Enhanced UI** - Better controls display and scrolling
* **Performance optimization** - Improved rendering and pathfinding
* **Content expansion** - Additional challenges, quests, and regions

---

## 🎯 **Strategic Depth**

### **Character Specialization**
* **Focused builds** - Players must choose which stats to develop
* **Approach optimization** - Different approaches reward different renown types
* **Predictable rewards** - Fixed approach rewards enable strategic planning

### **Temporal Strategy**
* **Early game** - Establish character direction and gather resources
* **Mid game** - Optimize approach selection and manage realm attention
* **Late game** - Position for final challenges and maximize fame gains

### **Spatial Strategy**
* **Movement planning** - Navigate to optimal regions for challenges
* **Feature positioning** - Access special locations and quest opportunities
* **Competitive positioning** - Race for challenges and strategic locations

---

## 🏆 **Victory Conditions**

* **Fame accumulation** - Primary victory metric
* **Strategic timing** - Balance early resource gathering with late-game optimization
* **Character development** - Specialize effectively in chosen approaches
* **Resource management** - Efficient use of realm attention and character stats

---

## 🎨 **Art & Assets**

### **Animation System**
* **135 unique animations** across 9 creature categories
* **Sprite-based rendering** with libGDX
* **Thematic consistency** between animations and challenge descriptions

### **UI Design**
* **Clean, functional interface** with resource bars and action menus
* **Color-coded renown types** for easy identification
* **Responsive layout** adapting to different screen sizes

---

## 🔮 **Future Vision**

Heroes Journey aims to create a **strategic, story-rich RPG experience** that combines:
* **Deep character customization** through focused stat development
* **Meaningful choices** in approach selection and resource management
* **Competitive gameplay** through scarce resources and racing objectives
* **Rich thematic content** with 135 unique challenges and diverse approaches
* **Accessible complexity** through clear UI and intuitive systems

The game successfully bridges **RPG character development** with **board game strategic depth**, creating a unique hybrid experience that rewards both tactical thinking and long-term planning.