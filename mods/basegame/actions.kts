import heroes.journey.mods.gameMod

gameMod("Base Game Actions") {
/*
    CooldownAction(
        "workout", "Work out", "Lift weights, gain body", false,
        Cost(0, 0, 0, 0), 1, false
    ) {
        override fun internalOnSelect(input: ActionInput): ActionResult {
            return StatsUtils.adjustBody(input.gameState, input.entityId, 1)
        }
    }.register()

    CooldownAction(
        "study", "Study", "Expand your mind, increasing your potential", false,
        Cost(0, 0, 0, 0), 2, false
    ) {
        override fun internalOnSelect(input: ActionInput): ActionResult {
            return StatsUtils.adjustMind(input.gameState, input.entityId, 1)
        }
    }.register()
    TargetAction<Quest>(
        "quest_board", "Quest Board",
        "See what the people need help with", Cost(0, 0, 0, 0)
    ) {
        override fun getTargets(input: ActionInput): List<Quest> {
            val town = Utils.getLocation(input)
            return QuestsComponent.get(input.gameState.world, town).quests
        }

        override fun getTargetDisplayName(input: TargetInput<Quest>): String {
            return input.input.getName()
        }

        override fun onSelectTarget(input: TargetInput<Quest>): ActionResult {
            val town = Utils.getLocation(input)
            val factionsQuestsComponent = QuestsComponent.get(
                input.gameState.world,
                town
            )

            if (factionsQuestsComponent != null) {
                factionsQuestsComponent.remove(input.input)
                QuestsComponent.get(input.gameState.world, input.entityId)
                    .addQuest(input.input)
            }
            return EndTurnResult()
        }
    }.register()*/
}
