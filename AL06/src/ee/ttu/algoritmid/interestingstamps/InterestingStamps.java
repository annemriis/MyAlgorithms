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
        int[] optimalInteresting = new int[sum + 1];
        stampOptions.sort(Collections.reverseOrder());
        List<Integer> stamps = new ArrayList<>();
        int solution = 0;
        int nextSolution = sum;
        int lastIndex = stampOptions.size() - 1;
        for (int i = sum; i >= stampOptions.get(lastIndex); i--) {
            optimalSolution[i] = Integer.MAX_VALUE;
            optimalInteresting[i] = 0;
            if (solution == sum) {
                break;
            }
            for (int j = 0; j < stampOptions.size(); j++) {
                Integer stamp = stampOptions.get(j);
                if ((i >= stamp) && (optimalSolution[i] >= optimalSolution[i - stamp] + 1)) {
                    if (optimalSolution[i] > optimalSolution[i - stamp] + 1) {
                        optimalSolution[i] = optimalSolution[i - stamp] + 1;
                        lastChosenMark[i] = stamp;
                    } else if (isInterestingStamp(stamp) && optimalInteresting[i] < optimalInteresting[i - stamp] + 1) {
                        optimalInteresting[i] = optimalInteresting[i - stamp] + 1;
                        optimalSolution[i] = optimalSolution[i - stamp] + 1;
                        lastChosenMark[i] = stamp;
                    }
                }
            }
            if (i == nextSolution) {
                solution += lastChosenMark[i];
                nextSolution = lastChosenMark[i];
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

    public static void main(String[] args) {
        ArrayList<Integer> stamps = new ArrayList<>();
        stamps.add(1);
        stamps.add(10);
        stamps.add(24);
        stamps.add(30);
        stamps.add(33);
        stamps.add(36);
        stamps.add(1000);
        System.out.println(findStamps(100, stamps));
    }
}
