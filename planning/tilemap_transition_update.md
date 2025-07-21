# 🎯 Tilemap System Upgrade: Height-Based Terrain Transitions with Animation Support

---

## 📝 Current Implementation Context

- **Tile Storage:** Each tile is represented by a `Tile` object, which stores its `Terrain` type and neighbor terrain types (for Wang/WFC logic). There is currently **no height/elevation property** on tiles.
- **Rendering:** The `TileMap` class renders each tile and an optional environment layer. There is **no logic for alpha-blended overlays** or smooth transitions between different terrain types.
- **Transitions:** Transition tiles (e.g., PLAINS_TO_WATER, PLAINS_TO_HILL) are defined in KTS scripts and used in map generation, but these are based on terrain type only, not height.
- **Wave Function Collapse:** Map generation uses WFC, which selects tiles based on neighbor constraints (terrain only).
- **Animation:** Animated tiles are supported for water and some transitions, but not for overlays or alpha masks.
- **Alpha Mask Assets:** Alpha mask images for blending are present in `Textures/tile_layouts/`, but are **not yet used in code**.

---

## ✅ Goal

Refactor the tilemap rendering system to support:

1. **Smooth alpha-based transitions** between terrain types *at the same elevation*.
2. **Hard transitions (cliffs)** when terrain height changes between adjacent tiles.
3. Optional **animations** for both soft and hard transitions:
    - Water washing up on shore (soft)
    - Cliff-face wave splash or shimmer effects (hard)

---

## 🛣️ Implementation Plan (Staged Approach)

### **Stage 1: Smooth Alpha Transitions (No Heightmap Yet)**

- [ ] **Alpha-blended overlays**: For adjacent tiles with different terrain types (e.g., grass next to sand), render an alpha mask overlay using the assets in `Textures/tile_layouts/`.
- [ ] **Rendering logic**: Update the `TileMap` rendering code to:
    - For each tile, check neighbors for different terrain types.
    - If a neighbor is a different terrain (but same elevation, since height is not yet implemented), overlay the appropriate alpha mask to blend the transition.
- [ ] **No height/elevation logic yet**: All transitions are based solely on terrain type for now.
- [ ] **Animation (optional)**: If time allows, support animated alpha masks for transitions like water edges.

### **Stage 2: Add Heightmap and Cliff Transitions**

- [ ] **Tile height property**: Extend the `Tile` class to include a `heightLevel` property.
- [ ] **Map generation**: Update WFC and map scripts to assign and propagate height levels.
- [ ] **Transition logic**: 
    - If `deltaHeight == 0`, use alpha-blended overlays as in Stage 1.
    - If `deltaHeight == 1`, render a cliff tile or overlay (hard transition).
    - If `deltaHeight > 1`, consider multi-level cliff or fallback.
- [ ] **Rendering pipeline**: Add support for cliff overlays, shadows, and animated cliff effects as described in the original plan.

---

## 🧱 Tile Data Requirements

- [ ] Each tile must store:
    - `primaryTerrainType` (e.g., GRASS, WATER, SAND, STONE)
    - *(Stage 2+)* `heightLevel` (integer, e.g., 0 = ground, 1 = hill, 2 = mountain)

---

## 🌿 Smooth Transitions (Same Height)

- [x] *(Stage 1 focus)* For adjacent tiles with the **same elevation** (currently, all tiles), use **alpha-blended transitions** between differing terrain types.
- [ ] Define a set of reusable **alphamap masks**:
    - Edge blends
    - Corner blends
    - 3-way and 4-way junctions
- [ ] Use base terrain tile as a base, and overlay neighboring terrain types using alpha masks.

---

## ⛰️ Cliff Transitions (Height Differences)

- *(Stage 2+)* For adjacent tiles where `abs(height difference) == 1`, insert a **cliff tile**.
    - Use a **Wang-style cliff tileset** for directionality.
    - Cliff tile visually replaces blending — this is a hard cutoff.
- *(Stage 2+)* Add **height shadow overlays** and cliff-top/bottom edge tiles as needed.

---

## 🌊 Animation Support

- *(Optional, both stages)* Allow animated alpha masks for water wash / shoreline motion.
- *(Stage 2+)* Animated cliff tiles for effects like splashes or shimmer.

---

## 🛠️ Rendering Pipeline

Suggested tile rendering layers:

1. **Base terrain layer** (static or animated)
2. **Alpha blend overlays** (only if `deltaHeight == 0`)
3. **Cliff tiles** (inserted if `deltaHeight == 1`)
4. **Cliff overlays & animated FX**
5. **Decals / props / UI markers**

---

## 🔜 Next Steps

- [ ] Implement Stage 1: Alpha-blended overlays for smooth terrain transitions at the same elevation.
- [ ] Once stable, proceed to Stage 2: Add heightmap and cliff transition logic.

---

*This document will be updated as implementation progresses and requirements evolve.*
