# 🛠️ Action Modlib Migration Plan (Updated June 2024)

## Overview

Actions are the most complex Registrable to migrate due to their ability to modify the game state in many ways. The goal is to expose a safe, high-level API for mods to define actions, without giving direct access to ECS internals or unsafe mutations.

## Current State of Migration

- `IActionContext` is now an **abstract class** in `modlib` with abstract methods for stat and buff modification (`getStat`, `addStat`, `adjustStat`, `addBuff`).
- `ActionContext` in core implements all required methods, routing to ECS/component logic.
- **All training actions and the rest action** in `core_actions.kts` have been refactored to use only the context API for stat and buff changes. No direct component access remains in these mod actions.
- Many niche or UI/game-control actions (e.g., open menu, exit game, end turn, save, popup) have been moved out of the mod layer and into the core module. The modlib API is now focused on reusable, safe, high-level game state changes that are appropriate for modders.
- The migration is now centered on expanding the context API as new reusable needs arise in mod actions.

## IActionContext Methods: Status & Usage Chart

| Method Signature                                 | Status      | Used in / Needed for                | `_actions.kts` File(s)         |
|--------------------------------------------------|-------------|-------------------------------------|--------------------------------|
| `getStat(entityId: UUID, statId: String): Int`   | Implemented | Training actions (stat checks)      | `core_actions.kts`             |
| `addStat(entityId: UUID, statId: String, delta: Int)` | Implemented | Training actions (stat cost)        | `core_actions.kts`             |
| `adjustStat(entityId: UUID, statId: String, delta: Int)` | Implemented | Training actions (stat reward)      | `core_actions.kts`             |
| `addBuff(entityId: UUID, buffId: String)`        | Implemented | Rest action (add 'rested' buff)     | `core_actions.kts`             |
|                                                  |             |                                     |                                |
| `moveEntity(entityId: UUID, position: Position)` | Not added   | Movement actions (e.g. travel)      | `delve_actions.kts`, etc.      |
| `addFame(entityId: UUID, amount: Int)`           | Not added   | Fame reward actions                 | `delve_actions.kts`, etc.      |
| `showPopup(message: String)`                     | Not added   | Popup/message actions               | `core_actions.kts` (was)       |
| `triggerAction(entityId: UUID, actionId: String, params: Map<String, String>)` | Not added | Chained/triggered actions           | `core_actions.kts`, others     |
| `saveGame(filename: String, useJson: Boolean)`   | Not added   | Save action                         | `core_actions.kts` (was)       |
| `exitToMenu()`                                   | Not added   | Exit game action                    | `core_actions.kts` (was)       |
| `endTurn()`                                      | Not added   | End turn action                     | `core_actions.kts` (was)       |
| `setOption(optionId: String, value: Any)`        | Not added   | Option/toggle actions               | `core_actions.kts` (was)       |
| `getTargets(regionId: UUID): List<UUID>`         | Not added   | Target selection actions            | `quest_actions.kts`, etc.      |
| `addQuest(entityId: UUID, questId: String)`      | Not added   | Quest management                    | `quest_actions.kts`            |
| `removeQuest(entityId: UUID, questId: String)`   | Not added   | Quest management                    | `quest_actions.kts`            |
| `completeQuest(entityId: UUID, questId: String)` | Not added   | Quest completion                    | `quest_actions.kts`            |
| `addItem(entityId: UUID, itemId: String, amount: Int)` | Not added | Inventory management                | `delve_actions.kts`, etc.      |
| `applyStatusEffect(entityId: UUID, effectId: String)` | Not added | Status effect actions               | `delve_actions.kts`, etc.      |
| `removeChallenge(regionId: UUID, challengeId: String)` | Not added | Challenge management                | `challenge_actions.kts`        |
| `grantReward(entityId: UUID, rewardType: String, amount: Int)` | Not added | Reward distribution                 | `challenge_actions.kts`, etc.  |

## Game State Changes in Existing Actions

- **Stat and Buff Modification:** Now fully routed through the context API for all training and rest actions.
- **Other game state changes** (movement, inventory, quest, challenge, etc.) will be added to the context API as needed, following the same pattern.
- **UI and game control actions** are now handled in core, not exposed to mods.

## Merged/Unified Concepts

- **Stat and Buff changes** are unified as context API methods.
- **Other operations** (inventory, quest, challenge, etc.) will be unified as high-level context methods as they are migrated.

## Recommendations for Modlib API

- Continue to expose only safe, high-level methods in `IActionContext` for operations that are reusable and appropriate for mods.
- All mutations must go through these methods; no direct ECS/component access in mods.
- Provide clear documentation and examples for each method as the API expands.

## Next Steps

1. Expand `IActionContext` with new methods as new reusable needs arise in mod actions (e.g., inventory, quest, challenge management).
2. Refactor additional mod actions to use the context API as new methods are added.
3. Continue to move niche or UI/game-control actions into core as appropriate.
4. Test and iterate for safety and usability.

---

**This migration is now well underway. The foundation is complete for stat and buff changes, and the pattern is established for future expansion.** 
