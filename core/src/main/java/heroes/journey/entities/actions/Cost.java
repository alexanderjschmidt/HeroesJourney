package heroes.journey.entities.actions;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.StatsComponent;
import lombok.Builder;

@Builder
public class Cost {

    @Builder.Default private int stamina = 0, mana = 0, health = 0, gold = 0;
    @Builder.Default protected BiConsumer<GameState,Integer> onUse = (gs, e) -> {
    };
    @Builder.Default
    protected BiFunction<GameState,Integer,ShowAction> requirementsMet = (gs, e) -> ShowAction.YES;
    @Builder.Default protected BiFunction<GameState,Integer,Double> multiplier = (gs, e) -> 1.0;

    public void onUse(GameState gameState, Integer userId) {
        if (userId == null) {
            return;
        }
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), userId);

        double mult = multiplier.apply(gameState, userId);

        statsComponent.adjustStamina((int)-(stamina * mult));
        statsComponent.adjustMana((int)-(mana * mult));
        statsComponent.adjustHealth((int)-(health * mult));

        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), userId);
        inventoryComponent.adjustGold((int)-(gold * mult));

        onUse.accept(gameState, userId);
    }

    public ShowAction requirementsMet(GameState gameState, Integer userId) {
        if (userId == null) {
            return ShowAction.YES;
        }
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), userId);

        double mult = multiplier.apply(gameState, userId);

        ShowAction enoughStamina =
            statsComponent.getStamina() > this.stamina * mult ? ShowAction.YES : ShowAction.GRAYED;
        ShowAction enoughMana =
            statsComponent.getMana() >= this.mana * mult ? ShowAction.YES : ShowAction.GRAYED;
        ShowAction enoughHealth =
            statsComponent.getHealth() >= this.health * mult ? ShowAction.YES : ShowAction.GRAYED;

        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), userId);
        ShowAction enoughGold =
            inventoryComponent.getGold() >= this.gold * mult ? ShowAction.YES : ShowAction.GRAYED;

        //System.out.println(enoughStamina);
        //System.out.println(enoughMana);
        //System.out.println(enoughHealth);
        //System.out.println(enoughGold);

        return requirementsMet.apply(gameState, userId)
            .and(enoughStamina)
            .and(enoughMana)
            .and(enoughHealth)
            .and(enoughGold);
    }

}
