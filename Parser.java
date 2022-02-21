import java.util.*;
import java.io.*;
import java.net.URL;

public class Parser {
    public static String joinStringTokenizer(StringTokenizer st) {
        String s = "";
        while (st.hasMoreTokens()) s += st.nextToken() + " ";
        if (s.length() > 0) s = s.substring(0, s.length()-1);
        return s;
    }
    public static ArrayList<Prime> parse(String _url) {
        try {
            URL url = new URL(_url);
            Scanner sc = new Scanner(url.openStream());

            // There are 31 lines before the primes start
            for (int i = 0; i < 31; ++i) sc.nextLine();
            // Perhaps something with Scanner.skip might work better though

            // The first 4999 are formatted nicely for us to work with
            // (after that, we require manual formatting for Java to parse ir properly)
            ArrayList<Prime> primes = new ArrayList<>();
            // while (primes.size() < /* 4999 */ 5050) {
            while (sc.hasNext()) {
                String s = sc.nextLine();

                // Too many spaces corresponds to the line not containing a prime
                if (s.startsWith("      ")) continue;

                // Remove leading / trailing whitespace
                // and turn multiple spaces into one space
                s = s.trim().replace("\\s+", " ");

                // The end of the primes
                if (s.contains("---")) break;

                StringTokenizer st = new StringTokenizer(s);
                // System.out.println("Current line: \"" + s + "\"");

                st.nextToken();
                // If we needed to extract the rank:
                // String srank = st.nextToken();
                // if (Character.isAlphabetic(srank.charAt(srank.length()-1))) {
                //     srank = srank.substring(0, srank.length() - 1);
                // }
                // int rank = Integer.parseInt(srank);

                String formula;
                if (s.contains("other")) {
                    formula = joinStringTokenizer(st);
                    s = sc.nextLine().trim().replace("\\s+", " ");
                    st = new StringTokenizer(s);
                } else {
                    formula = st.nextToken();
                }
                while (formula.endsWith("\\")) {
                    s = sc.nextLine().trim().replace("\\s+", " ");
                    st = new StringTokenizer(s);
                    formula = formula.substring(0, formula.length()-1) + st.nextToken();
                }

                if (!st.hasMoreTokens()) {
                    s = sc.nextLine().trim().replace("\\s+", " ");
                    st = new StringTokenizer(s);
                }
                int digitCount = Integer.parseInt(st.nextToken());
                st.nextToken();
                // If we wanted the author:
                // String _who = st.nextToken();
                int year = Integer.parseInt(st.nextToken());
                String comment = joinStringTokenizer(st);
                primes.add(new Prime(formula, digitCount, year, comment));
                // Not using `rank` in favor of a static variable (see Prime.java)
            }

            sc.close();
            return primes;
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
