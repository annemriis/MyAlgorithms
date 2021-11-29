package ee.ttu.algoritmid.trampoline;

import java.util.HashMap;
import java.util.Map;

public class TrampolineData {

    // Poolik.
    private Trampoline trampoline;
    private int x;
    private int y;
    private int jumpForceEast = Integer.MIN_VALUE;
    private int jumpForceSouth = Integer.MIN_VALUE;
    private int fine = Integer.MAX_VALUE;
    private Map<Trampoline, Integer[]> neighbours;

    // Poolik.
    public TrampolineData(Trampoline trampoline, int x, int y) {
        this.trampoline = trampoline;
        this.x = x;
        this.y = y;
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

    public Map<Trampoline, Integer[]> getNeighbours(Trampoline[][] map) {
        if (neighbours == null) {
            findNeighbours(map);
        }
        return neighbours;
    }

    // Valmis? Poolik.
    public void findNeighbours(Trampoline[][] map) {
        neighbours = new HashMap<>();
        int jumpForceEast = getJumpForceEast(map);
        int jumpForceSouth = getJumpForceSouth(map);
        // Find east neighbours.
        Trampoline eastNeighbour = findTrampolineNeighbour(map, "east", 0);
        Integer[] eastNeighbourCoordinates = new Integer[] {x + jumpForceEast, y};
        // Find neighbours for +- version.
        Trampoline eastNeighbourPlus1 = findTrampolineNeighbour(map, "east", 1);
        Integer[] eastNeighbourPlus1Coordinates = new Integer[] {x + jumpForceEast + 1, y};
        Trampoline eastNeighbourMinus1 = findTrampolineNeighbour(map, "east", -1);
        Integer[] eastNeighbourMinus1Coordinates = new Integer[] {x + jumpForceEast - 1, y};

        // Find south neighbours.
        Trampoline southNeighbour = findTrampolineNeighbour(map, "south", 0);
        Integer[] southNeighbourCoordinates = new Integer[] {x, y + jumpForceSouth};
        Trampoline southNeighbourPlus1 = findTrampolineNeighbour(map, "south", 1);
        Integer[] southNeighbourPlus1Coordinates = new Integer[] {x, y + jumpForceSouth + 1};
        Trampoline southNeighbourMinus1 = findTrampolineNeighbour(map, "south", -1);
        Integer[] southNeighbourMinus1Coordinates = new Integer[] {x, y + jumpForceSouth - 1};

        // Add neighbours to the neighbours map.
        neighbours.put(eastNeighbour, eastNeighbourCoordinates);
        neighbours.put(eastNeighbourPlus1, eastNeighbourPlus1Coordinates);
        neighbours.put(eastNeighbourMinus1, eastNeighbourMinus1Coordinates);
        neighbours.put(southNeighbour, southNeighbourCoordinates);
        neighbours.put(southNeighbourPlus1, southNeighbourPlus1Coordinates);
        neighbours.put(southNeighbourMinus1, southNeighbourMinus1Coordinates);
    }

    // Täpsustused.
    public Trampoline findTrampolineNeighbour(Trampoline[][] map, String quarter, int extraForce) {

        int mapLength = map.length - 1;
        int mapWidth = map[0].length - 1;

        if (quarter.equals("east")) {
            int jumpForce = getJumpForceEast(map);
            int neighbourX = x + jumpForce + extraForce;
            if (neighbourX <= mapWidth && neighbourX >= 0) {  // Find neighbour from the east.
                return map[y][neighbourX];
            }
        } else if (quarter.equals("south")) {
            int jumpForce = getJumpForceSouth(map);
            int neighbourY = y + jumpForce + extraForce;
            if (neighbourY <= mapLength && neighbourY >= 0) {  // Find neighbour from the south.
                return map[neighbourY][x];
            }
        }
        return null;
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
