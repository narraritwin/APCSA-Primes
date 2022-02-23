import java.util.*;

public class Main {
    private static final int RANDOM_PRIME_COUNT = 1000;
    private static ArrayList<Prime> getPrimes(boolean progress) {
        if (progress) System.out.println("Parsing large primes...");
        ArrayList<Prime> primes = Parser.parse1("https://primes.utm.edu/primes/lists/all.txt");
        if (progress) System.out.println("Large primes parsed!");

        if (progress) System.out.println("Parsing special primes...");
        ArrayList<Prime> newPrimes = Parser.parse2("https://web.archive.org/web/20160426065100/http://www.leyland.vispa.com/numth/primes/xyyx.htm");
        if (progress) System.out.println("Special primes parsed!");

        // PrintWriter pw = new PrintWriter("primes.txt", "UTF-8");
        // for (Prime p : primes) pw.println(p);
        // pw.close();

        if (progress) System.out.println("Generating random primes...");
        for (int i = 0; i < RANDOM_PRIME_COUNT; ++i) {
            newPrimes.add(Generator.randomPrime());
        }
        if (progress) System.out.println("Random primes generated!");

        if (progress) System.out.println("Generating primes above powers...");
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
        if (progress) System.out.println("Primes above powers generated!\n");

        Collections.sort(newPrimes);
        Collections.reverse(newPrimes);
        for (Prime p : newPrimes) primes.add(p);

        if (progress) System.out.println("All primes ready!\n");
        return primes;
    }

    public static void main(String[] args) {
        ArrayList<Prime> primes = getPrimes(true);
        /*System.out.println("We have " + primes.size() + " primes for you!\n");

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the index of a prime you want to know about: ");
            int i = sc.nextInt();
            if (i < 0) i += primes.size();
            if (i < 0 || i > primes.size()) break;
            System.out.println(primes.get(i));
            System.out.println();
        }
        sc.close();*/
        //Interacting with the User
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello!");
        sleep(1500);
        System.out.println("This program contains " + primes.size() + " of the largest prime numbers.");
        sleep(2000);
        System.out.println("What would you like to do?\n");
        sleep(2000);
        while(true){
            System.out.println("1: See the list of primes");
            sleep(500);
            System.out.println("2: Find the nth largest prime (1-5000)");
            sleep(500);
            System.out.println("3: stop");
            int choice = scan.nextInt();
            if(choice == 3)break;
            else if(choice == 1){
                System.out.println("Here is the list of primes:");
                for(int i = 0; i < primes.size(); ++i){
                    System.out.println(primes.get(i));
                }
            }else if(choice == 2){
                System.out.println("Which prime would you like to find?");
                int n = scan.nextInt();
                System.out.print("The " + n + "th prime is " + primes.get(n-1) + ".");
            }
            System.out.println("\n");
        }

    }
}
