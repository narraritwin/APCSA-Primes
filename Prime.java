import java.math.BigInteger;

public class Prime implements Comparable<Prime> {
    /* This means there's a 2^-60 = 1 in a quintillion chance
       that a probable prime we find is not actually prime. */
    public static final int CERTAINTY = 60;

    private static int totalPrimes = 0;

    int rank;
    String formula;
    int digitCount;
    int year;
    String comment;
    /**
     * Constructs a prime with the relevant information
     * @param formula       A short representation of the prime
     * @param digitCount    The number of digits of the prime
     * @param year          The year the prime was found
     * @param comment       An optional comment; usually a prime form like "Mersenne Number"
     */
    public Prime(String formula, int digitCount, int year, String comment) {
        this.rank = Prime.totalPrimes++;
        this.formula = formula;
        this.digitCount = digitCount;
        this.year = year;
        this.comment = comment;
    }
    /**
     * Constructs a prime with the decimal representation instead of some formula.
     * Used when generating primes, rather than the primes that we've parsed.
     * @param p         The decimal representation, instead of a formula
     * @param year      The year the prime was found
     * @param comment   An optional comment; usually a prime form like "Mersenne Number"
     */
    public Prime(BigInteger p, int year, String comment) {
        String s = p.toString();
        this.rank = Prime.totalPrimes++;
        this.formula = s;
        this.digitCount = s.length();
        this.year = year;
        this.comment = comment;
    }
    /**
     * Constructs a prime with the decimal representation instead of some formula.
     * Used when generating primes, rather than the primes that we've parsed.
     * When we generate a prime we want the year as 2022 anyway, so that's default.
     * @param p         The decimal representation, instead of a formula
     * @param comment   An optional comment; usually a prime form like "Mersenne Number"
     */
    public Prime(BigInteger p, String comment) {
        this(p, 2022, comment);
    }
    /**
     * Constructs a prime with the decimal representation instead of some formula.
     * Used when generating primes, rather than the primes that we've parsed.
     * When we generate a prime we want the year as 2022 anyway, so that's default.
     * @param p         The decimal representation, instead of a formula
     * @param comment   An optional comment; usually a prime form like "Mersenne Number"
     */
    public Prime(int p, String comment) {
        this(BigInteger.valueOf(p), comment);
    }
    /**
     * Converts the prime to a String
     * @return The prime as a String
     */
    public String toString() {
        String s = this.formula + " has " + this.digitCount + " digits and was found in " + this.year;
        return comment.equals("") ? s : s + " (" + this.comment + ")";
    }

    @Override
    /**
     * <p>Comparator for Primes</p>
     * 
     * This will only work for the primes we generate; because they have exact
     * representations. For numbers that we parsed from primes.utm.edu, we
     * would need a lot more infrastructure to handle comparison correctly
     * @param other     The other prime being compared
     * @return          An integer representing the comparison as is standard: <ul>
     *                     <li> negative means {@code this < other}
     *                     <li> 0 means {@code this == other}
     *                     <li> positive means {@code this > other} </ul>
     */
    public int compareTo(Prime other) {
        if (this.digitCount != other.digitCount) {
            return this.digitCount - other.digitCount;
        }
        return this.formula.compareTo(other.formula);
    }
}
