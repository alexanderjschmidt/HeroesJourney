import heroes.journey.modlib.config.turnConfig

// Turn-based progression configuration
// Controls challenge power tiers, ambient lighting, and special events

// Early game (turns 1-10) - Tutorial and basic challenges
turnConfig {
    id = "turn_5_power_increase"
    turnNumber = 5
    applyConfig = { context ->
        context.setMinChallengePowerTier(1)
        context.setMaxChallengePowerTier(2)
        context.setAmbientLighting(0.9f)
    }
}.register()

turnConfig {
    id = "turn_10_power_increase"
    turnNumber = 10
    applyConfig = { context ->
        context.setMinChallengePowerTier(1)
        context.setMaxChallengePowerTier(3)
        context.setAmbientLighting(0.85f)
    }
}.register()

// Mid game (turns 11-30) - Increasing difficulty
turnConfig {
    id = "turn_20_power_increase"
    turnNumber = 20
    applyConfig = { context ->
        context.setMinChallengePowerTier(2)
        context.setMaxChallengePowerTier(5)
        context.setAmbientLighting(0.75f)
    }
}.register()

turnConfig {
    id = "turn_30_power_increase"
    turnNumber = 30
    applyConfig = { context ->
        context.setMinChallengePowerTier(3)
        context.setMaxChallengePowerTier(7)
        context.setAmbientLighting(0.65f)
    }
}.register()

// Late game (turns 31-50) - High difficulty
turnConfig {
    id = "turn_40_power_increase"
    turnNumber = 40
    applyConfig = { context ->
        context.setMinChallengePowerTier(5)
        context.setMaxChallengePowerTier(9)
        context.setAmbientLighting(0.5f)
    }
}.register()

turnConfig {
    id = "turn_50_power_increase"
    turnNumber = 50
    applyConfig = { context ->
        context.setMinChallengePowerTier(7)
        context.setMaxChallengePowerTier(10)
        context.setAmbientLighting(0.4f)
    }
}.register()

// End game (turns 51+) - Maximum difficulty and demon king
turnConfig {
    id = "turn_60_demon_king_arrival"
    turnNumber = 60
    applyConfig = { context ->
        context.setMinChallengePowerTier(8)
        context.setMaxChallengePowerTier(11)
        context.setAmbientLighting(0.3f)
        context.spawnDemonKing()
        context.setGameStateValue("special_event", "demon_king_arrival")
    }
}.register()

turnConfig {
    id = "turn_70_apocalypse"
    turnNumber = 70
    applyConfig = { context ->
        context.setMinChallengePowerTier(9)
        context.setMaxChallengePowerTier(11)
        context.setAmbientLighting(0.2f)
        context.setGameStateValue("special_event", "apocalypse")
    }
}.register()
