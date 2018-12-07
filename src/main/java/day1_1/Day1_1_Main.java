package day1_1;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day1_1_Main {

    private static final ClassLoader loader = Day1_1_Main.class.getClassLoader();

    public static void main (String args[]) {
        try {
            day1_1();
            day1_2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void day1_1() throws Exception
    {
        InputStream freqInputStream = loader.getResourceAsStream("day1freqs.txt");
        Scanner freqScanner = new Scanner(freqInputStream);

        int currFreq = 0;
        while (freqScanner.hasNextInt())
        {
            currFreq += freqScanner.nextInt();
        }

        System.out.println("Day1 #1 -> " + currFreq);
    }

    private static void day1_2() throws Exception
    {
        InputStream freqInputStream = loader.getResourceAsStream("day1freqs.txt");
        Scanner freqScanner = new Scanner(freqInputStream);

        Set<Integer> found = new HashSet<>();
        int currFreq = 0;
        found.add(currFreq);

        while(true) {
            if (!freqScanner.hasNextInt()) {
                freqInputStream = loader.getResourceAsStream("day1freqs.txt");
                freqScanner = new Scanner(freqInputStream);
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
