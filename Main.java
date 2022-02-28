import java.util.*;
import java.util.concurrent.*;
import java.math.*;

public class Main {
    private static ArrayList<Prime> Lprimes = new ArrayList<>(); // Large Primes
    private static ArrayList<Prime> Smprimes = new ArrayList<>(); // Small Primes
    private static ArrayList<Prime> Sprimes = new ArrayList<>(); // Special Primes
    private static final int RANDOM_PRIME_COUNT = 1000;

    public static void sleep(int time){
        //Forces program to sleep, allows modification of speed for User Interface.
        try{
            TimeUnit.MILLISECONDS.sleep(time);
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    private static void getPrimes(boolean progress) {
        
        //Accessing primes read/generated from other programs.
        
        if (progress) System.out.println("Parsing large primes...");
        Lprimes = Parser.parse1("https://primes.utm.edu/primes/lists/all.txt");
        System.out.print("[");
        for(int i = 0 ; i < 36; ++i){
            sleep(75);
            System.out.print("=");
        }
        System.out.println("]");
        if (progress) System.out.println("Large primes parsed!\n");

        if (progress) System.out.println("Parsing special primes...");
        System.out.print("[");
        Sprimes = Parser.parse2("https://web.archive.org/web/20160426065100/http://www.leyland.vispa.com/numth/primes/xyyx.htm");
        for (int i = 0; i < RANDOM_PRIME_COUNT; ++i) {
            if(i % (RANDOM_PRIME_COUNT /18) == (RANDOM_PRIME_COUNT/18-1)) System.out.print("=");
            Sprimes.add(Generator.randomPrime());
        }
        for (int pw = 128; pw >= 6; --pw) {
            if(pw%7==0) System.out.print("=");
            Sprimes.add(Generator.primeAbovePowerOfTen(pw));
        }
        System.out.println("]");
        if (progress) System.out.println("Special primes parsed!\n");
        if (progress) System.out.println("Parsing small primes...");
        System.out.print("[");
        Smprimes = Generator.smallPrimes("https://www2.cs.arizona.edu/icon/oddsends/primes.htm", 50000-(Sprimes.size()+Lprimes.size()));
        System.out.println("]");
        if (progress) System.out.println("Small primes parsed!\n");

        Collections.sort(Sprimes);
        Collections.reverse(Sprimes);
        sleep(500);
        if (progress) System.out.println("All primes ready!\n");
    }

    public static boolean isPrime(BigInteger b){
        return b.isProbablePrime(100);
    }

    public static void main(String[] args) {
        getPrimes(true);
        sleep(500);
        System.out.println("We have a total of " + (Lprimes.size() + Smprimes.size() + Sprimes.size()) + " primes for you!\n"); // # of primes can be replaced with 50,000
        
        //Interacting with the User
        
        Scanner scan = new Scanner(System.in);
        System.out.println("What would you like to do?\n");
        sleep(1000);
        while(true){
            //5 options in main menu
            System.out.println("1: Find out some Small primes");
            sleep(300);
            System.out.println("2: Find out some Large primes");
            sleep(300);
            System.out.println("3: Find out some Special primes");
            sleep(300);
            System.out.println("4: Find out if a number is prime");
            sleep(300);
            System.out.println("5: stop");
            System.out.println();
            int choice = scan.nextInt();
            if(choice == 1){
                //3 choices in sub-menu
                while(true){
                    System.out.println("Would you like to..");
                    sleep(200);
                    System.out.println("1: See a list of our Small primes?");
                    sleep(200);
                    System.out.println("2: Find out about the nth Small prime? (1-" + Smprimes.size() + ")");
                    sleep(200);
                    System.out.println("3: Go back to the main menu\n");
                    int choice2 = scan.nextInt();
                    System.out.println();
                    if(choice2 == 1){
                        System.out.println("Here are our Small primes:");
                        for(Prime p : Smprimes)System.out.println(p.formula);
                        System.out.println();
                    }else if(choice2 == 2){
                        while(true){
                            System.out.println("Which Small prime would you like to know about? (press -1 to exit)");
                            int choice3 = scan.nextInt();
                            if(choice3 == -1){System.out.println();break;}
                            if(choice3 > Smprimes.size() || choice3 < 1){
                                System.out.println("Invalid choice! Please choose a number between (1-" + Smprimes.size() + ".)\n");
                                continue;
                            }else{
                                System.out.println(Smprimes.get(choice3-1).formula + "\n");
                            }
                        }
                    }else{
                        break;
                    }
                }
            }else if(choice == 2){
                //3 choices in sub-menu
                while(true){
                    System.out.println("Would you like to..");
                    sleep(200);
                    System.out.println("1: See a list of our Large primes?");
                    sleep(200);
                    System.out.println("2: Find out about the nth Large prime? (1-" + Lprimes.size() + ")");
                    sleep(200);
                    System.out.println("3: Go back to the main menu\n");
                    int choice2 = scan.nextInt();
                    System.out.println();
                    if(choice2 == 1){
                        System.out.println("Here are our Large primes:");
                        for(Prime p : Lprimes)System.out.println(p);
                        System.out.println();
                    }else if(choice2 == 2){
                        while(true){
                            System.out.println("Which Large prime would you like to know about? (press -1 to exit)");
                            int choice3 = scan.nextInt();
                            if(choice3 == -1){System.out.println();break;}
                            if(choice3 > Lprimes.size() || choice3 < 1){
                                System.out.println("Invalid choice! Please choose a number between (1-" + Lprimes.size() + ").\n");
                                continue;
                            }else{
                                System.out.println(Lprimes.get(choice3-1) + "\n");
                            }
                        }
                    }else{
                        break;
                    }
                }
            }else if(choice == 3){
                ///3 choices in sub-menu
                while(true){
                    System.out.println("Would you like to..");
                    sleep(200);
                    System.out.println("1: See a list of our Special primes?");
                    sleep(200);
                    System.out.println("2: Find out about the nth Special prime? (1-" + Sprimes.size() + ")");
                    sleep(200);
                    System.out.println("3: Go back to the main menu\n");
                    int choice2 = scan.nextInt();
                    System.out.println();
                    if(choice2 == 1){
                        System.out.println("Here are our Special primes:");
                        for(Prime p : Sprimes)System.out.println(p);
                        System.out.println();
                    }else if(choice2 == 2){
                        while(true){
                            System.out.println("Which Special prime would you like to know about? (press -1 to exit)");
                            int choice3 = scan.nextInt();
                            if(choice3 == -1){System.out.println();break;}
                            if(choice3 > Sprimes.size() || choice3 < 1){
                                System.out.println("Invalid choice! Please choose a number between (1-" + Sprimes.size() + ")\n");
                                continue;
                            }else{
                                System.out.println(Sprimes.get(choice3-1) + "\n");
                            }
                        }
                    }else{
                        break;
                    }
                }
            }else if(choice == 4){
                //Asks user for any size number, answers using BigInteger library 
                while(true){
                    System.out.println("Which number would you like to test? (press -1 to exit)");
                    BigInteger choice2 = scan.nextBigInteger();
                    if(choice2.intValue() == -1){System.out.println();break;}
                    if(isPrime(choice2)){
                        System.out.println("The number " + choice2 + " is prime!\n");
                    }else{
                        System.out.println("The number " + choice2 + " is not prime!\n");
                    }
                }
            }else{
                System.out.print("Goodbye!");
                break;
            }
            System.out.println("\n");
        }

    }
}
