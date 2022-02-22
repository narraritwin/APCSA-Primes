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

    public static ArrayList<Prime> parse1(String _url) {
        ArrayList<Prime> primes = new ArrayList<>();

        try {
            URL url = new URL(_url);
            Scanner sc = new Scanner(url.openStream());

            // There are 31 lines before the primes start
            for (int i = 0; i < 31; ++i) sc.nextLine();
            // Perhaps something with Scanner.skip might work better though

            // The first 4999 are formatted nicely for us to work with
            // (after that, we require manual formatting for Java to parse ir properly)
            while (sc.hasNext()) {
                String s = sc.nextLine();

                // Too many spaces corresponds to the line not containing a prime
                // But that usually means it contains sonme information about the previous prime
                if (s.startsWith("      ")) {
                    if (primes.get(primes.size()-1).comment.length() == 0) {
                        primes.get(primes.size()-1).comment = s.trim();
                    } else {
                        primes.get(primes.size()-1).comment += " " + s.trim();
                    }
                    continue;
                }

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
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return primes;
    }

    public static ArrayList<Prime> parse2(String _url) {
        ArrayList<Prime> primes = new ArrayList<>();

        try {
            URL url = new URL(_url);
            Scanner sc = new Scanner(url.openStream());
            // First 491 lines have no primes; just information
            for (int i = 0; i < 491; ++i) sc.nextLine();

            while (sc.hasNextLine()) {
                String sa = sc.nextLine(); // Value of a
                if (!sa.startsWith("    <td align=\"middle\" width=\"110\" height=\"19\">")) continue;

                // There's one edge case that's annoying to deal with
                if (sa.length() == 47) {
                    sc.nextLine();
                    sc.nextLine();
                    String s = sc.nextLine().trim();
                    sa += s.substring(0, s.length()-6);
                    sc.nextLine();
                    sa += sc.nextLine();
                }
                String sb = sc.nextLine(); // Value of b
                String sd = sc.nextLine(); // Number of digits

                sa = sa.substring(47, sa.length()-5).replace("&nbsp;", "").trim();
                sb = sb.substring(46, sb.length()-5).replace("&nbsp;", "").trim();
                sd = sd.substring(47, sd.length()-5).replace("&nbsp;", "").trim();

                int a = Integer.parseInt(sa);
                int b = Integer.parseInt(sb);
                int d = Integer.parseInt(sd);
                String formula = String.format("%d^%d+%d^%d", a, b, b, a);
                // Or: String formula = a+"^"+b+"+"+b+"^"+a;

                System.out.println(formula);

                // I'm putting it as 2005 because the parsing is hard
                primes.add(new Prime(formula, d, 2005, "Of the form a^b+b^a"));
                while (sc.hasNextLine() && !sc.nextLine().equals("  <tr>"));
            }
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return primes;
    }
}
