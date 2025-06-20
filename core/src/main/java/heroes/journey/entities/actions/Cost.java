package heroes.journey.entities.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.initializers.base.tags.Stats;
import heroes.journey.initializers.utils.StatsUtils;
import heroes.journey.utils.art.ResourceManager;

public class Cost {
    private final int stamina;
    private final int mana;
    private final int health;
    private final int gold;

    public Cost(int stamina, int mana, int health, int gold) {
        this.stamina = stamina;
        this.mana = mana;
        this.health = health;
        this.gold = gold;
    }

    public Cost() {
        this(0, 0, 0, 0);
    }

    public void onUse(ActionInput input) {
        GameState gameState = input.getGameState();
        UUID userId = input.getEntityId();
        if (userId == null) {
            return;
        }

        StatsUtils.adjustStamina(gameState, userId, -(stamina));
        StatsUtils.adjustMana(gameState, userId, -(mana));
        StatsUtils.adjustHealth(gameState, userId, -(health));

        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), userId);
        inventoryComponent.adjustGold(-(gold));
    }

    public ShowAction requirementsMet(ActionInput input) {
        GameState gameState = input.getGameState();
        UUID userId = input.getEntityId();
        if (userId == null) {
            return ShowAction.YES;
        }
        Attributes statsComponent = StatsComponent.get(gameState.getWorld(), userId);

        assert statsComponent != null;
        ShowAction enoughStamina =
            statsComponent.get(Stats.STAMINA) > this.stamina ? ShowAction.YES : ShowAction.GRAYED;
        ShowAction enoughMana =
            statsComponent.get(Stats.MANA) >= this.mana ? ShowAction.YES : ShowAction.GRAYED;
        ShowAction enoughHealth =
            statsComponent.get(Stats.HEALTH) >= this.health ? ShowAction.YES : ShowAction.GRAYED;

        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), userId);
        ShowAction enoughGold =
            inventoryComponent.getGold() >= this.gold ? ShowAction.YES : ShowAction.GRAYED;

        return enoughStamina.and(enoughMana).and(enoughHealth).and(enoughGold);
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

        if (stamina != 0) {
            staminaCost.setText("S: " + (stamina));
            costLabels.add(staminaCost);
        }
        if (mana != 0) {
            manaCost.setText("M: " + (mana));
            costLabels.add(manaCost);
        }
        if (health != 0) {
            healthCost.setText("H: " + (health));
            costLabels.add(healthCost);
        }
        if (gold != 0) {
            goldCost.setText("G: " + (gold));
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
