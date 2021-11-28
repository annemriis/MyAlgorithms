package ee.ttu.algoritmid.interestingstamps;

import java.util.*;
import java.util.stream.Collectors;

public class InterestingStamps {

    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
        if (stampOptions.isEmpty()) {
                throw new IllegalArgumentException();
        }
        Map<Integer, Integer> optimalSolution = new HashMap<>();
        Map<Integer, Integer> lastChosenMark = new HashMap<>();
        Map<Integer, Integer> optimalInteresting = new HashMap<>();
        Collections.reverse(stampOptions);
        stampOptions = stampOptions.stream().filter(stamp -> stamp <= sum).collect(Collectors.toList());
        if (stampOptions.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> stamps = new ArrayList<>();
        int lastIndex = stampOptions.size() - 1;
        for (int i = stampOptions.get(lastIndex); i < sum + 1; i++) {
            optimalSolution.put(i, Integer.MAX_VALUE);
            optimalInteresting.put(i, 0);
            for (Integer stamp: stampOptions) {
                if ((i >= stamp) && (optimalSolution.get(i) >= optimalSolution.getOrDefault(i - stamp, 0) + 1)) {
                    if (optimalSolution.get(i) > optimalSolution.getOrDefault(i - stamp, 0) + 1) {
                        optimalSolution.put(i, optimalSolution.getOrDefault(i - stamp, 0) + 1);
                        lastChosenMark.put(i, stamp);
                    } else if (isInterestingStamp(stamp) && optimalInteresting.get(i) < optimalInteresting.getOrDefault(i - stamp, 0) + 1) {
                        optimalInteresting.put(i, optimalInteresting.getOrDefault(i - stamp, 0) + 1);
                        optimalSolution.put(i, optimalSolution.getOrDefault(i - stamp, 0) + 1);
                        lastChosenMark.put(i, stamp);
                    }
                }
            }
        }
        int n = sum;
        while (n > 0) {
            stamps.add(lastChosenMark.get(n));
            n -= lastChosenMark.get(n);
        }
        return stamps;
    }

    private static boolean isInterestingStamp(Integer stamp) {
        return stamp != 1 && stamp % 10 != 0;
    }
}
