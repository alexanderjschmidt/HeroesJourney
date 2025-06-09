package heroes.journey.registries;

import java.util.HashMap;

import heroes.journey.tilemap.Region;

public class RegionManager extends HashMap<Integer,Region> {

    private static RegionManager regionManager;

    public static RegionManager get() {
        if (regionManager == null)
            regionManager = new RegionManager();
        return regionManager;
    }

    public static Region getRegion(Integer id) {
        return get().get(id);
    }
}
