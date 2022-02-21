import java.util.*;

public class Main {
    private static final int RANDOM_PRIME_COUNT = 1000;
    public static void main(String[] args) {
        System.out.println("Parsing primes...");
        ArrayList<Prime> primes = Parser.parse("https://primes.utm.edu/primes/lists/all.txt");
        System.out.println("Primes parsed!");

        // PrintWriter pw = new PrintWriter("primes.txt", "UTF-8");
        // for (Prime p : primes) pw.println(p);
        // pw.close();

        ArrayList<Prime> newPrimes = new ArrayList<>();

        System.out.println("Generating random primes...");
        for (int i = 0; i < RANDOM_PRIME_COUNT; ++i) {
            newPrimes.add(Generator.randomPrime());
        }
        System.out.println("Random primes generated!");

        System.out.println("Generating primes above powers...");
        for (int pw = 128; pw >= 6; --pw) {
            newPrimes.add(Generator.primeAbovePowerOfTen(pw));
        }
        /* In my opinion, primes above powers of two are not as
         * interesting as primes above a power of 10 (because
         * the latter ends up having so many consecutive zeros) */
        // System.out.println("...");
        // for (int pw = 512; pw >= 20; --pw) {
        //     newPrimes.add(Generator.primeAbovePowerOfTwo(pw));
        // }
        System.out.println("Primes above powers generated!\n");

        Collections.sort(newPrimes);
        Collections.reverse(newPrimes);
        for (Prime p : newPrimes) primes.add(p);

        System.out.println("All primes ready!\n");

        System.out.println("We have " + primes.size() + " primes for you!\n");

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the index of a prime you want to know about: ");
            int i = sc.nextInt();
            if (i < 0) i += primes.size();
            if (i < 0 || i > primes.size()) break;
            System.out.println(primes.get(i));
            System.out.println();
        }
        sc.close();
    }
}
