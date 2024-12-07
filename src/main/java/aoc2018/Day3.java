package aoc2018;

import util.FileUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public static void main (String args[]) {
        try {
            day3();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void day3() throws Exception {
        Scanner sc = FileUtils.getScanner("2018/day3input.txt");
        int arraySize = 1100;

        // each element is 0 for unfilled, ID for claimed by one, -1 for claimed by many
        int claimsArray[][] = new int[arraySize][arraySize];
        Arrays.stream(claimsArray).forEach(a -> Arrays.fill(a, 0));

        Pattern capturePattern = Pattern.compile("#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+).*");

        Set<Integer> nonOverlappingIds = new HashSet<>();
        while (sc.hasNext()) {
            String claimText = sc.nextLine();
            Matcher claimMatcher = capturePattern.matcher(claimText);
            claimMatcher.matches();
            int elfId = Integer.valueOf(claimMatcher.group(1));
            int xOffset = Integer.valueOf(claimMatcher.group(2));
            int yOffset = Integer.valueOf(claimMatcher.group(3));
            int xSize = Integer.valueOf(claimMatcher.group(4));
            int ySize = Integer.valueOf(claimMatcher.group(5));

            nonOverlappingIds.add(elfId);
            for (int x = xOffset; x < xOffset+xSize; x++) {
                for (int y = yOffset; y < yOffset+ySize; y++) {
                    int currentClaim = claimsArray[x][y];
                    if (currentClaim == 0) {
                        claimsArray[x][y] = elfId;
                    } else if (currentClaim > 0) {
                        claimsArray[x][y] = -1;
                        nonOverlappingIds.remove(elfId);
                        nonOverlappingIds.remove(currentClaim);
                    } else if (currentClaim == -1) {
                        nonOverlappingIds.remove(elfId);
                    }
                }
            }
        }

        int multiClaimCount = 0;
        int noOverlapId = 0;
        for (int x=0; x<arraySize; x++) {
            for (int y=0; y<arraySize; y++) {
                int claimId = claimsArray[x][y];
                if (claimId == -1) {
                    multiClaimCount++;
                } else if (claimId > 0) {
                    noOverlapId = claimId;
                }
            }
        }

        System.out.println("Day3 #1 -> " + multiClaimCount);
        System.out.println("Day3 #2 -> " + nonOverlappingIds.toString());
    }

}
