package ee.ttu.algoritmid.fibonacci;

public class AL01A {

    /**
     * Compute the Fibonacci sequence number.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public String iterativeF(int n) {
        int a = 1, b = 1, c;
        for (int i = 3; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return String.valueOf(b);
    }

    public static void main(String[] args) {
        AL01A al01A = new AL01A();
        System.out.println(al01A.iterativeF(12));
    }
}
