package aoc2018;

import util.FileUtils;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day1 {

    public static void main (String args[])
    {
        try {
            day1_1();
            day1_2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void day1_1() throws Exception
    {
        Scanner freqScanner = FileUtils.getScanner("2018/day1freqs.txt");

        int currFreq = 0;
        while (freqScanner.hasNextInt())
        {
            currFreq += freqScanner.nextInt();
        }

        System.out.println("Day1 #1 -> " + currFreq);
    }

    private static void day1_2() throws Exception
    {
        Scanner freqScanner = FileUtils.getScanner("2018/day1freqs.txt");

        Set<Integer> found = new HashSet<>();
        int currFreq = 0;
        found.add(currFreq);

        while(true) {
            if (!freqScanner.hasNextInt()) {
                freqScanner = FileUtils.getScanner("2018/day1freqs.txt");
            }
            currFreq += freqScanner.nextInt();
            boolean notSeen = found.add(currFreq);
            if (!notSeen) {
                break;
            }
        }

        System.out.println("Day1 #2 -> " + currFreq);
    }

}
