# HUD Layout Documentation

## Overview
The HUD (Heads-Up Display) in Heroes Journey is built using LibGDX's Scene2D UI framework with a three-column layout system. The HUD manages different states and provides a comprehensive interface for player interaction.

## Layout Structure

### Main Layout (3-Column Design)
```
┌─────────────────┬─────────────────┬─────────────────┐
│   LEFT COLUMN   │   CENTER PANEL  │  RIGHT COLUMN   │
│   (1/5 width)   │   (3/5 width)   │   (1/5 width)   │
│                 │                 │                 │
│  TurnUI         │                 │  ActionMenu     │
│  (7.5% height)  │                 │  (49% height)   │
│                 │                 │                 │
│  [FILLER]       │   StatsUI       │                 │
│  (expandable)   │   PopupUI       │                 │
│                 │                 │                 │
│  EntityUI       │                 │  InfoUI         │
│  (17.5% height) │                 │  (49% height)   │
│                 │                 │                 │
│  TerrainUI      │                 │                 │
│  (7.5% height)  │                 │                 │
└─────────────────┴─────────────────┴─────────────────┘
```

## Component Details

### Left Column (1/5 screen width)
**Position**: Left side of screen
**Width**: `screenWidth / 5`
**Layout**: Vertical stack with percentage-based heights

#### 1. TurnUI (7.5% height)
- **Purpose**: Displays current turn and player information
- **Content**: "Day X Player/Opponent"
- **Position**: Top of left column
- **Padding**: 2.5f bottom

#### 2. Filler Space (expandable)
- **Purpose**: Flexible space between TurnUI and EntityUI
- **Behavior**: Expands to fill available space

#### 3. EntityUI (17.5% height)
- **Purpose**: Displays selected entity information and resource bars
- **Content**: 
  - Entity name
  - Resource bars (Stamina, Focus, Mana, Moxie)
- **Position**: Bottom-middle of left column
- **Padding**: 2.5f top and bottom

#### 4. TerrainUI (7.5% height)
- **Purpose**: Displays terrain and location information
- **Content**: Terrain name, environment, coordinates (debug mode)
- **Position**: Bottom of left column
- **Padding**: 2.5f top

### Center Panel (3/5 screen width)
**Position**: Center of screen
**Width**: Expands to fill available space
**Behavior**: Dynamic content based on HUD state

#### StatsUI (Default State)
- **Purpose**: Displays character stats and quest information
- **Content**: 
  - StatsDisplay (character attributes)
  - QuestsDisplay (active quests)
- **Toggle**: Press NEXT_CHARACTER key to switch between displays
- **Position**: Full center area

#### PopupUI (Popup State)
- **Purpose**: Modal popup dialogs
- **Content**: Custom text and content
- **Position**: Centered with 75% width and 75% height
- **Padding**: 10f around content

### Right Column (1/5 screen width)
**Position**: Right side of screen
**Width**: `screenWidth / 5`
**Layout**: Vertical stack with percentage-based heights

#### 1. ActionMenu (49% height)
- **Purpose**: Displays available actions for selected entity
- **Content**: Scrollable list of action entries
- **Behavior**: 
  - Shows actions for selected entity
  - Shows region/location actions
  - Handles action selection and execution
- **Position**: Top of right column
- **Padding**: 2.5f bottom

#### 2. InfoUI (49% height)
- **Purpose**: Displays detailed information about selected actions
- **Content**: 
  - Title label
  - Custom content table
  - Description body
- **Behavior**: Updates based on hovered action
- **Position**: Bottom of right column
- **Padding**: 2.5f top

## HUD States

### State Machine
The HUD uses a StackStateMachine to manage different UI states:

1. **CURSOR_MOVE** (Default)
   - Normal gameplay state
   - Cursor can move around map
   - All UI components visible

2. **STATS**
   - Shows character statistics
   - Toggle between stats and quests
   - Center panel shows StatsUI

3. **POP_UP**
   - Modal dialog state
   - Center panel shows PopupUI
   - Blocks other interactions

4. **DELVE**
   - Special state for dungeon interactions
   - Custom UI layout

5. **LOCKED**
   - Prevents state changes
   - Used during transitions

6. **GLOBAL**
   - Global state that applies to all other states
   - Handles common functionality

## Cursor System

### Cursor Behavior
- **Position**: Tracks x,y coordinates on game map
- **Visual States**:
  - Blue: Entity selected
  - Red: Entity hovered
  - White: Normal state
- **Map Pointer**: Additional pointer for navigation
- **Path Visualization**: Shows movement paths

### Cursor Functions
- `setPosition(int x, int y)`: Move cursor to coordinates
- `setPosition(UUID entityId)`: Move cursor to entity
- `getSelected()`: Get currently selected entity
- `getHover()`: Get currently hovered entity

## UI Component Classes

### Core Components
- **HUD**: Main container and state manager
- **Cursor**: Mouse/touch input handler
- **UI**: Base class for all UI components

### Window Components
- **ActionMenu**: Action selection interface
- **StatsUI**: Character statistics display
- **InfoUI**: Detailed information panel
- **TerrainUI**: Terrain information display
- **EntityUI**: Entity information and resource bars
- **TurnUI**: Turn and player information
- **PopupUI**: Modal dialog system

### Sub-Components
- **StatsDisplay**: Character attributes
- **QuestsDisplay**: Quest information
- **ResourceBar**: Progress bars for resources
- **ScrollPane**: Scrollable content containers

## Responsive Design

### Viewport
- **Type**: FitViewport
- **Behavior**: Maintains aspect ratio
- **Resize**: Updates on window resize

### Layout Constraints
- **Side Columns**: Fixed width (1/5 screen width each)
- **Center Panel**: Flexible width (expands to fill)
- **Heights**: Percentage-based for vertical components
- **Padding**: Consistent 5f layout padding

## Input Handling

### Key Bindings
- **NEXT_CHARACTER**: Toggle between stats and quests displays
- **Debug Mode**: Shows additional information (coordinates, etc.)

### State Transitions
- **Automatic**: Based on game events
- **Manual**: Through state machine methods
- **Revert**: Return to previous states

## Future Improvements

### TODO Items (from code comments)
1. **Right Column Class**: Move ActionMenu and InfoUI to dedicated class
2. **Left Column Class**: Move TerrainUI, EntityUI, TurnUI to dedicated class
3. **Center Screen Class**: Dedicated class for center panel management
4. **Action Menu Width**: Lock width so middle column can expand
5. **Action Menu Space**: Make it only take needed space with filler row

### Potential Enhancements
- **Responsive Breakpoints**: Different layouts for different screen sizes
- **UI Themes**: Customizable visual themes
- **Animation System**: Smooth transitions between states
- **Accessibility**: Screen reader support and keyboard navigation
- **Localization**: Multi-language support for UI text

## Usage Examples

### Adding New UI Components
```java
// Create new UI component
MyNewUI myUI = new MyNewUI();

// Add to layout
layout.add(myUI).expand().fill();
```

### State Management
```java
// Change HUD state
HUD.get().setState(States.STATS);

// Revert to previous state
HUD.get().revertToPreviousState();
```

### Updating UI Content
```java
// Update center panel
HUD.get().updateCenterPanel();

// Set entity for stats display
statsUI.setEntity(entityId);
```

This documentation provides a comprehensive overview of the HUD system architecture and can be used as a reference for future development and modifications. 