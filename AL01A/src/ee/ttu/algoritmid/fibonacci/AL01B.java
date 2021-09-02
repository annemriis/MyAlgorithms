package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01B {

    /**
     * Estimate or find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(int n) {
        BigInteger rows =  iterativeF(n).multiply(BigInteger.valueOf(3)).subtract(BigInteger.valueOf(2));
        BigInteger startTime = BigInteger.valueOf(System.nanoTime());
        recursiveF(2);
        BigInteger endTime = BigInteger.valueOf(System.nanoTime());
        return String.valueOf(rows.multiply(startTime.subtract(endTime)));
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
}
