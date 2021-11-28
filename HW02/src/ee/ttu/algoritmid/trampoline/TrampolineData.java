package ee.ttu.algoritmid.trampoline;

public class TrampolineData {

    private Trampoline trampoline;
    private int x;
    private int y;
    private int jumpForceEast;
    private int jumpForceSouth;
    private int fine;
    private Trampoline[] neighbours;

    public TrampolineData(Trampoline trampoline, int x, int y, Trampoline[][] map) {
        this.trampoline = trampoline;
        this.x = x;
        this.y = y;
        this.jumpForceEast = fixTrampolineJumpForceEast(map);
        this.jumpForceSouth = fixTrampolineJumpForceSouth(map);
        this.fine = calculateTrampolineFine(trampoline);
    }

    public Trampoline getTrampoline() {
        return trampoline;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getCoordinates() {
        return new int[] {x, y};
    }

    public int getJumpForceEast() {
        return jumpForceEast;
    }

    public int getJumpForceSouth() {
        return jumpForceSouth;
    }

    public int getFine() {
        return fine;
    }

    public Trampoline[] findNeighbours(Trampoline[][] map) {
        Trampoline[] neighbours = new Trampoline[2];
        Trampoline eastNeighbour = findTrampolineNeighbour(map, "east");
        Trampoline southNeighbour = findTrampolineNeighbour(map, "south");
        neighbours[0] = eastNeighbour;
        neighbours[1] = southNeighbour;
        return fixNeighboursArray(neighbours);
    }

    public Trampoline findTrampolineNeighbour(Trampoline[][] map, String quarter) {
        int mapLength = map.length - 1;
        int mapWidth = map[0].length - 1;
        int jumpForce;
        if (quarter.equals("east")) {
            // Pooleli
            jumpForce = fixTrampolineJumpForceEast(map);
            int neighbourX = x + jumpForce;
            if (neighbourX <= mapWidth) {  // Find neighbour from east.
                return map[y][neighbourX];
            }
        } else if (quarter.equals("south")) {
            // Pooleli
            jumpForce = fixTrampolineJumpForceSouth(map);
            int neighbourY = y + jumpForce;
            if (neighbourY <= mapLength) {  // Find neighbour from south.
                return map[neighbourY][x];
            }
        }
        return null;
    }

    private Trampoline[] fixNeighboursArray(Trampoline[] neighbours) {
        boolean hasEastNeighbour = (neighbours[0] != null && !neighbours[0].getType().equals(Trampoline.Type.WALL));
        boolean hasSouthNeighbour = (neighbours[1] != null && !neighbours[1].getType().equals(Trampoline.Type.WALL));
        if (!hasEastNeighbour && !hasSouthNeighbour) {
            return new Trampoline[] {};
        } else if (!hasEastNeighbour) {
            return new Trampoline[] {neighbours[1]};
        } else if (!hasSouthNeighbour) {
            return new Trampoline[] {neighbours[0]};
        }
        return neighbours;
    }

    public static int calculateTrampolineFine(Trampoline trampoline) {
        Trampoline.Type type = trampoline.getType();
        if (type.equals(Trampoline.Type.WITH_FINE)) {
            return -trampoline.getJumpForce();
        }
        return 0;
    }

    private int fixTrampolineJumpForceEast(Trampoline[][] map) {
        int mapWidth = map[0].length;
        int jumpForce = trampoline.getJumpForce();
        for (int i = jumpForce; i > 0; i--) {
            int neighbourX = x + i;
            if (neighbourX < mapWidth) {
                if (isWall(map, neighbourX, y)) {
                    return i - 1;
                }
            }
        }
        return jumpForce;
    }

    private int fixTrampolineJumpForceSouth(Trampoline[][] map) {
        int mapLength = map.length;
        int jumpForce = trampoline.getJumpForce();
        for (int i = jumpForce; i > 0; i--) {
            int neighbourY = y + i;
            if (neighbourY < mapLength) {
                if (isWall(map, x, neighbourY)) {
                    return i - y;
                }
            }
        }
        return jumpForce;
    }

    private boolean isWall(Trampoline[][] map, Integer x, Integer y) {
        return map[y][x].getType().equals(Trampoline.Type.WALL);
    }
}
