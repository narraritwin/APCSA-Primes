import java.util.Scanner;
import java.util.ArrayList;
import java.lang.*;
import java.io.*;

public class Regression {
    private static ArrayList<Integer> primes = new ArrayList<>();
    private static ArrayList<Double> weights = new ArrayList<>();
    private static long div = 0L;

    public static double getVal(ArrayList<Double> w, int x) {
        double val = 0;
        for (int i = 0; i < w.size(); i++) {
            val += w.get(i) * Math.pow(x, w.size() - i - 1);
        }
        return val;
    }

    public static double getLoss(ArrayList<Double> w) {
        double loss = 0;
        for(int i = 0; i < primes.size(); i++) {
            loss += Math.pow((getVal(w, i) - primes.get(i)), 2);
        }
        return loss;
    }

    public static ArrayList<Double> getGrad(ArrayList<Double> w) {
        ArrayList<Double> grad = new ArrayList<>();
        for(int i = 0; i < w.size(); i++) grad.add(0.0);
        for(int i = 0; i < primes.size(); i++) { 
            double temp = 2.0*(getVal(w, i) - primes.get(i)) / div;
            for(int j = 0; j < w.size(); j++) {
                grad.set(j, grad.get(j) + temp * Math.pow(i, w.size() - j - 1));
            }
        }
        return grad;
    }

    public static boolean descent(ArrayList<Double> weights, Double learn, int iterations){
        double lowLoss = getLoss(weights);

        // Loss : (y - y')^2, where y = f(x), y' = f(x')
        // Grad : 2(y - y') / div, where y = f(x), y' = f(x')

        for(int i = 0; i < iterations; ++i){
            ArrayList<Double> grad = getGrad(weights);
            for(int j = 0; j < weights.size(); ++j){
                weights.set(j, weights.get(j) - learn * grad.get(j));
            }
            if(getLoss(weights) > lowLoss) {
                return false;
            }else{
                lowLoss = getLoss(weights);
            }
        }
        return true;
    }

    public static void gradDescent(long NumOfPrimes) {
        primes.clear(); weights.clear();
        long degree = NumOfPrimes / 1000 + 3;
        int currentNum = 2;
        while(primes.size() != NumOfPrimes) {
            if(Generator.isPrime(currentNum)) {
                primes.add(currentNum);
            }
            currentNum++;
        }
        div = (long)Math.pow(NumOfPrimes, degree);

        long low = 1L, high = 100000000000L;

        System.out.print("[");
        for(int i = 0; i < degree; i++) weights.add(1.0);
        while(low < high) {
            System.out.print("-");
            for(int i = 0; i < degree; i++) weights.set(i, 1.0);

            boolean work = descent(weights, (high+low+1.0) / 2000000000000.0, 10000);

            //System.out.println("Currently testing learning rate : " + (low+high)/2 + "\n" + ((work) ? ("It worked and had accuracy " + getLoss(weights)) : ("It did not work :(")));
            //System.out.println();
            if(work) low = (low+high+1)/2;
            else high = (low+high+1)/2-1;
        }
        System.out.print("-");

        for(int i = 0; i < degree; i++) weights.set(i, 1.0);
        descent(weights, low / 2000000000000.0, 100000);

        System.out.println("]");

        System.out.print("The BEST polynomial is : " + String.format("%.5f", weights.get(0)) + "x^" + (degree-1));
        for(int i = 1; i < degree; i++) {
            System.out.print(" + " + String.format("%.5f", weights.get(i)));
            if(degree-i-1 != 1 && degree-i-1 != 0) System.out.print("x^" + (degree-i-1));
            else if(degree-i-1 == 1) System.out.print("x");
        }
        System.out.println();
    }

    public static void main(String[] args) {
    }
}
