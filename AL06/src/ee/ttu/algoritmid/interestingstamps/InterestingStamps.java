package ee.ttu.algoritmid.interestingstamps;

import java.util.*;
import java.util.stream.Collectors;

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
        int lastIndex = stampOptions.size() - 1;
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.addAll(stampOptions);
        Set<Integer> set = new HashSet<>(heap);
        while (!heap.isEmpty()) {
            int minSum = heap.poll();
            for (Integer number: set) {
                int numbersSum = number + minSum;
                if (numbersSum <= sum && !set.contains(numbersSum)) {
                    heap.add(numbersSum);
                }
            }
            set.addAll(heap);
        }
        List<Integer> setList = set.stream().sorted().collect(Collectors.toList());
        for (Integer i: setList) {
            optimalSolution[i] = Integer.MAX_VALUE;
            optimalInteresting[i] = 0;
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
        System.out.println(findStamps(100, stamps));
    }
}
