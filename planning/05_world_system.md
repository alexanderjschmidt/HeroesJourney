# 🌍 **World & Map System**

## **Overview**

The world is a **procedurally generated tile-based world** using libGDX, featuring multiple regions with unique features and terrain types. The world provides the spatial framework for challenges, quests, and player movement.

## **Map Generation**

### **Procedural Generation Pipeline**
1. **Noise Generation** - Create base terrain using cellular automata
2. **Region Generation** - Divide world into Voronoi regions
3. **Biome Assignment** - Assign biomes to regions based on terrain
4. **Feature Placement** - Add towns, dungeons, and other features
5. **Path Generation** - Create roads and connections between features
6. **Tile Refinement** - Apply wave function collapse for detailed tiles

### **Map Parameters**
* **Map Size**: 100x100 tiles (configurable)
* **Region Count**: 3-6 regions per player
* **Feature Density**: 1-3 challenges per region
* **Quest Boards**: Available in towns and cities

### **Generation Phases**
* **Noise Phase** - Base terrain generation using cellular automata
* **World Gen Phase** - Region and biome creation
* **Post World Gen Phase** - Feature placement and refinement
* **Entity Phase** - Player and NPC placement

## **Region System**

### **Region Structure**
* **Voronoi Regions** - Geometric regions created from center points
* **Ring-based Layout** - Regions organized in concentric rings
* **Biome Assignment** - Each region assigned a biome type
* **Feature Generation** - Regions contain towns, dungeons, etc.

### **Region Components**
* **RegionComponent** - Tracks region boundaries and properties
* **PositionComponent** - Region center coordinates
* **QuestsComponent** - Quest board functionality
* **PossibleActionsComponent** - Available actions in region

### **Region Types**
* **Capital Regions** - Central regions with high feature density
* **Town Regions** - Smaller regions with quest boards
* **Wilderness Regions** - Remote regions with challenges
* **Border Regions** - Transitional regions between biomes

## **Biome System**

### **Biome Types**
* **Kingdom Biome** - Plains terrain with towns and kingdoms
* **Desert Kingdom Biome** - Sand terrain with scattered settlements
* **Mesa Kingdom Biome** - Hills terrain with elevated features

### **Biome Properties**
* **Base Terrain** - Primary terrain type for the biome
* **Feature Generation** - Types and density of features
* **Challenge Distribution** - Types of challenges found in biome
* **Quest Availability** - Quest types common in biome

### **Biome Definition**
```kotlin
biome {
    id = Ids.BIOME_KINGDOM
    baseTerrain = Ids.TERRAIN_PLAINS
    feature { 
        featureTypeId = Ids.KINGDOM
        minDist = 0
        minInRegion = 1
        maxInRegion = 1 
    }
    feature { 
        featureTypeId = Ids.TOWN
        minDist = 5
        minInRegion = 3
        maxInRegion = 5 
    }
}.register()
```

## **Terrain System**

### **Terrain Types**
* **Water** - Impassable terrain, visual only
* **Plains** - Basic movement terrain
* **Hills** - Elevated terrain with visual effects
* **Sand** - Desert terrain
* **Path** - Road connections between features
* **Trees** - Forest terrain with visual effects

### **Terrain Transitions**
* **Plains to Hill** - Cliff transitions
* **Sand to Hill** - Desert cliff transitions
* **Plains to Sand** - Desert edge transitions
* **Land to Water** - Water edge transitions

### **Terrain Properties**
* **Movement Cost** - Visual effects only, no strategic blocking
* **Visual Effects** - Different tile appearances and animations
* **Feature Placement** - Terrain influences feature generation
* **Pathfinding** - Terrain affects movement calculations

## **Feature System**

### **Feature Types**
* **Kingdoms** - Capital cities with high feature density
* **Towns** - Smaller settlements with quest boards
* **Dungeons** - Challenge locations with high challenge density
* **Mines** - Resource locations with specialized challenges

### **Feature Generation**
* **Poisson Disk Sampling** - Ensures minimum distance between features
* **Region-based Placement** - Features placed within region boundaries
* **Density Controls** - Minimum and maximum features per region
* **Type Distribution** - Balanced distribution of feature types

### **Feature Components**
* **FeatureTypeComponent** - Defines feature type and properties
* **RenderComponent** - Visual representation of feature
* **PositionComponent** - Feature location on map
* **ChallengeComponent** - Challenges available at feature

## **Pathfinding System**

### **Movement Mechanics**
* **Tile-based Movement** - Characters move between adjacent tiles
* **Pathfinding Algorithm** - A* pathfinding for optimal routes
* **Movement Validation** - Check if movement is possible
* **Turn-based Movement** - Movement occurs during player turns

### **Movement Actions**
* **Travel Action** - Move to adjacent region
* **Path Calculation** - Determine optimal route to destination
* **Movement Animation** - Visual feedback during movement
* **Position Updates** - Update character location after movement

## **Fog of War**

### **Vision System**
* **Character Vision** - Based on character's VISION stat
* **Fog Calculation** - Determines visible areas around character
* **Exploration Tracking** - Remember explored areas
* **Dynamic Updates** - Fog updates as character moves

### **Vision Components**
* **Vision Range** - How far character can see (BODY + 3)
* **Explored Areas** - Previously seen areas remain visible
* **Hidden Features** - Features outside vision range are hidden
* **Information Gathering** - Discover features through exploration

## **World Generation Effects**

### **NoiseMapEffect**
* **Cellular Automata** - Creates natural-looking terrain
* **Smoothing** - Refines terrain edges and transitions
* **Island Generation** - Creates land masses surrounded by water
* **Parameter Control** - Amplitude, octaves, roughness, island mode

### **VoronoiRegionEffect**
* **Center Point Generation** - Creates region center points
* **Ring-based Layout** - Organizes regions in concentric rings
* **Region Assignment** - Assigns tiles to nearest center point
* **Biome Assignment** - Assigns biomes to generated regions

### **Feature Generation Effects**
* **Random Feature Placement** - Places features using Poisson disk sampling
* **Radial Feature Placement** - Places features in circular patterns
* **Connection Effects** - Creates roads between related features
* **Density Controls** - Ensures appropriate feature density

### **Wave Function Collapse**
* **Tile Refinement** - Applies detailed tile patterns
* **Constraint Satisfaction** - Ensures tile compatibility
* **Weighted Selection** - Uses tile weights for natural distribution
* **Environment Layer** - Separate layer for environmental features

## **Strategic Considerations**

### **Movement Planning**
* **Optimal Positioning** - Move to regions with beneficial features
* **Challenge Access** - Position near challenges that suit your build
* **Quest Availability** - Access regions with quest boards
* **Resource Competition** - Race for valuable locations

### **Exploration Strategy**
* **Vision Optimization** - Develop VISION stat for better exploration
* **Information Gathering** - Discover hidden features and challenges
* **Terrain Navigation** - Plan routes through optimal terrain
* **Feature Discovery** - Find valuable locations before opponents

### **Competitive Positioning**
* **Feature Scarcity** - Limited features create competition
* **Optimal Timing** - Arrive at features when they're most valuable
* **Resource Denial** - Occupy features to deny them to opponents
* **Strategic Retreat** - Move away from unfavorable positions 