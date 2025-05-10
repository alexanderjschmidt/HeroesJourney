package heroes.journey.components.utils;

import heroes.journey.tilemap.Fog;
import lombok.Getter;

import java.util.Arrays;

public class FogMap {

    public int length;
    @Getter
    private Fog[][] fog;

    public FogMap(Fog[][] fog) {
        length = fog.length;
        this.fog = fog;
        for (Fog[] fogs : getFog()) {
            Arrays.fill(fogs, Fog.DENSE);
        }
    }

    public void copy(FogMap from) {
        this.fog = new Fog[from.fog.length][from.fog.length];
        for (int x = 0; x < from.fog.length / 2; x++) {
            System.arraycopy(from.fog[x], 0, fog[x], 0, from.fog[x].length);
        }
    }

    public void clear() {
        this.fog = new Fog[length][length];
        for (Fog[] fogs : getFog()) {
            Arrays.fill(fogs, Fog.DENSE);
        }
    }
}
