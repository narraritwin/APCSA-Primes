public class Prime {
    static int primes = 0;
    int rank;
    String formula;
    int digitCount;
    int year;
    String comment;
    public Prime(String formula, int digitCount, int year, String comment) {
        this.rank = Prime.primes++;
        this.formula = formula;
        this.digitCount = digitCount;
        this.year = year;
        this.comment = comment;
    }
    public String toString() {
        String s = this.formula + " has " + this.digitCount + " digits and was found in " + this.year;
        return comment.equals("") ? s : s + " (" + this.comment + ")";
    }
}
