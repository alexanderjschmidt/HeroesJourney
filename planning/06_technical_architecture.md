# 🏗️ **Technical Architecture**

## **Multi-Module Architecture**

Heroes Journey uses a modular architecture with clear separation of concerns:

### **Core Module (`core`)**
* **Main game logic** - Game state management and simulation
* **Client-side rendering** - Graphics and UI using libGDX
* **Multiplayer client** - Network communication and lobby management
* **Mod system** - Content loading and hot-reloading
* **Save/load system** - Game state persistence

### **Server Module (`server`)**
* **Dedicated server** - Lobby management and multiplayer functionality
* **Connection handling** - TCP/UDP server on ports 54555/54777
* **Player session management** - Track connected players and lobby assignments
* **Network communication** - KryoNet-based message handling

### **Shared Module (`shared`)**
* **Network messages** - Request/response classes for lobby operations
* **Data models** - Shared data structures between client/server
* **Serialization** - Kryo class registration for network transmission
* **Constants** - Server configuration and shared constants

### **LWJGL3 Module (`lwjgl3`)**
* **Desktop platform launcher** - Using LWJGL3 for desktop deployment
* **Platform-specific code** - Windows, macOS, Linux support
* **Application lifecycle** - Startup, shutdown, and window management

## **Engine & Framework**

### **libGDX Framework**
* **Cross-platform support** - Windows, macOS, Linux, Android, iOS
* **Graphics rendering** - Sprite-based 2D graphics with OpenGL
* **Input handling** - Mouse and keyboard interaction
* **Audio system** - Music and sound effect management
* **UI framework** - Scene2D for HUD and menus

### **Artemis-ODB ECS**
* **Entity-Component-System** - Clean separation of data and logic
* **Component management** - Efficient data storage and retrieval
* **System processing** - Organized game logic execution
* **Entity lifecycle** - Creation, modification, and destruction

### **KryoNet Networking**
* **Network communication** - TCP/UDP for multiplayer
* **Message serialization** - Efficient binary protocol
* **Connection management** - Reliable client-server communication
* **Lobby system** - Player matching and session management

### **Kotlin DSL Modding**
* **Domain-specific language** - Declarative content creation
* **Script-based loading** - Mods loaded from `mods/` directory
* **Hot-reloading** - Mods can be reloaded without restart
* **Registry system** - Centralized content registration

### **Gradle Build System**
* **Dependency management** - Automatic library resolution
* **Multi-module builds** - Coordinated compilation across modules
* **Platform targeting** - Cross-platform deployment
* **Development tools** - IDE integration and debugging

## **Client-Side Systems**

### **Game Engine**
* **Main game loop** - Fixed timestep simulation
* **Entity management** - ECS-based game object system
* **World simulation** - Turn-based gameplay logic
* **State management** - Game state persistence and loading

### **Rendering Pipeline**
* **Sprite-based graphics** - 2D rendering with libGDX
* **Tile-based rendering** - Efficient map display
* **Animation system** - Sprite animations for creatures and effects
* **UI rendering** - HUD, menus, and interface elements

### **UI Framework**
* **Scene2D** - libGDX UI framework
* **HUD system** - Player information and controls
* **Action menus** - Context-sensitive action selection
* **Resource displays** - Stats, inventory, and quest information
* **Popup system** - Modal dialogs and notifications

### **Input Handling**
* **Mouse interaction** - Click detection and drag operations
* **Keyboard shortcuts** - Hotkeys for common actions
* **Touch support** - Mobile-friendly input handling
* **Input validation** - Context-sensitive input processing

### **Audio System**
* **Music management** - Background music and ambient sounds
* **Sound effects** - Action sounds and feedback
* **Audio mixing** - Volume control and audio prioritization
* **Platform audio** - Cross-platform audio support

### **Save/Load System**
* **JSON serialization** - Human-readable save files
* **Kryo serialization** - Binary save files for performance
* **Auto-save** - Automatic game state preservation
* **Save validation** - Integrity checking and error recovery

### **Multiplayer Client**
* **Network connection** - TCP/UDP client implementation
* **Lobby management** - Join/create game sessions
* **Message handling** - Network message processing
* **Connection state** - Online/offline status management

### **Mod System**
* **Content loading** - Dynamic mod discovery and loading
* **Hot-reloading** - Runtime mod updates
* **Registry management** - Centralized content registration
* **Error handling** - Mod validation and error reporting

