package heroes.journey.entities.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.utils.art.ResourceManager;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Builder
public class Cost {

    @Builder.Default
    private int stamina = 0, mana = 0, health = 0, gold = 0;
    @Builder.Default
    protected BiConsumer<GameState, UUID> onUse = (gs, e) -> {
    };
    @Builder.Default
    protected BiFunction<GameState, UUID, ShowAction> requirementsMet = (gs, e) -> ShowAction.YES;
    @Builder.Default
    protected BiFunction<GameState, UUID, Double> multiplier = (gs, e) -> 1.0;

    public void onUse(GameState gameState, UUID userId) {
        if (userId == null) {
            return;
        }
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), userId);

        double mult = multiplier.apply(gameState, userId);

        Utils.adjustStamina(gameState, userId, (int) -(stamina * mult));
        Utils.adjustMana(gameState, userId, (int) -(mana * mult));
        Utils.adjustHealth(gameState, userId, (int) -(health * mult));

        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), userId);
        inventoryComponent.adjustGold((int) -(gold * mult));

        onUse.accept(gameState, userId);
    }

    public ShowAction requirementsMet(GameState gameState, UUID userId) {
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

    private Table table;
    private Label staminaCost;
    private Label manaCost;
    private Label healthCost;
    private Label goldCost;

    public void initDisplay() {
        staminaCost = new Label("", ResourceManager.get().skin);
        staminaCost.setColor(Color.GREEN);
        manaCost = new Label("", ResourceManager.get().skin);
        manaCost.setColor(Color.BLUE);
        healthCost = new Label("", ResourceManager.get().skin);
        healthCost.setColor(Color.RED);
        goldCost = new Label("", ResourceManager.get().skin);
        goldCost.setColor(Color.GOLD);
        table = new Table();
        table.defaults().left();
    }

    public Table getDisplay() {
        if (staminaCost == null) {
            initDisplay();
        }
        table.clear();
        List<Label> costLabels = new ArrayList<>();

        double mult = multiplier.apply(GameState.global(), GameState.global().getCurrentEntity());

        if (stamina * mult != 0) {
            staminaCost.setText("S: " + (stamina * mult));
            costLabels.add(staminaCost);
        }
        if (mana * mult != 0) {
            manaCost.setText("M: " + (mana * mult));
            costLabels.add(manaCost);
        }
        if (health * mult != 0) {
            healthCost.setText("H: " + (health * mult));
            costLabels.add(healthCost);
        }
        if (gold * mult != 0) {
            goldCost.setText("G: " + (gold * mult));
            costLabels.add(goldCost);
        }
        for (int i = 0; i < costLabels.size(); i++) {
            table.add(costLabels.get(i)).padLeft(5).padRight(5).expand().fill().left();
            if (i % 2 == 1)
                table.row(); // Move to next row every 2 labels
        }
        if (costLabels.size() % 2 == 1) {
            table.row(); // Add row in case there's 1 leftover and no even pair
        }
        // TODO add item cost

        return table;
    }
}
