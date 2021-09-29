package ee.ttu.algoritmid.popularity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Popularity {

    private int maxCoordinates;
    private Map<List<Integer>, Integer> coordinates = new HashMap<>();
    private int numberOfOccurences = 0;

    public Popularity(int maxCoordinates) {
        this.maxCoordinates = maxCoordinates;
    }

    /**
     * @param x, y - coordinates
     */
    void addPoint(Integer x, Integer y) {
        if (x == null || y == null) {
            return;
        }
        List<Integer> point = List.of(x, y);
        coordinates.put(point, coordinates.getOrDefault(point, 0) + 1);
        int numberOfPointOccurances = coordinates.get(point);
        if (numberOfPointOccurances > numberOfOccurences) {
            numberOfOccurences = numberOfPointOccurances;
        }
    }

    /**
     * @param x, y - coordinates
     * @return the number of occurrennces of the point
     */
    int pointPopularity(Integer x,Integer y) {
        return coordinates.get(List.of(x, y));
    }


    /**
     * @return the number of occurrennces of the most popular point
     */
    int maxPopularity() {
        return numberOfOccurences;
    }

}
