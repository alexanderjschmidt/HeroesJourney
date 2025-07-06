package heroes.journey.models;

public class MapData {

    private int seed, mapSize, teamCount;

    private boolean fogOfWar;

    public MapData(int seed, int mapSize, int teamCount, boolean fogOfWar) {
        this.seed = seed;
        this.mapSize = mapSize;
        this.teamCount = teamCount;
        this.fogOfWar = fogOfWar;
    }

    public int getSeed() {
        return seed;
    }

    public int getMapSize() {
        return mapSize;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public int getRealmAttentionBase() {
        return teamCount + 1;
    }

    public boolean isFogOfWar() {
        return fogOfWar;
    }
}
