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

        // Poolik.
        PriorityQueue<TrampolineData> frontier = new PriorityQueue<>((o1, o2) -> {
            if (o1.getTrampoline().getType().equals(Trampoline.Type.WITH_FINE)) {
                return 1;
            } else if (o2.getTrampoline().getType().equals(Trampoline.Type.WITH_FINE)) {
                return -1;
            } else if (costSoFar.get(o1.getTrampoline()) > costSoFar.get(o2.getTrampoline())) {
                return 1;
            } else if (costSoFar.get(o2.getTrampoline()) > costSoFar.get(o1.getTrampoline())) {
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
        frontier.add(NWTrampolineData);  // Add start to the queue. O(log(n))

        // Poolik.
        // Find path.
        while (!frontier.isEmpty()) {
            current = frontier.poll();
            currentCoordinates = current.getCoordinates();

            if (Arrays.equals(currentCoordinates, finishCoordinates)) {
                break;
            }

            Trampoline[] neighbours = current.findNeighbours(map);
            for (Trampoline next: neighbours) {
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

        // Valmis.
        // Reconstruct the path.
        List<TrampolineData> path = new ArrayList<>();
        while (!current.equals(NWTrampolineData)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        path.add(NWTrampolineData);

        return convertPathToResult(path, map);
    }

    // Valmis.
    private int[] findFinishCoordinates(Trampoline[][] map) {
        int x = map[0].length - 1;
        int y = map.length - 1;
        return new int[] {x, y};
    }

    // Poolik
    private int[] findTrampolineCoordinates(Trampoline[][] map, Trampoline trampoline, TrampolineData neighbourTrampoline) {
        int x = neighbourTrampoline.getX();
        int y = neighbourTrampoline.getY();
        Trampoline eastNeighbour = neighbourTrampoline.findTrampolineNeighbour(map, "east", 0);
        Trampoline eastNeighbourPlus1 = neighbourTrampoline.findTrampolineNeighbour(map, "east", 1);
        Trampoline eastNeighbourMinus1 = neighbourTrampoline.findTrampolineNeighbour(map, "east", -1);
        if (eastNeighbour != null && eastNeighbour.equals(trampoline)) {
            int neighbourJumpForceEast = neighbourTrampoline.getJumpForceEast();
            return new int[] {x + neighbourJumpForceEast, y};
        } if (eastNeighbourPlus1 != null && eastNeighbourPlus1.equals(trampoline)) {
            int neighbourJumpForceEast = neighbourTrampoline.getJumpForceEast() + 1;
            return new int[] {x + neighbourJumpForceEast, y};
        } if (eastNeighbourMinus1 != null && eastNeighbourMinus1.equals(trampoline)) {
            int neighbourJumpForceEast = neighbourTrampoline.getJumpForceEast() - 1;
            return new int[] {x + neighbourJumpForceEast, y};
        } else {
            int neighbourJumpForceSouth = neighbourTrampoline.getJumpForceSouth();
            return new int[] {x, y + neighbourJumpForceSouth};
        }
    }

    private int calculateJumpForce(int coordinate, int nextCoordinate) {
        return nextCoordinate - coordinate;
    }

    // Valmis.
    private Result convertPathToResult(List<TrampolineData> path, Trampoline[][] map) {
        List<String> pathString = new ArrayList<>();
        int totalFine = 0;
        // Path is in reverse order.
        for (int i = path.size() - 1; i > 0; i--) {

            TrampolineData trampolineData = path.get(i);
            int[] coordinates = trampolineData.getCoordinates();
            int jumpForce;
            int[] nextCoordinates = path.get(i - 1).getCoordinates();

            totalFine += trampolineData.getFine();

            if (nextCoordinates[0] > coordinates[0]) {  // If next is to the right from current, then next is in the east.
                jumpForce = calculateJumpForce(coordinates[0], nextCoordinates[0]);
                pathString.add("E" + jumpForce);
            } else if (nextCoordinates[1] > coordinates[1]) {  // If next is to the left from current, then next is in the south.
                jumpForce = calculateJumpForce(coordinates[1], nextCoordinates[1]);
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
