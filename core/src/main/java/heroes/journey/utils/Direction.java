package heroes.journey.utils;

import com.badlogic.gdx.math.Vector2;
import heroes.journey.entities.Position;
import lombok.Getter;

public enum Direction {
    NODIRECTION("NA", -1, 0), EAST("E", 2, 180), WEST("W", 6, 0), NORTH("N", 0, 270), NORTHEAST("NE", 1,
        225), NORTHWEST("NW", 7, 315), SOUTH("S", 4, 90), SOUTHEAST("SE", 3, 135), SOUTHWEST("SW", 5, 45);

    public static final float SQRT_HALF = 0.70710678118f;

    @Getter
    private final int clockPos;
    @Getter
    private final float angle;
    private final String abbreviation;

    @Getter
    private static final Direction[] directions = {NORTHWEST, NORTH, NORTHEAST, WEST, EAST, SOUTHWEST, SOUTH, SOUTHWEST};

    private Direction(String abbreviation, int clockPos, float angle) {
        this.abbreviation = abbreviation;
        this.clockPos = clockPos;
        this.angle = angle;
    }

    public String toString() {
        return abbreviation;
    }

    public static Direction getDirectionWithClockPos(int clockPos) {
        for (Direction d : Direction.values()) {
            if (((clockPos + 8) % 8) == d.getClockPos()) {
                return d;
            }
        }
        return NODIRECTION;
    }


    public static Vector2 getVector(Direction dir, int dist) {
        Vector2 v = new Vector2();
        if (dir == Direction.NORTH)
            v.set(0, 1);
        else if (dir == Direction.SOUTH)
            v.set(0, -1);
        else if (dir == Direction.EAST)
            v.set(1, 0);
        else if (dir == Direction.WEST)
            v.set(-1, 0);
        else if (dir == Direction.NORTHEAST)
            v.set(SQRT_HALF, SQRT_HALF);
        else if (dir == Direction.NORTHWEST)
            v.set(-SQRT_HALF, SQRT_HALF);
        else if (dir == Direction.SOUTHEAST)
            v.set(SQRT_HALF, -SQRT_HALF);
        else if (dir == Direction.SOUTHWEST)
            v.set(-SQRT_HALF, -SQRT_HALF);
        return v.scl(dist);
    }

    public Vector2 getDirVector() {
        Vector2 v = new Vector2();
        if (this == Direction.NORTH)
            v.set(0, 1);
        else if (this == Direction.SOUTH)
            v.set(0, -1);
        else if (this == Direction.EAST)
            v.set(1, 0);
        else if (this == Direction.WEST)
            v.set(-1, 0);
        else if (this == Direction.NORTHEAST)
            v.set(1, 1);
        else if (this == Direction.NORTHWEST)
            v.set(-1, 1);
        else if (this == Direction.SOUTHEAST)
            v.set(1, -1);
        else if (this == Direction.SOUTHWEST)
            v.set(-1, -1);
        return v;
    }

    public Direction inverse() {
        if (this == Direction.NORTH)
            return Direction.SOUTH;
        else if (this == Direction.SOUTH)
            return Direction.NORTH;
        else if (this == Direction.EAST)
            return Direction.WEST;
        else if (this == Direction.WEST)
            return Direction.EAST;
        else if (this == Direction.NORTHEAST)
            return Direction.SOUTHWEST;
        else if (this == Direction.NORTHWEST)
            return Direction.SOUTHEAST;
        else if (this == Direction.SOUTHEAST)
            return Direction.NORTHWEST;
        else if (this == Direction.SOUTHWEST)
            return Direction.NORTHEAST;
        return Direction.NODIRECTION;
    }

    public boolean isCardinal() {
        return abbreviation.length() == 1;
    }

    public static Direction approximateDirection(Position from, Position to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        double angle = Math.atan2(dy, dx); // Note: Y axis is flipped (top-down map)
        double degrees = Math.toDegrees(angle);
        degrees = (degrees + 360) % 360; // Normalize to [0, 360)

        if (degrees >= 337.5 || degrees < 22.5)
            return Direction.EAST;
        if (degrees >= 22.5 && degrees < 67.5)
            return Direction.NORTHEAST;
        if (degrees >= 67.5 && degrees < 112.5)
            return Direction.NORTH;
        if (degrees >= 112.5 && degrees < 157.5)
            return Direction.NORTHWEST;
        if (degrees >= 157.5 && degrees < 202.5)
            return Direction.WEST;
        if (degrees >= 202.5 && degrees < 247.5)
            return Direction.SOUTHWEST;
        if (degrees >= 247.5 && degrees < 292.5)
            return Direction.SOUTH;
        if (degrees >= 292.5 && degrees < 337.5)
            return Direction.SOUTHEAST;

        return Direction.NORTH; // fallback
    }
}
