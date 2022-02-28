import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;

public class ReadPrimes{
    public static void main(String[] args) throws Exception{ 
        
        //This is an alternate method of reading primes from database using Scanner class.
        
        ArrayList<ArrayList<String>> primes = new ArrayList<ArrayList<String>>();
        URL p = new URL("https://primes.utm.edu/primes/lists/all.txt");
        Scanner s = new Scanner(p.openStream());
        int cnt = 0;
        while(s.hasNextLine()){cnt++;
            String ss = s.nextLine();
            if(cnt>31){
                //System.out.println("\t"+ ss);
                ArrayList<String> curr = new ArrayList<String>(Arrays.asList(ss.split("\\s+")));
                if(!curr.get(0).equals(""))break;
                for(int i = curr.size()-1; i >= 0; --i)if(curr.get(i).isBlank()||curr.get(i).length()==0)curr.remove(i);
                String Rank = rem(curr.get(0));
                String des = "";
                if(isNum(Rank, false)){
                    curr.set(0, Rank);
                    while(curr.size()>0 && !isNum(curr.get(curr.size()-1), true)){
                        des = curr.get(curr.size()-1) + " " + des;
                        curr.remove(curr.size()-1);
                    }
                    curr.add(des.trim());
                    primes.add(curr);
                }else primes.get(primes.size()-1).add(conv(curr));
                if(primes.get(primes.size()-1).get(primes.get(primes.size()-1).size()-1).length()==0)primes.get(primes.size()-1).remove(primes.get(primes.size()-1).size()-1);
                //System.out.println(primes.get(primes.size()-1));
            }
        }
        s.close();// <- this is important, very important, very very important

        //Interacting with the User
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello!");
        sleep(1500);
        System.out.println("This program contains 5000 of the largest prime numbers.");
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
    
    public static String conv(ArrayList<String> curr){
        String des = "";
        for(String i : curr)des += i + " ";
        return des.trim();
    }

    public static String rem(String s){
        if(s.length() > 5) return s;
        boolean hasDigit = false;
        for(int i = 0; i < s.length(); ++i){
            if(s.charAt(i) > '0' && s.charAt(i) < '9'){
                hasDigit = true;
                break;
            }
        }
        if(!hasDigit) return s;
        if(s.charAt(0)<'0' || s.charAt(0)>'9') return s.substring(1);
        else if(s.charAt(s.length()-1)<'0' || s.charAt(s.length()-1)>'9') return s.substring(0, s.length()-1);
        return s;
    }

    public static boolean isNum(String s, boolean year){
        if(year) return s.matches("\\d{4}");
        else return s.matches("\\d+");
    }
    
    public static void sleep(int time){
        try{
            TimeUnit.MILLISECONDS.sleep(time);
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}
