package heroes.journey.utils.worldgen.dungeon;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.inBounds;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.utils.Random;

public class DungeonGenerator {
    private final int roomWidth;
    private final int roomHeight;
    private final int width;
    private final int height;
    private final int minimumRoomSize;
    private final int maximumRoomSize;
    private int[][] dungeon;
    private int[][] grid;
    private final Random random = Random.get();
    private final List<Room> rooms = new ArrayList<>();

    public DungeonGenerator(int roomWidth, int roomHeight, int minimumRoomSize, int maximumRoomSize) {
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        this.minimumRoomSize = minimumRoomSize;
        this.maximumRoomSize = maximumRoomSize;
        this.width = roomWidth * (maximumRoomSize + 2);
        this.height = roomHeight * (maximumRoomSize + 2);
    }

    public int[][] generateDungeon(int dungeonLength) {
        while (true) {
            this.grid = new int[roomWidth][roomHeight];
            this.dungeon = new int[width][height];
            rooms.clear();
            try {
                createMainPath(dungeonLength);
                // TODO make it so bonus rooms can be more complex: multiple rooms chained, loops back into main path
                // TODO Add vanishing tunnels to rooms, directions where if you go that way to get garaunteed to be lost
                createBonusRooms();
                break;
            } catch (NoDirectionsException e) {
            }
        }
        return dungeon;
    }

    private void placeRoom(int x, int y, int w, int h) {
        for (int i = y; i < y + h; i++) {
            for (int j = x; j < x + w; j++) {
                if (inBounds(x, y, width, height)) {
                    dungeon[i][j] = 1;
                }
            }
        }
    }

    private void carveCorridor(int x1, int y1, int x2, int y2) {
        if (random.nextBoolean()) {
            // Horizontal then vertical
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                if (y1 >= 0 && y1 < height && x >= 0 && x < width) {
                    dungeon[y1][x] = 1;
                }
            }
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                if (y >= 0 && y < height && x2 >= 0 && x2 < width) {
                    dungeon[y][x2] = 1;
                }
            }
        } else {
            // Vertical then horizontal
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                if (y >= 0 && y < height && x1 >= 0 && x1 < width) {
                    dungeon[y][x1] = 1;
                }
            }
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                if (y2 >= 0 && y2 < height && x >= 0 && x < width) {
                    dungeon[y2][x] = 1;
                }
            }
        }
    }

    private void createMainPath(int dungeonLength) throws NoDirectionsException {
        int currentX = roomWidth / 2;
        int currentY = 0;
        Room previousRoom = null;

        for (int i = 0; i < dungeonLength; i++) {
            previousRoom = addAdjacentRoom(currentX, currentY, previousRoom, 1, true);
            if (previousRoom != null) {
                currentX = gridX(previousRoom);
                currentY = gridY(previousRoom);
            }
        }
    }

    private Room addAdjacentRoom(
        int currentX,
        int currentY,
        Room previousRoom,
        float chance,
        boolean mainPath) throws NoDirectionsException {
        if (random.nextDouble() >= chance) {
            return null;
        }
        int dir = random.nextInt(4);
        int offsetX = 0, offsetY = 0;
        switch (dir) {
            case 0 -> offsetY--;             // Up
            case 1 -> offsetY++;         // Down
            case 2 -> offsetX--;             // Left
            case 3 -> offsetX++;         // Right
        }

        int consideredDir = 0;
        while (!(currentX + offsetX >= 0 && currentX + offsetX < roomWidth && currentY + offsetY >= 0 &&
            currentY + offsetY < roomHeight && grid[currentX + offsetX][currentY + offsetY] == 0) &&
            consideredDir < 4) {
            offsetX = 0;
            offsetY = 0;
            switch (dir) {
                case 0 -> offsetY--;             // Up
                case 1 -> offsetY++;         // Down
                case 2 -> offsetX--;             // Left
                case 3 -> offsetX++;         // Right
            }
            dir = (dir + 1) % 4;
            consideredDir++;
        }
        if (consideredDir == 4 && mainPath) {
            System.out.println("No Directions worked main");
            throw new NoDirectionsException();
        } else if (consideredDir == 4) {
            System.out.println("No Directions worked side");
            return null;
        }
        currentX += offsetX;
        currentY += offsetY;
        int w = random.nextInt(minimumRoomSize, maximumRoomSize);
        int h = random.nextInt(minimumRoomSize, maximumRoomSize);
        int x = (currentX * (maximumRoomSize + 2)) + (maximumRoomSize / 2) - (w / 2) + 1;
        int y = (currentY * (maximumRoomSize + 2)) + (maximumRoomSize / 2) - (h / 2) + 1;

        grid[currentX][currentY] = 1;
        placeRoom(x, y, w, h);
        Room room = new Room(x, y, w, h);
        if (mainPath) {
            rooms.add(room);
        }

        if (previousRoom != null) {
            carveCorridor(previousRoom.centerX(), previousRoom.centerY(), room.centerX(), room.centerY());
        }

        return room;
    }

    private void createBonusRooms() throws NoDirectionsException {
        for (int i = 1; i < rooms.size() - 1; i++) {
            Room room = rooms.get(i);
            addAdjacentRoom(gridX(room), gridY(room), room, 0.6f, false);
        }
    }

    public void printDungeon() {
        for (int[] row : dungeon) {
            for (int cell : row) {
                System.out.print(cell == 1 ? "." : "#");
            }
            System.out.println();
        }
    }

    private int gridX(Room room) {
        return room.centerX() / (maximumRoomSize + 2);
    }

    private int gridY(Room room) {
        return room.centerY() / (maximumRoomSize + 2);
    }

    private static class Room {
        int x, y, w, h;

        Room(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        int centerX() {
            return x + w / 2;
        }

        int centerY() {
            return y + h / 2;
        }
    }

    private static class NoDirectionsException extends Exception {

    }
}