## **Server-Side Systems**

### **Lobby Management**
* **Lobby creation** - Host new multiplayer sessions
* **Player joining** - Handle player connection requests
* **Session tracking** - Monitor active game sessions
* **Player limits** - Support up to 6 players per lobby

### **Connection Handling**
* **TCP server** - Port 54555 for reliable communication
* **UDP server** - Port 54777 for fast updates
* **Connection pooling** - Efficient resource management
* **Error recovery** - Network failure handling

### **Player Session Management**
* **Player tracking** - Monitor connected players
* **Lobby assignments** - Associate players with game sessions
* **Session state** - Track lobby and game state
* **Disconnect handling** - Graceful player departure

### **Network Communication**
* **Message routing** - Direct messages to appropriate handlers
* **Protocol validation** - Ensure message format compliance
* **Rate limiting** - Prevent network abuse
* **Error reporting** - Network issue diagnostics

## **Shared Systems**

### **Network Messages**
* **CreateLobbyRequest/Response** - Lobby creation protocol
* **JoinLobbyRequest/Response** - Player joining protocol
* **UpdateLobbyPlayers** - Player list synchronization
* **Game state messages** - In-game communication

### **Data Models**
* **Lobby** - Lobby state and player management
* **LobbyInfo** - Lobby information for UI display
* **MapData** - Map configuration (seed, size, teams, fog of war)
* **PlayerInfo** - Player data and preferences

### **Serialization**
* **Kryo registration** - Class registration for network transmission
* **Message serialization** - Efficient binary protocol
* **Data validation** - Message integrity checking
* **Version compatibility** - Backward compatibility support

### **Constants**
* **Server configuration** - TCP/UDP ports and settings
* **Game constants** - Shared game parameters
* **Network timeouts** - Connection and message timeouts
* **Platform settings** - Cross-platform configuration

## **System Architecture**

### **Entity-Component-System (ECS)**
* **Entities** - Game objects with unique IDs
* **Components** - Data containers for entity properties
* **Systems** - Logic processors that operate on components
* **Aspects** - Queries for entities with specific components

### **System Categories**
* **Constant Systems** - Always active (rendering, input)
* **Triggerable Systems** - Activated by specific events
* **AI Systems** - Computer-controlled entity behavior
* **Management Systems** - Game state and resource management

### **Component Types**
* **Data Components** - Pure data storage (Position, Stats)
* **Behavior Components** - Control entity behavior (AI, Player)
* **Visual Components** - Rendering and display (Render, Animation)
* **System Components** - System-specific data (Movement, Action)

### **Event System**
* **Event Queue** - Asynchronous event processing
* **Event Types** - Turn events, movement events, action events
* **Event Handlers** - System responses to events
* **Event Propagation** - Event routing and delivery

## **Performance Considerations**

### **Rendering Optimization**
* **Sprite batching** - Efficient draw call batching
* **Texture atlasing** - Reduced texture switching
* **Culling** - Only render visible entities
* **LOD system** - Level of detail for distant objects

### **Memory Management**
* **Object pooling** - Reuse frequently created objects
* **Component pooling** - Efficient component lifecycle management
* **Resource caching** - Cache frequently used assets
* **Garbage collection** - Minimize GC pressure

### **Network Optimization**
* **Message compression** - Reduce network bandwidth
* **Delta updates** - Only send changed data
* **Connection pooling** - Reuse network connections
* **Rate limiting** - Prevent network spam

### **Mod System Performance**
* **Lazy loading** - Load mods on demand
* **Parallel processing** - Concurrent mod loading
* **Caching** - Cache compiled mod scripts
* **Error isolation** - Prevent mod errors from crashing game

## **Development Workflow**

### **Build Process**
* **Gradle builds** - Automated compilation and packaging
* **Multi-module coordination** - Synchronized builds across modules
* **Dependency resolution** - Automatic library management
* **Platform targeting** - Cross-platform deployment

### **Testing Strategy**
* **Unit testing** - Individual component testing
* **Integration testing** - System interaction testing
* **Mod testing** - Content validation and testing
* **Performance testing** - Load and stress testing

### **Deployment Pipeline**
* **Development builds** - Fast iteration and testing
* **Release builds** - Optimized production builds
* **Platform packaging** - Platform-specific deployment
* **Distribution** - Automated release distribution 