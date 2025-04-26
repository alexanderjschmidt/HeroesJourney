package heroes.journey.entities.actions;

import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.StatsComponent;
import lombok.Builder;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Builder
public class Cost {

    @Builder.Default
    private int stamina = 0, mana = 0, health = 0, gold = 0;
    @Builder.Default
    protected BiConsumer<GameState, Integer> onUse = (gs, e) -> {
    };
    @Builder.Default
    protected BiFunction<GameState, Integer, ShowAction> requirementsMet = (gs, e) -> ShowAction.YES;

    public void onUse(GameState gameState, Integer userId) {
        if (userId == null) {
            return;
        }
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), userId);

        statsComponent.adjustStamina(-stamina);
        statsComponent.adjustMana(-mana);
        statsComponent.adjustHealth(-health);

        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), userId);
        inventoryComponent.adjustGold(-gold);

        onUse.accept(gameState, userId);
    }

    public ShowAction requirementsMet(GameState gameState, Integer userId) {
        if (userId == null) {
            return ShowAction.YES;
        }
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), userId);

        ShowAction enoughStamina = statsComponent.getStamina() > this.health ? ShowAction.YES : ShowAction.GRAYED;
        ShowAction enoughMana = statsComponent.getMana() >= this.mana ? ShowAction.YES : ShowAction.GRAYED;
        ShowAction enoughHealth = statsComponent.getHealth() >= this.health ? ShowAction.YES : ShowAction.GRAYED;

        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), userId);
        ShowAction enoughGold = inventoryComponent.getGold() >= this.gold ? ShowAction.YES : ShowAction.GRAYED;

        //System.out.println(enoughStamina);
        //System.out.println(enoughMana);
        //System.out.println(enoughHealth);
        //System.out.println(enoughGold);

        return requirementsMet.apply(gameState, userId).and(enoughStamina).and(enoughMana).and(enoughHealth).and(enoughGold);
    }

}
