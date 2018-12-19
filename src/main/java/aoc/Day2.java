package aoc;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Scanner;

public class Day2 {

    public static void main (String args[])
    {
        try {
            day2_1();
            day2_2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void day2_1() throws Exception
    {
        Scanner sc = Utils.getScanner("day2codes.txt");
        int twoCount = 0;
        int threeCount = 0;
        while (sc.hasNext()) {
            String s = sc.next();
            char[] chars = s.toCharArray();
            Multiset<Character> charCounts = HashMultiset.create();
            for (char c : chars) {
                charCounts.add(c);
            }

            boolean hasTwo = charCounts.entrySet().stream()
                    .anyMatch(e -> e.getCount() == 2);
            if (hasTwo) {
                twoCount++;
            }

            boolean hasThree = charCounts.entrySet().stream()
                    .anyMatch(e -> e.getCount() == 3);
            if (hasThree) {
                threeCount++;
            }
        }

        System.out.println("Day2 #1 -> " + (twoCount*threeCount));
    }

    private static void day2_2() throws Exception
    {
        String[] codes = Utils.getTokenList("day2codes.txt").toArray(new String[0]);

        for (int i=0; i<codes.length; i++) {
            String codeI = codes[i];
            for (int j=i+1; j<codes.length; j++) {
                String codeJ = codes[j];
                int oneDiff = oneDiff(codeI, codeJ);
                if (oneDiff != -1) {
                    System.out.println("Day2 #2 -> " + codeI + " " + codeJ + " @" + oneDiff);
                }
            }
        }
    }

    // returns index of one difference, or -1 if there is more than one difference
    // assumes strings same length and not the same
    private static int oneDiff(String s1, String s2) {
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();

        int lastDiffAt = -1;
        for (int i=0; i<chars1.length; i++) {
            if (chars1[i] != chars2[i]) {
                if (lastDiffAt != -1) {
                    return -1;
                }
                lastDiffAt = i;
            }
        }

        return lastDiffAt;
    }

}
