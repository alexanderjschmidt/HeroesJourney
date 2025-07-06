import heroes.journey.GameState
import heroes.journey.components.ChallengeComponent
import heroes.journey.components.NamedComponent
import heroes.journey.components.QuestsComponent
import heroes.journey.components.RegionComponent
import heroes.journey.components.StatsComponent
import heroes.journey.entities.Challenge
import heroes.journey.entities.Quest
import heroes.journey.entities.actions.*
import heroes.journey.entities.actions.results.EndTurnResult
import heroes.journey.entities.tagging.Attributes
import heroes.journey.entities.tagging.Stat
import heroes.journey.initializers.utils.Utils
import heroes.journey.mods.gameMod
import heroes.journey.registries.Registries
import heroes.journey.registries.Registries.QuestManager
import java.util.*

gameMod("Challenge Actions", 0) {
    // Quest Action
    action {
        id = "quest"
        name = "Accept Quest"
        description = "Accept a quest to complete"
        inputDisplayNameFn = { input ->
            QuestManager.get(input["target"])!!.getName()
        }
        onSelectFn = { input ->
            val town: UUID = UUID.fromString(input["owner"])
            val factionsQuestsComponent = QuestsComponent.get(
                input.gameState.world,
                town
            )

            val quest: Quest = QuestManager.get(input["target"])!!
            if (factionsQuestsComponent != null) {
                factionsQuestsComponent.remove(quest)
                QuestsComponent.get(input.gameState.world, input.entityId)
                    .addQuest(quest)
            }
            EndTurnResult()
        }
    }.register()

    // Quest Board
    targetAction<Quest> {
        id = "quest_board"
        name = "Quest Board"
        description = "See what the people need help with"
        inputDisplayNameFn = { input ->
            val gs: GameState = GameState.global()
            "Quest Board for " + NamedComponent.get(gs.world, UUID.fromString(input["owner"]), "Unknown")
        }
        getTargets = { input ->
            val town = UUID.fromString(input["owner"])
            QuestsComponent.get(input.gameState.world, town).quests
        }
        targetAction = "quest"
    }.register()

    // Choose Approach
    action {
        id = "choose_approach"
        name = "Choose Approach"
        description = "Select an approach to handle the challenge."
        inputDisplayNameFn = { input ->
            Stat.valueOf(input["target"]!!).name
        }
        onSelectFn = { input ->
            val regionId = Utils.getRegion(input)
            val regionComponent = RegionComponent.get(
                input.gameState.world,
                regionId
            )
            val approach: Stat = Stat.valueOf(input["target"]!!)
            val challengeEntityId = UUID.fromString(input["challenge"])
            if (regionComponent != null) {
                regionComponent.removeChallenge(challengeEntityId)
                input.gameState.world.delete(challengeEntityId)

                val stats: Attributes = StatsComponent.get(input.gameState.world, input.entityId)
                val realmAttention = input.gameState.getRealmsAttention()

                // Reward: 1 renown per base stat part in the chosen approach, limited by realm attention
                val baseToRenown = mapOf(
                    Stat.BODY to Stat.VALOR,
                    Stat.MIND to Stat.INSIGHT,
                    Stat.MAGIC to Stat.ARCANUM,
                    Stat.CHARISMA to Stat.INFLUENCE
                )
                val partCounts = mutableMapOf<Stat, Int>()
                approach.getParts().forEach { (baseStat, count) ->
                    partCounts[baseStat] = count
                }
                for ((baseStat, count) in partCounts) {
                    val renownStat = baseToRenown[baseStat] ?: continue
                    val currentRealmAttention = realmAttention.get(renownStat)
                    val actualReward = minOf(count, currentRealmAttention)
                    if (actualReward > 0) {
                        stats.add(renownStat, actualReward)
                        realmAttention.put(renownStat, currentRealmAttention - actualReward)
                    }
                }
            }
            EndTurnResult()
        }
    }.register()

    // Face Challenge
    targetAction<Stat> {
        id = "face_challenge"
        name = "Face Challenge"
        description = "Face down a challenge to prove your legend to the realm."
        inputDisplayNameFn = { input ->
            NamedComponent.get(GameState.global().world, UUID.fromString(input["target"]), "---")
        }
        getTargets = { input ->
            input["challenge"] = input["target"]!!
            val challengeEntityId = UUID.fromString(input["target"])
            val challengeComponent: ChallengeComponent =
                ChallengeComponent.get(input.gameState.world, challengeEntityId)
            val challenge: Challenge = challengeComponent.challenge()
            challenge.approaches.toList()
        }
        targetAction = "choose_approach"
    }.register()

    // Face Challenges
    targetAction<UUID> {
        id = "face_challenges"
        name = "Face Challenges"
        description = "See what challenges you can face"
        getTargets = { input ->
            val regionId = Utils.getRegion(input)
            RegionComponent.get(input.gameState.world, regionId).challenges
        }
        targetAction = "face_challenge"
    }.register()
} 