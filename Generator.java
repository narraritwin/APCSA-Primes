import java.math.BigInteger;
import java.util.Random;

public class Generator {
    private static final int minBits = 330;
    private static final int maxBits = 420;
    public static BigInteger generateRandomPrime() {
        Random rng = new Random();
        BigInteger p = BigInteger.ZERO;
        // Generate a random prime between 100 and 127 digits
        while (p.bitLength() < minBits || !p.isProbablePrime(Prime.CERTAINTY)) {
            p = new BigInteger(maxBits, rng);
        }
        return p;
    }
    public static Prime randomPrime() {
        String p = generateRandomPrime().toString();
        return new Prime(p, p.length(), 2022, "Random prime");
    }

    public static Prime primeAbovePowerOfTen(int pw) {
        if (pw == 0) return new Prime(BigInteger.TWO, "Above a power of ten");
        BigInteger p = BigInteger.TEN.pow(pw);

        /* 10^n+1 is guaranteed composite for all n that has an odd divisor
         * other than 1. Proof: Let's say that divisor is some prime p, then
         * 10^(n/p)+1 divides n from sum of p-th powers formula
         * So 10^n+1 can only possibly be prime when n is a power of two
         * Case in point: 11, 101; however actually no more are known */
        int lastDigit = (pw & -pw) == pw ? 1 : 3;
        if ((pw & -pw) == pw) p = p.add(BigInteger.ONE);
        else p = p.add(BigInteger.valueOf(3));

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
}
