package heroes.journey.utils.worldgen;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.utils.Random;

public class BSPDungeon {

    static final int WIDTH = 40;
    static final int HEIGHT = 40;
    static final int MIN_ROOM_SIZE = 3;
    static final int MAX_ROOM_SIZE = 8;
    static final int MAX_DEPTH = 4;

    static class Rect {
        int x, y, w, h;
        Rect left, right;
        Rect room;

        Rect(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        boolean split() {
            if (left != null || right != null)
                return false;

            boolean splitHorizontally = w < h;

            int max = (splitHorizontally ? h : w) - MIN_ROOM_SIZE;
            if (max <= MIN_ROOM_SIZE)
                return false;

            int split = MIN_ROOM_SIZE + Random.get().nextInt(max - MIN_ROOM_SIZE);
            if (splitHorizontally) {
                left = new Rect(x, y, w, split);
                right = new Rect(x, y + split, w, h - split);
            } else {
                left = new Rect(x, y, split, h);
                right = new Rect(x + split, y, w - split, h);
            }
            return true;
        }

        Rect getRoom() {
            if (room != null)
                return room;
            if (left != null && right != null) {
                Rect lRoom = left.getRoom();
                Rect rRoom = right.getRoom();
                return Random.get().nextBoolean() ? lRoom : rRoom;
            }
            return room;
        }
    }

    public static int[][] generateDungeon() {
        int[][] map = new int[WIDTH][HEIGHT];
        Rect root = new Rect(0, 0, WIDTH, HEIGHT);
        List<Rect> rooms = new ArrayList<>();
        List<Rect> leaves = new ArrayList<>();
        leaves.add(root);

        for (int i = 0; i < MAX_DEPTH; i++) {
            List<Rect> newLeaves = new ArrayList<>();
            for (Rect leaf : leaves) {
                if (leaf.split()) {
                    newLeaves.add(leaf.left);
                    newLeaves.add(leaf.right);
                }
            }
            leaves.addAll(newLeaves);
        }

        for (Rect leaf : leaves) {
            if (leaf.left == null && leaf.right == null) {
                int rw = MIN_ROOM_SIZE +
                    Random.get().nextInt(Math.max(1, Math.min(leaf.w, MAX_ROOM_SIZE) - MIN_ROOM_SIZE + 1));
                int rh = MIN_ROOM_SIZE +
                    Random.get().nextInt(Math.max(1, Math.min(leaf.h, MAX_ROOM_SIZE) - MIN_ROOM_SIZE + 1));
                int rx = leaf.x + Random.get().nextInt(leaf.w - rw + 1);
                int ry = leaf.y + Random.get().nextInt(leaf.h - rh + 1);
                leaf.room = new Rect(rx, ry, rw, rh);
                rooms.add(leaf.room);
                fillRoom(map, leaf.room);
            }
        }

        connectRooms(map, root);

        return map;
    }

    static void fillRoom(int[][] map, Rect room) {
        for (int x = room.x; x < room.x + room.w; x++) {
            for (int y = room.y; y < room.y + room.h; y++) {
                if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {
                    map[x][y] = 1;
                }
            }
        }
    }

    static void connectRooms(int[][] map, Rect node) {
        if (node.left != null && node.right != null) {
            Rect a = node.left.getRoom();
            Rect b = node.right.getRoom();
            if (a != null && b != null) {
                int ax = a.x + a.w / 2;
                int ay = a.y + a.h / 2;
                int bx = b.x + b.w / 2;
                int by = b.y + b.h / 2;

                if (Random.get().nextBoolean()) {
                    carveHor(map, ax, bx, ay);
                    carveVer(map, ay, by, bx);
                } else {
                    carveVer(map, ay, by, ax);
                    carveHor(map, ax, bx, by);
                }
            }

            connectRooms(map, node.left);
            connectRooms(map, node.right);
        }
    }

    static void carveHor(int[][] map, int x1, int x2, int y) {
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT)
                map[x][y] = 1;
        }
    }

    static void carveVer(int[][] map, int y1, int y2, int x) {
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT)
                map[x][y] = 1;
        }
    }

    // Optional: for debugging in console
    public static String printMap(int[][] map) {
        String mapStr = "";
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                mapStr += map[x][y] == 1 ? "#" : ".";
            }
            mapStr += "\n";
        }
        return mapStr;
    }
}
