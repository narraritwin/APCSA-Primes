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
    public Prime(String formula, int digitCount, int year, String comment) {
        this.rank = Prime.totalPrimes++;
        this.formula = formula;
        this.digitCount = digitCount;
        this.year = year;
        this.comment = comment;
    }
    public Prime(BigInteger p, int year, String comment) {
        String s = p.toString();
        this.rank = Prime.totalPrimes++;
        this.formula = s;
        this.digitCount = s.length();
        this.year = year;
        this.comment = comment;
    }
    public Prime(BigInteger p, String comment) {
        this(p, 2022, comment);
    }
    public String toString() {
        String s = this.formula + " has " + this.digitCount + " digits and was found in " + this.year;
        return comment.equals("") ? s : s + " (" + this.comment + ")";
    }

    @Override
    /**
     * This will only work for the primes we generate; because they have exact
     * representations. For numbers that we parsed from primes.utm.edu, we
     * would need a lot more infrastructure to handle comparison correctly
     */
    public int compareTo(Prime other) {
        if (this.digitCount != other.digitCount) {
            return this.digitCount - other.digitCount;
        }
        return this.formula.compareTo(other.formula);
    }
}
