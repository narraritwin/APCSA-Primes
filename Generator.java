import java.math.BigInteger;
import java.util.Random;
import java.util.ArrayList;

/**
 * A class that generates primes. See each function for the relevant information.
 */
public class Generator {
    private static final int minBits = 330;
    private static final int maxBits = 420;
    /**
     * Generates a random prime with between minBits and maxBits binary digits (inclusive).
     * Note that each bit length does not have an equal probability of occurring.
     * @return  A BigInteger prime in the specified interval
     */
    public static BigInteger generateRandomPrime() {
        Random rng = new Random();
        BigInteger p = BigInteger.ZERO;
        // Generate a random prime between 100 and 127 digits
        while (p.bitLength() < minBits || !p.isProbablePrime(Prime.CERTAINTY)) {
            p = new BigInteger(maxBits, rng);
        }
        return p;
    }
    /**
     * Generates a random prime with between minBits and maxBits binary digits (inclusive).
     * Note that each bit length does not have an equal probability of occurring.
     * @return  A prime in the specified interval, but of type Prime
     * @see     generateRandomPrime
     */
    public static Prime randomPrime() {
        String p = generateRandomPrime().toString();
        return new Prime(p, p.length(), 2022, "Random prime");
    }

    /**
     * Generates the smallest prime above 10^pw
     * @return  That prime, with type Prime
     */
    public static Prime primeAbovePowerOfTen(int pw) {
        if (pw == 0) return new Prime(BigInteger.TWO, "Above a power of ten");
        BigInteger p = BigInteger.TEN.pow(pw);

        /* 10^n+1 is guaranteed composite for all n that has an odd divisor
         * other than 1. Proof: Let's say that divisor is some prime p, then
         * 10^(n/p)+1 divides n from sum of p-th powers formula
         * So 10^n+1 can only possibly be prime when n is a power of two
         * Case in point: 11, 101; however actually no more are known
         * Note that (pw & -pw) == pw checks if pw is a power of two or zero
         * (works because of the two's complement representation of integers)
         */
        if ((pw & -pw) == pw) p = p.add(BigInteger.ONE);
        else p = p.add(BigInteger.valueOf(3));
        int lastDigit = (pw & -pw) == pw ? 1 : 3;

        while (!p.isProbablePrime(Prime.CERTAINTY)) {
            if (lastDigit == 1) {
                lastDigit = 3;
                p = p.add(BigInteger.TWO);
            } else if (lastDigit == 3) {
                lastDigit = 7;
                p = p.add(BigInteger.valueOf(4));
            } else if (lastDigit == 7) {
                lastDigit = 9;
                p = p.add(BigInteger.TWO);
            } else if (lastDigit == 9) {
                lastDigit = 1;
                p = p.add(BigInteger.TWO);
            }
        }
        return new Prime(p, "Above a power of ten");
    }

    /**
     * Generates the smallest prime above 2^pw
     * @param pw    The power of two to start at
     * @return      That prime, with type Prime
     */
    public static Prime primeAbovePowerOfTwo(int pw) {
        BigInteger p = BigInteger.TWO.pow(pw);

        /* Same story as before, but we don't need to keep track of the
           last digit in this case because it doesn't optimize much
           Although if we wanted to, keeping track modulo 3 and 7 could help */
        p = p.add((pw & -pw) == pw ? BigInteger.ONE : BigInteger.valueOf(3));

        while (!p.isProbablePrime(Prime.CERTAINTY)) {
            p = p.add(BigInteger.TWO);
        }
        return new Prime(p, "Above a power of two");
    }

    /**
     * Tests primality in O(√n) by trial division; runs almost
     * instantly for all ints n (although if changed to a long,
     * it will take noticable time for n > 10¹⁶ or so)
     * @param n     The number to test for primality
     * @return      Whether the specified number is prime
     */
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i*i <= n; i++) {
            if (n%i == 0) return false;
        }
        return true;
    }

    /**
     * Finds the first primeCount primes
     * @param primeCount    The number of primes to find
     * @return              an {@code ArrayList<Prime>} with those primes
     */
    public static ArrayList<Prime> smallPrimes(int primeCount) {
        final int part = primeCount / 36;
        ArrayList<Prime> primes = new ArrayList<>();
        // Because we're only searching for a few primes, we can brute
        // force the computation (we're using O(sqrt n) primality testing).
        for (int p = 2; primes.size() < primeCount; p++) {
            if (!isPrime(p)) continue;
            primes.add(new Prime(p, "Small prime"));
            if (primes.size() % part == 0) {
                System.out.print("=");
            }
        }
        return primes;
    }
}
