package heroes.journey.ui.windows;

import static heroes.journey.modlib.Ids.LIGHT_BLUE;
import static heroes.journey.modlib.Ids.PURPLE;
import static heroes.journey.modlib.Ids.RED;
import static heroes.journey.modlib.Ids.YELLOW;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import heroes.journey.GameState;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.ui.ResourceBar;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

public class RealmAttentionUI extends UI {

    private final Label title;
    private final ResourceBar valor, insight, arcanum, influence;

    public RealmAttentionUI() {
        super();
        this.title = new Label("Realm's Attention", ResourceManager.get().skin);
        this.title.setWrap(true);
        this.mainTable.add(title).expandX().row();

        this.valor = new ResourceBar(RED, null);
        this.insight = new ResourceBar(LIGHT_BLUE, null);
        this.arcanum = new ResourceBar(PURPLE, null);
        this.influence = new ResourceBar(YELLOW, null);

        this.mainTable.add(valor).expandX().row();
        this.mainTable.add(insight).expandX().row();
        this.mainTable.add(arcanum).expandX().row();
        this.mainTable.add(influence).expandX().row();

        pack();
    }

    @Override
    public void update() {
        Attributes realmAttention = GameState.global().getRealmsAttention();

        if (realmAttention != null) {
            int realmAttentionBase = GameState.global().getMapData().getRealmAttentionBase();
            valor.update(realmAttention, "valor", realmAttentionBase);
            insight.update(realmAttention, "insight", realmAttentionBase);
            arcanum.update(realmAttention, "arcanum", realmAttentionBase);
            influence.update(realmAttention, "influence", realmAttentionBase);
        }
    }
}
