package siakod.task6.bratyshev_t_d;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static String generateString(int stringLength) {
        int leftLimit = 97;
        int rightLimit = 100;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public static int simpleSearch(String string, String substring) {
        int s = string.length();
        int k = substring.length();
        int i = 0, j = 0;

        while (i <= s - k && j < k) {
            if (string.charAt(i + j) == substring.charAt(j)) {
                j++;
            } else {
                i++;
                j = 0;
            }
        }
        if (j == k) {
            return i;
        }
        return - 1;
    }

    private static int[] createOffSetTable(String substring) {
        int[] offSet = new int[128];
        Arrays.fill(offSet, -1);
        for (int i = 0; i < substring.length(); i++) {
            offSet[substring.charAt(i)] = i;
        }
        return offSet;
    }

    private static int[] createSuffix(String substring) {
        int[] suffix = new int[substring.length() + 1];
        for (int i = 0; i < substring.length(); i++) {
            suffix[i] = substring.length();
        }
        suffix[substring.length()] = 1;
        for (int i = substring.length() - 1; i >= 0 ; i--) {
            for (int n = i; n < substring.length(); n++) {
                String str = substring.substring(n);
                for (int j = n - 1; j >= 0 ; j--) {
                    String p = substring.substring(j, str.length());
                    if (p.equals(str)) {
                        suffix[i] = n - 1;
                        n = substring.length();
                        break;
                    }
                }
            }
        }
        return suffix;
    }
    public static int boyerMooreSearch(String string, String substring) {
        int[] offSet = createOffSetTable(substring);
        int[] suffix = createSuffix(substring);
        int t = 0;
        int last = substring.length() - 1;
        while (t < string.length() - last) {
            int p = last;
            while (p >= 0 && string.charAt(t + p) == substring.charAt(p)) {
                p--;
            }
            if (p == -1) {
                return t;
            }
            t += Math.max(p - offSet[string.charAt(t + p)], suffix[p + 1]);
        }
        return -1;
    }


    public static void main(String[] args) {
        String string = generateString(100000);
        String substring = "dba";
        simpleSearch(string, substring);

//        generateString(10);

        System.out.println(simpleSearch(string, substring));
        System.out.println(boyerMooreSearch(string, substring));

    }
}
