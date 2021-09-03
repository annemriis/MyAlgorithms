package ee.ttu.algoritmid.fibonacci;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class AL01B {

    /**
     * Estimate or find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(int n) {
        BigInteger rows =  iterativeF(n).multiply(BigInteger.valueOf(3)).subtract(BigInteger.valueOf(2));
        BigInteger startTime = BigInteger.valueOf(System.nanoTime());
        recursiveF(30);
        BigInteger endTime = BigInteger.valueOf(System.nanoTime());
        BigInteger oneRowTime = endTime.subtract(startTime).divide(BigInteger.valueOf(2496118));
        return String.valueOf(nanosecondsToYears(oneRowTime.multiply(rows)));
    }

    /**
     * Compute the Fibonacci sequence number recursively.
     * (You need this in the timeToComputeRecursiveFibonacci(int n) function!)
     * @param n The n-th number to compute.
     * @return The n-th Fibonacci number as a string.
     */
    public BigInteger recursiveF(int n) {
        if (n <= 1)
            return BigInteger.valueOf(n);
        return recursiveF(n - 1).add(recursiveF(n - 2));
    }

    /**
     * Compute the Fibonacci sequence number.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public BigInteger iterativeF(int n) {
        if (n == 0) {
            return BigInteger.valueOf(0);
        }
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(1);
        BigInteger c;
        for (int i = 3; i <= n; i++) {
            c = a.add(b);
            a = b;
            b = c;
        }
        return b;
    }

    /**
     * Calculate nanoseconds to years.
     * @param nanoseconds BigInteger.
     * @return years.
     */
    public BigDecimal nanosecondsToYears(BigInteger nanoseconds) {
        BigDecimal seconds = new BigDecimal(nanoseconds).divide(BigDecimal.valueOf(Math.pow(10, 9)),
                10, RoundingMode.HALF_UP);
        return seconds.divide(BigDecimal.valueOf(31557600), 10, RoundingMode.HALF_UP);
    }
}
