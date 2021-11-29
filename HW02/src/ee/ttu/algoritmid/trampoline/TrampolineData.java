package ee.ttu.algoritmid.trampoline;

public class TrampolineData {

    // Poolik.
    private Trampoline trampoline;
    private int x;
    private int y;
    private int jumpForceEast;
    private int jumpForceSouth;
    private int fine;
    private Trampoline[] neighbours;
    private int finalJumpForce;

    // Poolik.
    public TrampolineData(Trampoline trampoline, int x, int y, Trampoline[][] map) {
        this.trampoline = trampoline;
        this.x = x;
        this.y = y;
        // Pole vaja.
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

    // Poolik.
    public int getJumpForceEast() {
        // If lause
        return jumpForceEast;
    }

    // Poolik.
    public int getJumpForceSouth() {
        // If lause
        return jumpForceSouth;
    }

    public int getFine() {
        return fine;
    }

    // Poolik. Naabrid listi.
    public Trampoline[] findNeighbours(Trampoline[][] map) {
        Trampoline[] neighbours = new Trampoline[6];
        // Find east neighbours.
        Trampoline eastNeighbour = findTrampolineNeighbour(map, "east", 0);
        // Find neighbours for +- version.
        Trampoline eastNeighbourPlus1 = findTrampolineNeighbour(map, "east", 1);
        Trampoline eastNeighbourMinus1 = findTrampolineNeighbour(map, "east", -1);
        // Find south neighbours.
        Trampoline southNeighbour = findTrampolineNeighbour(map, "south", 0);
        Trampoline southNeighbourPlus1 = findTrampolineNeighbour(map, "south", 1);
        Trampoline southNeighbourMinus1 = findTrampolineNeighbour(map, "south", -1);
        neighbours[0] = eastNeighbour;
        neighbours[1] = southNeighbour;
        neighbours[2] = eastNeighbourPlus1;
        neighbours[3] = southNeighbourPlus1;
        neighbours[4] = eastNeighbourMinus1;
        neighbours[5] = southNeighbourMinus1;
        return neighbours;
    }

    // Poolik.
    public Trampoline findTrampolineNeighbour(Trampoline[][] map, String quarter, int extraForce) {
        int mapLength = map.length - 1;
        int mapWidth = map[0].length - 1;
        int jumpForce;
        if (quarter.equals("east")) {
            // Pooleli. jumpForce võta muutujast.
            jumpForce = fixTrampolineJumpForceEast(map);
            int neighbourX = x + jumpForce + extraForce;
            if (neighbourX <= mapWidth && neighbourX >= 0) {  // Find neighbour from east.
                return map[y][neighbourX];
            }
        } else if (quarter.equals("south")) {
            // Pooleli. jumpForce muutujast.
            jumpForce = fixTrampolineJumpForceSouth(map);
            int neighbourY = y + jumpForce + extraForce;
            if (neighbourY <= mapLength && neighbourY >= 0) {  // Find neighbour from south.
                return map[neighbourY][x];
            }
        }
        return null;
    }

    // Poolik. Pole vaja?
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

    // Valmis.
    public static int calculateTrampolineFine(Trampoline trampoline) {
        Trampoline.Type type = trampoline.getType();
        if (type.equals(Trampoline.Type.WITH_FINE)) {
            return trampoline.getJumpForce();
        }
        return 0;
    }

    // Poolik. midagi peaks tegema for-loopiga.
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

    // Poolik. Midagi peaks tegema for-loopiga.
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

    // Valmis.
    private boolean isWall(Trampoline[][] map, Integer x, Integer y) {
        return map[y][x].getType().equals(Trampoline.Type.WALL);
    }
}
