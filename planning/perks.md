# Heroes Journey - Perk System Design

## Design Philosophy

The perk system is designed around the core game loop where:
- **Challenges are always defeated** - no failure states or combat mechanics
- **Movement is guaranteed** - players can always move between regions
- **Resource optimization is key** - managing realm attention to maximize rewards
- **Strategic positioning matters** - where you are and what challenges you face

## Perk Categories

### Realm Attention Efficiency Perks

#### BODY-based perks:
- **Endurance Training** (BODY 5): Gain +1 realm attention when facing challenges in consecutive turns
- **Legendary Stamina** (BODY 10): Challenges in the same region cost 1 less realm attention

#### MIND-based perks:
- **Strategic Planning** (MIND 5): See realm attention costs before facing challenges
- **Master Tactician** (MIND 10): Gain +1 realm attention when facing challenges with your highest stat

#### MAGIC-based perks:
- **Arcane Resonance** (MAGIC 5): Gain +1 realm attention when facing magical challenges
- **Realm Attunement** (MAGIC 10): Convert excess realm attention to other stats at 2:1 ratio

#### CHARISMA-based perks:
- **Inspiring Presence** (CHARISMA 5): Gain +1 realm attention when other players are in the same region
- **Legendary Reputation** (CHARISMA 10): Start each turn with +1 realm attention in all stats

### Challenge Optimization Perks

#### Cross-Stat Synergies:
- **Versatile Hero** (BODY 3 + MIND 3): Can use any of your top 3 stats for any challenge
- **Adaptive Approach** (MIND 5 + CHARISMA 5): Reroll one challenge approach per turn
- **Magical Flexibility** (MAGIC 5 + MIND 5): Convert one stat to another at 2:1 ratio when facing challenges

### Movement and Positioning Perks

#### Strategic Movement:
- **Swift Traveler** (BODY 5): Move to any adjacent region for free once per turn
- **Scout's Knowledge** (MIND 5): See challenges in adjacent regions
- **Telepathic Link** (MAGIC 5): Share realm attention with other players in the same region

## Implementation Notes

### Perk Unlock System
- Perks unlock based on base stat thresholds (not current stats)
- Some perks require multiple stats at certain levels
- Perks are permanent once unlocked
- Consider tiered progression (Bronze/Silver/Gold levels)

### Balance Considerations
- Perks should enhance strategic depth without breaking the core loop
- Focus on resource optimization rather than power increases
- Cooperative perks should encourage multiplayer interaction
- Information perks should reduce randomness without eliminating it

### Future Expansion Ideas
- **Quest-specific perks**: Unlock special abilities for certain challenge types
- **Terrain perks**: Special abilities in specific terrain types
- **Reputation perks**: Different perks based on your legend status
- **Equipment synergy**: Perks that work with specific item types 