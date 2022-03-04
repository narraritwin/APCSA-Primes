import java.math.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    private static ArrayList<Prime> largePrimes = new ArrayList<>(); // Large Primes
    private static ArrayList<Prime> smallPrimes = new ArrayList<>(); // Small Primes
    private static ArrayList<Prime> specialPrimes = new ArrayList<>(); // Special Primes
    private static final int RANDOM_PRIME_COUNT = 510;

    /**
     * Sleeps for the specified time.
     * @param time  The amount of time to sleep, in milliseconds.
     */
    public static void sleep(int time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Utility function to print a string and then sleep for a specified amount of time/
     * @param s     The {@code String} to print out
     * @param ms    The amount of time to sleep, in milliseconds
     */
    private static void println(String s, int ms) {
        System.out.println(s);
        sleep(ms);
    }
    /**
     * Utility function to print a string and then sleep for a specified amount of time/
     * @param s     The {@code String} to print out
     * @param ms    The amount of time to sleep, in milliseconds
     */
    private static void print(String s, int ms) {
        System.out.print(s);
        sleep(ms);
    }
    
    /**
     * Uses the builtin function {@code BigInteger.isProbablePrime} to test
     * if a number is prime. See Prime.java; with certainty set to 60 there
     * is a one-in-a-quintillion chance that the number is not actually prime.
     * @param p     The number to test for primality
     * @return      True or False, depending on if the number is prime
     * @see         Prime.CERTAINTY
     */
    public static boolean isPrime(BigInteger p) {
        return p.isProbablePrime(Prime.CERTAINTY);
    }
    
    /**
     * Collects primes from all the various sources
     * @param progress  Whether to print out the current progress or not
     */
    private static void getPrimes(boolean progress) {
        if (progress) System.out.println("Parsing large primes...");
        largePrimes = Parser.parse1("https://primes.utm.edu/primes/lists/all.txt");
        if (progress) System.out.print("[");
        for (int i = 0; i < 36; i++) {
            print("=", 75);
        }
        if (progress) System.out.println("]\nLarge primes parsed!\n");

        
        if (progress) System.out.print("Parsing special primes...\n[");
        specialPrimes = Parser.parse2("https://web.archive.org/web/20160426065100/http://www.leyland.vispa.com/numth/primes/xyyx.htm");
        final int CHUNK = RANDOM_PRIME_COUNT / 35;
        for (int i = 0; i < RANDOM_PRIME_COUNT; i++) {
            if (progress && i % CHUNK == CHUNK-1) System.out.print("=");
            specialPrimes.add(Generator.randomPrime());
        }
        for (int pw = 128; pw >= 6; pw--) {
            specialPrimes.add(Generator.primeAbovePowerOfTen(pw));
        }
        
        if (progress) {
            System.out.println("]");
            System.out.println("Special primes parsed!\n");
            System.out.println("Parsing small primes...");
            System.out.print("[");
        }
        smallPrimes = Generator.smallPrimes(50000 - (specialPrimes.size()+largePrimes.size()));
        if (progress) {
            System.out.println("]");
            System.out.println("Small primes parsed!\n");
        }

        Collections.sort(specialPrimes);
        Collections.reverse(specialPrimes);
        if (progress) {
            sleep(500);
            System.out.println("All primes ready!\n");
        }
    }

    /**
     * The main function that provides a User Interface (through
     * the commandline) to get information about primes.
     */
    public static void main(String[] args) {
        getPrimes(true);
        sleep(500);
        // Actually this can be replaced with a constant 50000
        final int primes = largePrimes.size() + smallPrimes.size() + specialPrimes.size();
        System.out.println("We have a total of " + primes + " primes for you!\n");

        // Interacting with the User
        Scanner sc = new Scanner(System.in);
        println("What would you like to do?\n", 1000);
        while (true) {
            // 5 options in main menu
            println("1: Find out about some small primes", 300);
            println("2: Find out about some large primes", 300);
            println("3: Find out about some special primes", 300);
            println("4: Find out if a number is prime", 300);
            println("5: stop\n", 0);
            int choice = sc.nextInt();
            if (choice == 1) {
                // 3 choices in sub-menu
                while (true) {
                    println("Would you like to..", 200);
                    println("1: See a list of our small primes?", 200);
                    println("2: Find out about the nth smallest prime? (1-" + smallPrimes.size() + ")", 200);
                    println("3: Go back to the main menu\n", 0);
                    int choice2 = sc.nextInt();
                    System.out.println();
                    if (choice2 == 1) {
                        System.out.println("Here are our small primes:");
                        for (Prime p : smallPrimes) System.out.println(p.formula);
                        System.out.println();
                    } else if (choice2 == 2) {
                        while (true) {
                            System.out.println("Which small prime would you like to know about? (press -1 to exit)");
                            int choice3 = sc.nextInt();
                            if (choice3 == -1) {
                                System.out.println();
                                break;
                            } else if (choice3 < 1 || choice3 > smallPrimes.size()) {
                                System.out.println("Invalid choice! Please choose a number between (1-" + smallPrimes.size() + ").\n");
                                continue;
                            } else {
                                System.out.println(smallPrimes.get(choice3-1).formula + "\n");
                            }
                        }
                    } else {
                        break;
                    }
                }
            } else if (choice == 2) {
                // 3 choices in sub-menu
                while (true) {
                    println("Would you like to..", 200);
                    println("1: See a list of our large primes?", 200);
                    println("2: Find out about the nth largest (known) prime? (1-" + largePrimes.size() + ")", 200);
                    println("3: Go back to the main menu\n", 0);
                    int choice2 = sc.nextInt();
                    System.out.println();
                    if (choice2 == 1) {
                        System.out.println("Here are our large primes:");
                        for (Prime p : largePrimes) System.out.println(p);
                        System.out.println();
                    } else if (choice2 == 2) {
                        while (true) {
                            System.out.println("Which large prime would you like to know about? (press -1 to exit)");
                            int choice3 = sc.nextInt();
                            if (choice3 == -1) {
                                System.out.println();
                                break;
                            } else if (choice3 < 1 || choice3 > largePrimes.size()) {
                                System.out.println("Invalid choice! Please choose a number between (1-" + largePrimes.size() + ").\n");
                                continue;
                            } else {
                                System.out.println(largePrimes.get(choice3-1) + "\n");
                            }
                        }
                    } else {
                        break;
                    }
                }
            } else if (choice == 3) {
                // 3 choices in sub-menu
                while (true) {
                    println("Would you like to..", 200);
                    println("1: See a list of our special primes?", 200);
                    println("2: Find out about the nth special prime? (1-" + specialPrimes.size() + ")", 200);
                    println("3: Go back to the main menu\n", 0);
                    int choice2 = sc.nextInt();
                    System.out.println();
                    if (choice2 == 1) {
                        System.out.println("Here are our special primes:");
                        for (Prime p : specialPrimes) System.out.println(p);
                        System.out.println();
                    } else if (choice2 == 2) {
                        while (true) {
                            System.out.println("Which special prime would you like to know about? (press -1 to exit)");
                            int choice3 = sc.nextInt();
                            if (choice3 == -1) {
                                System.out.println();
                                break;
                            } else if (choice3 < 1 || choice3 > specialPrimes.size()) {
                                System.out.println("Invalid choice! Please choose a number between (1-" + specialPrimes.size() + ")\n");
                                continue;
                            } else {
                                System.out.println(specialPrimes.get(choice3-1) + "\n");
                            }
                        }
                    } else {
                        break;
                    }
                }
            } else if (choice == 4) {
                // Asks user for any size number, answers using BigInteger library 
                while (true) {
                    System.out.println("Which number would you like to test? (press -1 to exit)");
                    BigInteger choice2 = sc.nextBigInteger();
                    if (choice2.intValue() == -1) {
                        System.out.println();
                        break;
                    } else if (Prime.isPrime(choice2)) {
                        System.out.println("The number " + choice2 + " is prime!\n");
                    } else {
                        System.out.println("The number " + choice2 + " is not prime!\n");
                    }
                }
            } else {
                System.out.print("Goodbye!");
                break;
            }
            System.out.println("\n");
        }
        sc.close();
    }
}
