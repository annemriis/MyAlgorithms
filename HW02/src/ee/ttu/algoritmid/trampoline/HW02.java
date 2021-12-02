package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class HW02 implements TrampolineCenter {

    @Override
    public Result play(Trampoline[][] map) {
        Trampoline NWTrampoline = map[0][0];
        int[] currentCoordinates;
        int[] finishCoordinates = findFinishCoordinates(map);
        HashMap<TrampolineData, TrampolineData> cameFrom = new HashMap<>();
        HashMap<Trampoline, Integer> costSoFar = new HashMap<>();

        PriorityQueue<TrampolineData> frontier = new PriorityQueue<>((o1, o2) -> {

            // Check if trampoline is with fine.
            if (o1.getTrampoline().getType().equals(Trampoline.Type.WITH_FINE)) {
                return 1;
            } else if (o2.getTrampoline().getType().equals(Trampoline.Type.WITH_FINE)) {
                return -1;

                // If not then check if trampoline has a larger cost so far.
            } else if (costSoFar.get(o1.getTrampoline()) > costSoFar.get(o2.getTrampoline())) {
                return 1;
            } else if ((costSoFar.get(o2.getTrampoline()) > costSoFar.get(o1.getTrampoline()))) {
                return -1;
            }
            return 0;
        });

        // Add start.
        // Creat new TrampolineData instance, where Trampoline and coordinates are stored.
        TrampolineData NWTrampolineData = new TrampolineData(NWTrampoline, 0, 0, map);
        TrampolineData current = NWTrampolineData;
        cameFrom.put(NWTrampolineData, null);
        costSoFar.put(NWTrampoline, 0);
        frontier.add(NWTrampolineData);  // Add start to the queue.

        // Find path.
        while (!frontier.isEmpty()) {
            current = frontier.poll();
            currentCoordinates = current.getCoordinates();

            if (Arrays.equals(currentCoordinates, finishCoordinates)) {
                break;
            }

            Trampoline[] neighbours = current.getNeighbours(map);
            for (Trampoline next: neighbours) {
                if (next == null || next.getType().equals(Trampoline.Type.WALL)) {
                    continue;
                }
                // Täpsustada.
                int neighbourCost = -TrampolineData.calculateTrampolineFine(next);
                int newCost = costSoFar.get(current.getTrampoline()) + neighbourCost + 1;
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    int[] coordinates = findTrampolineCoordinates(map, next, current);
                    TrampolineData nextTrampolineData = new TrampolineData(next, coordinates[0], coordinates[1], map);
                    costSoFar.put(next, newCost);
                    cameFrom.put(nextTrampolineData, current);
                    frontier.add(nextTrampolineData);
                }
            }
        }

        // Reconstruct the path.
        List<TrampolineData> path = new ArrayList<>();
        while (!current.equals(NWTrampolineData)) {
            // Move back from the finish to the start.
            path.add(current);
            current = cameFrom.get(current);
        }
        path.add(NWTrampolineData);

        return convertPathToResult(path);
    }

    /**
     * Find map finish coordinates.
     *
     * @param map with Trampoline instances.
     * @return finish coordinates.
     */
    private int[] findFinishCoordinates(Trampoline[][] map) {
        int x = map[0].length - 1;
        int y = map.length - 1;
        return new int[] {x, y};
    }

    // Poolik. Täpsustused. Lisada naabrid kohe listi.
    private int[] findTrampolineCoordinates(Trampoline[][] map, Trampoline trampoline, TrampolineData neighbourTrampoline) {
        int x = neighbourTrampoline.getX();
        int y = neighbourTrampoline.getY();
        Trampoline eastNeighbour = neighbourTrampoline.findTrampolineNeighbour(map, "east", 0);
        Trampoline eastNeighbourPlus1 = neighbourTrampoline.findTrampolineNeighbour(map, "east", 1);
        Trampoline eastNeighbourMinus1 = neighbourTrampoline.findTrampolineNeighbour(map, "east", -1);
        Trampoline southNeighbour = neighbourTrampoline.findTrampolineNeighbour(map, "south", 0);
        Trampoline southNeighbourPlus1 = neighbourTrampoline.findTrampolineNeighbour(map, "south", 1);
        if (eastNeighbour != null && eastNeighbour.equals(trampoline)) {
            int neighbourJumpForceEast = neighbourTrampoline.getJumpForceEast(map);
            return new int[] {x + neighbourJumpForceEast, y};
        } else if (eastNeighbourPlus1 != null && eastNeighbourPlus1.equals(trampoline)) {
            int neighbourJumpForceEast = neighbourTrampoline.getJumpForceEast(map) + 1;
            return new int[] {x + neighbourJumpForceEast, y};
        } else if (eastNeighbourMinus1 != null && eastNeighbourMinus1.equals(trampoline)) {
            int neighbourJumpForceEast = neighbourTrampoline.getJumpForceEast(map) - 1;
            return new int[] {x + neighbourJumpForceEast, y};
        } else if (southNeighbour != null && southNeighbour.equals(trampoline)) {
            int neighbourJumpForceSouth = neighbourTrampoline.getJumpForceSouth(map);
            return new int[] {x, y + neighbourJumpForceSouth};
        } else if (southNeighbourPlus1 != null && southNeighbourPlus1.equals(trampoline)) {
            int neighbourJumpForceSouth = neighbourTrampoline.getJumpForceSouth(map) + 1;
            return new int[] {x, y + neighbourJumpForceSouth};
        } else {
            int neighbourJumpForceSouth = neighbourTrampoline.getJumpForceSouth(map) - 1;
            return new int[] {x, y + neighbourJumpForceSouth};
        }
    }

    /**
     * Calculate jump force with coordinates.
     *
     * @param coordinate of the current Trampoline
     * @param nextCoordinate of the next Trampoline
     * @return current Trampoline jump force
     */
    private int calculateJumpForce(int coordinate, int nextCoordinate) {
        return nextCoordinate - coordinate;
    }

    /**
     * Convert path with TrampolineData instances to path with movement instructions and calculate path's total fine.
     *
     * Converted path and total fine are added to the Result instance.
     * @param path form finish to start with TrampolineData instances.
     * @return Result instance with a path from start to finish and total fine
     */
    private Result convertPathToResult(List<TrampolineData> path) {
        List<String> pathString = new ArrayList<>();
        int totalFine = 0;
        // Path is in reverse order.
        for (int i = path.size() - 1; i > 0; i--) {

            TrampolineData trampolineData = path.get(i);
            int[] coordinates = trampolineData.getCoordinates();  // Current trampoline coordinates.
            int[] nextCoordinates = path.get(i - 1).getCoordinates();  // Next trampoline coordinates.

            totalFine += trampolineData.getFine();

            if (nextCoordinates[0] > coordinates[0]) {  // If next is to the right from current, then next is in the east.
                int jumpForce = calculateJumpForce(coordinates[0], nextCoordinates[0]);
                pathString.add("E" + jumpForce);
            } else if (nextCoordinates[1] > coordinates[1]) {  // If next is to the left from current, then next is in the south.
                int jumpForce = calculateJumpForce(coordinates[1], nextCoordinates[1]);
                pathString.add("S" + jumpForce);
            }
        }

        // Create Result instance.
        HW02Result result = new HW02Result();
        result.setJumps(pathString);
        result.setTotalFine(totalFine);

        return result;
    }
}
