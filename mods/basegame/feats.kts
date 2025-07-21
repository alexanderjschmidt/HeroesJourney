import heroes.journey.modlib.Ids
import heroes.journey.modlib.misc.feat
import heroes.journey.modlib.misc.FeatType

// Example feats for the base game
feat {
    id = Ids.FEAT_WEAPON_MASTERY
    featType = FeatType.PASSIVE
    onEarnFn = { context ->
        // Add weapon mastery bonus to the player
        // This would typically modify stats or add abilities
    }
    onLoseFn = { context ->
        // Remove weapon mastery bonus from the player
        // This would typically restore original stats
    }
}.register()

feat {
    id = Ids.FEAT_IRON_WILL
    featType = FeatType.PASSIVE
    onEarnFn = { context ->
        // Add mental resistance bonus
    }
    onLoseFn = { context ->
        // Remove mental resistance bonus
    }
}.register()

feat {
    id = Ids.FEAT_BERSERKER_RAGE
    featType = FeatType.ACTIVE
    onEarnFn = { context ->
        // Grant the ability to enter berserker rage
    }
    onLoseFn = { context ->
        // Remove the berserker rage ability
    }
}.register() 