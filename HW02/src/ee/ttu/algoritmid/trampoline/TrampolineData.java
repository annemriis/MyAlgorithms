package ee.ttu.algoritmid.trampoline;

public class TrampolineData {

    // Poolik.
    private Trampoline trampoline;
    private int x;
    private int y;
    private int jumpForceEast = Integer.MIN_VALUE;
    private int jumpForceSouth = Integer.MIN_VALUE;
    private int fine = Integer.MAX_VALUE;
    private TrampolineData[] neighbours;

    // Poolik.
    public TrampolineData(Trampoline trampoline) {
        this.trampoline = trampoline;
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

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getCoordinates() {
        return new int[] {x, y};
    }

    public int getJumpForceEast(Trampoline[][] map) {
        if (jumpForceEast == Integer.MIN_VALUE) {  // Calculate jump force if it has not been calculated.
            jumpForceEast = fixTrampolineJumpForceEast(map);
        }
        return jumpForceEast;
    }

    public int getJumpForceSouth(Trampoline[][] map) {
        if (jumpForceSouth == Integer.MIN_VALUE) {  // Calculate jump force if it has not been calculated.
            jumpForceSouth = fixTrampolineJumpForceSouth(map);
        }
        return jumpForceSouth;
    }

    public int getFine() {
        if (fine == Integer.MAX_VALUE) {
            fine = calculateTrampolineFine(trampoline);
        }
        return fine;
    }

    public TrampolineData[] getNeighbours(Trampoline[][] map) {
        if (neighbours == null) {
            findNeighbours(map);
        }
        return neighbours;
    }

    // Valmis? Poolik.
    public void findNeighbours(Trampoline[][] map) {
        neighbours = new TrampolineData[6];
        // Find east neighbours.
        TrampolineData eastNeighbour = findTrampolineNeighbour(map, "east", 0);
        // Find neighbours for +- version.
        TrampolineData eastNeighbourPlus1 = findTrampolineNeighbour(map, "east", 1);
        TrampolineData eastNeighbourMinus1 = findTrampolineNeighbour(map, "east", -1);

        // Find south neighbours.
        TrampolineData southNeighbour = findTrampolineNeighbour(map, "south", 0);
        TrampolineData southNeighbourPlus1 = findTrampolineNeighbour(map, "south", 1);
        TrampolineData southNeighbourMinus1 = findTrampolineNeighbour(map, "south", -1);

        // Add neighbours to the neighbours array;
        neighbours[0] = eastNeighbour;
        neighbours[1] = southNeighbour;
        neighbours[2] = eastNeighbourPlus1;
        neighbours[3] = southNeighbourPlus1;
        neighbours[4] = eastNeighbourMinus1;
        neighbours[5] = southNeighbourMinus1;
    }

    // Täpsustused.
    public TrampolineData findTrampolineNeighbour(Trampoline[][] map, String quarter, int extraForce) {
        int mapLength = map.length - 1;
        int mapWidth = map[0].length - 1;

        if (quarter.equals("east")) {
            int jumpForce = getJumpForceEast(map);
            int neighbourX = x + jumpForce + extraForce;
            if (neighbourX <= mapWidth && neighbourX >= 0) {  // Find neighbour from the east.
                TrampolineData neighbourData = new TrampolineData(map[y][neighbourX]);
                neighbourData.setCoordinates(neighbourX, y);
                return neighbourData;
            }
        } else if (quarter.equals("south")) {
            int jumpForce = getJumpForceSouth(map);
            int neighbourY = y + jumpForce + extraForce;
            if (neighbourY <= mapLength && neighbourY >= 0) {  // Find neighbour from the south.
                TrampolineData neighbourData = new TrampolineData(map[neighbourY][x]);
                neighbourData.setCoordinates(x, neighbourY);
                return neighbourData;
            }
        }
        return null;
    }

    // Valmis.
    public int calculateTrampolineFine(Trampoline trampoline) {
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
