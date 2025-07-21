# 📚 **Heroes Journey - Documentation Index**

This directory contains the complete documentation for Heroes Journey, organized into focused documents for better readability and maintenance.

## **📖 Documentation Structure**

### **🎯 [Core Concept](01_core_concept.md)**
* Game overview and primary objectives
* Victory conditions and game structure
* Core gameplay loop and strategic depth
* Temporal and spatial strategy considerations

### **👤 [Character System](02_character_system.md)**
* Base stats (BODY, MIND, MAGIC, CHARISMA)
* Renown stats (VALOR, INSIGHT, ARCANUM, INFLUENCE)
* Calculated stats and stat groups
* Character progression and specialization
* Component architecture and resource management

### **⚔️ [Challenge System](03_challenge_system.md)**
* 135 unique challenges across 9 categories
* Challenge type system and approach filtering
* 16 approach system with confluence stats
* Reward system and strategic considerations
* Challenge distribution and implementation

### **🏆 [Quest System](04_quest_system.md)**
* Quest structure and mechanics
* Quest types (training, exploration, specialized)
* Quest availability and completion
* Strategic considerations and resource management
* Quest generation and balance

### **🌍 [World System](05_world_system.md)**
* Procedural map generation pipeline
* Region system and Voronoi regions
* Biome system and terrain types
* Feature system and pathfinding
* Fog of war and exploration mechanics

### **🏗️ [Technical Architecture](06_technical_architecture.md)**
* Multi-module architecture (core, server, shared, lwjgl3)
* Engine frameworks (libGDX, Artemis-ODB, KryoNet)
* Client-side and server-side systems
* Performance considerations and development workflow
* System architecture and event handling

### **🔧 [Modding System](07_modding_system.md)**
* Mod architecture and modlib API layer
* Mod loading system and DSLs
* Registry system and content creation
* Advanced modding features and best practices
* Community expansion and testing flexibility

### **📊 [Implementation Status](08_implementation_status.md)**
* Completed systems and features
* In-development features and priorities
* Planned features and roadmap
* Development priorities and testing strategy

## **🔗 Quick Reference**

### **Game Overview**
* **Type**: Competitive turn-based boardgame-style RPG
* **Objective**: Earn the most Fame through challenges, quests, and character development
* **Duration**: ~1 hour per game
* **Players**: 1-6 players (competitive)

### **Core Mechanics**
* **Turn-based**: Each turn represents a full day
* **Action-based**: One action per turn (challenge, quest, travel, etc.)
* **Stat-driven**: Character stats determine available approaches and rewards
* **Resource management**: Balance renown earning vs. spending

### **Key Systems**
* **135 Challenges** across 9 categories with 16 approaches
* **Quest System** with costs, rewards, and fame points
* **Procedural World** with regions, biomes, and features
* **Mod System** with Kotlin DSL for content creation
* **Multiplayer** with lobby system and network communication

### **Technical Stack**
* **libGDX** - Cross-platform game framework
* **Artemis-ODB** - Entity-Component-System
* **KryoNet** - Network communication
* **Kotlin DSL** - Modding system
* **Gradle** - Build system

## **📝 Documentation Guidelines**

### **Maintenance**
* Keep documents up-to-date with code changes
* Add new sections as features are implemented
* Update implementation status regularly
* Maintain cross-references between documents

### **Organization**
* Each document focuses on a specific system or aspect
* Use consistent formatting and structure
* Include code examples where relevant
* Provide strategic considerations for gameplay

### **Accessibility**
* Clear headings and subheadings
* Consistent emoji usage for visual organization
* Code blocks for technical examples
* Strategic insights for game design

## **🚀 Getting Started**

1. **Read Core Concept** - Understand the game's fundamental design
2. **Review Character System** - Learn about stats and progression
3. **Explore Challenge System** - Understand the main gameplay loop
4. **Check Implementation Status** - See what's completed and what's planned
5. **Dive into Technical Architecture** - For developers and contributors

## **🤝 Contributing**

When updating documentation:
* Maintain the existing structure and formatting
* Update related documents when making changes
* Add new documents for major new systems
* Keep implementation status current
* Include code examples for technical features

---

*Last updated: [Current Date]*
*Version: [Current Version]* 