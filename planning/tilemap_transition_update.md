# 🎯 Tilemap System Upgrade: Height-Based Terrain Transitions with Animation Support

## ✅ Goal

Refactor the tilemap rendering system to support:

1. **Smooth alpha-based transitions** between terrain types *at the same elevation*.
2. **Hard transitions (cliffs)** when terrain height changes between adjacent tiles.
3. Optional **animations** for both soft and hard transitions:
    - Water washing up on shore (soft)
    - Cliff-face wave splash or shimmer effects (hard)

---

## 🧱 Tile Data Requirements

- [ ] Each tile must store:
    - `primaryTerrainType` (e.g., GRASS, WATER, SAND, STONE)
    - `heightLevel` (integer, e.g., 0 = ground, 1 = hill, 2 = mountain)

- [ ] Tile transitions are not based on terrain type alone, but on **relative elevation between neighbors**:
    - `deltaHeight == 0` → soft blend
    - `deltaHeight == 1` → cliff face (hard transition)
    - `deltaHeight > 1` → optional: stacked or multi-level cliff representation

---

## 🌿 Smooth Transitions (Same Height)

- [ ] For adjacent tiles with the **same `heightLevel`**, use **alpha-blended transitions** between differing
  terrain types.
- [ ] Define a set of reusable **alphamap masks**:
    - Edge blends
    - Corner blends
    - 3-way and 4-way junctions

- [ ] Use base terrain tile as a base, and overlay neighboring terrain types using alpha masks.
- [ ] Only apply if `tile.height == neighbor.height`.

---

## ⛰️ Cliff Transitions (Height Differences)

- [ ] For adjacent tiles where `abs(height difference) == 1`, insert a **cliff tile**.
    - Use a **Wang-style cliff tileset**:
        - Directions: North, South, East, West, and diagonal/corner transitions
    - Cliff tile visually replaces blending — this is a hard cutoff.

- [ ] Cliff tile occupies either the lower or upper tile, depending on rendering approach:
    - Option A: Draw cliff overlay *on the lower tile* to show face of elevation.
    - Option B: Have dedicated cliff tiles in the map data replacing both tile types.

- [ ] Optional:
    - Add **height shadow overlays** to make elevation more readable.
    - Define cliff-top edge tiles and cliff-bottom support tiles.

---

## 🌊 Animation Support

### Soft Transitions (e.g., water to grass, same elevation):

- Allow animated alpha masks for water wash / shoreline motion.
- Loop through subtle edge frames (e.g., ripple frames).

### Hard Transitions (e.g., cliff next to water):

- Cliff tiles can have animated variants (e.g., splashes, crashing waves).
- Animation frames assigned per tile direction (e.g., downward cliff into water).

### General Animation Requirements:

- [ ] Tiles can reference:
    - Animation frame set
    - Frame duration
    - Loop behavior
    - Offset timer (for desync)

---

## 🛠️ Rendering Pipeline

Suggested tile rendering layers:

1. **Base terrain layer** (static or animated)
2. **Alpha blend overlays** (only if `deltaHeight == 0`)
3. **Cliff tiles** (inserted if `deltaHeight == 1`)
4. **Cliff overlays & animated FX**
5. **Decals / props / UI markers**
