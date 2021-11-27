package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class HW02 implements TrampolineCenter {

    @Override
    public Result play(Trampoline[][] map) {
        PriorityQueue<Trampoline> frontier = new PriorityQueue<>(Comparator.comparingInt(Trampoline::getJumpForce));
        Trampoline NWTrampoline = map[0][0];
        Integer[] currentCoordinates = new Integer[]{0, 0};
        Integer[] startCoordinates = new Integer[]{0, 0};
        Integer[] finishCoordinates = findFinishCoordinates(map);
        frontier.add(NWTrampoline);  // Add start to the queue. O(log(n))
        // Poolik.
        HashMap<Integer[], Trampoline> cameFrom = new HashMap<>();
        HashMap<Trampoline, Integer> costSoFar = new HashMap<>();
        // Poolik cameFromCoordinates.
        HashMap<Integer[], Integer[]> cameFromCoordinates = new HashMap<>();
        HashMap<Trampoline, Integer[]> coordinatesMap = new HashMap<>();
        cameFrom.put(startCoordinates, null);
        costSoFar.put(NWTrampoline, 0);
        cameFromCoordinates.put(startCoordinates, startCoordinates);  // Add start coordinates.
        coordinatesMap.put(NWTrampoline, startCoordinates);

        while (!frontier.isEmpty()) {
            Trampoline current = frontier.poll();
            currentCoordinates = coordinatesMap.get(current);

            if (Arrays.equals(currentCoordinates, finishCoordinates)) {
                break;
            }

            Trampoline[] neighbours = findNeighbours(map, currentCoordinates, current);
            for (Trampoline next: neighbours) {
                int neighbourCost = calculateTrampolineFine(next);
                int newCost = costSoFar.get(current) + neighbourCost;
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    Integer[] coordinates = findTrampolineCoordinates(map, next, current, currentCoordinates);
                    costSoFar.put(next, newCost);
                    frontier.add(next);
                    cameFrom.put(currentCoordinates, current);
                    cameFromCoordinates.put(coordinates, currentCoordinates);
                    coordinatesMap.put(next, coordinates);
                }
            }
        }

        // Reconstruct the path.
        List<Integer[]> path = new ArrayList<>();
        List<Trampoline> pathWithTrampolines = new ArrayList<>();
        while (currentCoordinates != startCoordinates) {
            path.add(currentCoordinates);
            Trampoline trampoline = cameFrom.get(currentCoordinates);
            pathWithTrampolines.add(trampoline);
            currentCoordinates = cameFromCoordinates.get(currentCoordinates);
        }
        path.add(startCoordinates);
        pathWithTrampolines.add(NWTrampoline);
        Collections.reverse(path);
        Collections.reverse(pathWithTrampolines);

        return convertPathToResult(path, pathWithTrampolines);
    }

    private Trampoline[] findNeighbours(Trampoline[][] map, Integer[] coordinates, Trampoline trampoline) {
        Trampoline[] neighbours = new Trampoline[2];
        Trampoline eastNeighbour = findTrampolineNeighbour(map, coordinates, trampoline, "east");
        Trampoline southNeighbour = findTrampolineNeighbour(map, coordinates, trampoline, "south");
        neighbours[0] = eastNeighbour;
        neighbours[1] = southNeighbour;
        return fixNeighboursArray(neighbours);
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

    private Integer[] findFinishCoordinates(Trampoline[][] map) {
        int x = map[0].length - 1;
        int y = map.length - 1;
        return new Integer[] {x, y};
    }

    private int calculateTrampolineFine(Trampoline trampoline) {
        Trampoline.Type type = trampoline.getType();
        if (type.equals(Trampoline.Type.WITH_FINE)) {
            return trampoline.getJumpForce();
        }
        return 0;
    }

    private Integer[] findTrampolineCoordinates(Trampoline[][] map, Trampoline trampoline, Trampoline neighbourTrampoline, Integer[] neighbourCoordinates) {
        int x = neighbourCoordinates[0];
        int y = neighbourCoordinates[1];
        int neighbourJumpForce = neighbourTrampoline.getJumpForce();
        Trampoline eastNeighbour = findTrampolineNeighbour(map, neighbourCoordinates, neighbourTrampoline, "east");
        if (eastNeighbour != null && eastNeighbour.equals(trampoline)) {
            return new Integer[] {x + neighbourJumpForce, y};
        } else {
            return new Integer[] {x, y + neighbourJumpForce};
        }
    }

    private Trampoline findTrampolineNeighbour(Trampoline[][] map, Integer[] coordinates, Trampoline trampoline, String quarter) {
        int x = coordinates[0];
        int y = coordinates[1];
        int mapLength = map.length - 1;
        int mapWidth = map[0].length - 1;
        int jumpForce = trampoline.getJumpForce();
        if (quarter.equals("east")) {
            // Pooleli
            int neighbourX = x + jumpForce;
            if (neighbourX <= mapWidth) {  // Find neighbour from east.
                return map[y][neighbourX];
            }
        } else if (quarter.equals("south")) {
            // Pooleli
            int neighbourY = y + jumpForce;
            if (neighbourY <= mapLength) {  // Find neighbour from south.
                return map[neighbourY][x];
            }
        }
        return null;
    }

    private Result convertPathToResult(List<Integer[]> path, List<Trampoline> pathWithTrampolines) {
        List<String> pathString = new ArrayList<>();
        int totalFine = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Trampoline trampoline = pathWithTrampolines.get(i);
            Integer[] coordinates = path.get(i);
            int jumpForce = trampoline.getJumpForce();
            Integer[] nextCoordinates = path.get(i + 1);
            totalFine += calculateTrampolineFine(trampoline);
            if (nextCoordinates[0] > coordinates[0]) {
                pathString.add("E" + jumpForce);
            } else if (nextCoordinates[1] > coordinates[1]) {
                pathString.add("S" + jumpForce);
            }
        }
        HW02Result result = new HW02Result();
        result.setJumps(pathString);
        result.setTotalFine(totalFine);
        return result;
    }
}
