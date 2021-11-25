package ee.ttu.algoritmid.interestingstamps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterestingStamps {

    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
        if (stampOptions.isEmpty()) {
                throw new IllegalArgumentException();
            }
        int[] optimalSolution = new int[sum + 1];
        int[] lastChosenMark = new int[sum + 1];
        stampOptions.sort(Collections.reverseOrder());
        List<Integer> stamps = new ArrayList<>();
        for (int i = 0; i < sum + 1; i++) {
            optimalSolution[i] = Integer.MAX_VALUE;
            for (Integer stamp: stampOptions) {
                if ((i >= stamp) && (optimalSolution[i] > optimalSolution[i - stamp] + 1)) {
                   optimalSolution[i] = optimalSolution[i - stamp] + 1;
                   lastChosenMark[i] = stamp;
                }
            }
        }
        int n = sum;
        while (n > 0) {
            stamps.add(lastChosenMark[n]);
            n -= lastChosenMark[n];
        }
        return stamps;
    }

    private static boolean isInterestingStamp(Integer stamp) {
        return stamp != 1 && stamp % 10 != 0;
    }
}
