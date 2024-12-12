package aoc2024;

import util.FileUtils;

import java.util.HashSet;
import java.util.Set;

public class Day10 {

    public static void main (String args[]) {
        System.out.println("Puzzle A: " + puzzleA());
        System.out.println("Puzzle B: " + puzzleB());
    }

    public static String puzzleA() {
        int[][] input = readAndParseMap();
        int score = 0;
        for (int i=0; i<input.length; i++) {
            for (int j=0; j<input[0].length; j++) {
                if (input[i][j] == 0) {
                    Set<String> summitsReached = new HashSet<>();
                    pathsFrom(input, i, j, 1, summitsReached);
                    score += summitsReached.size();
                }
            }
        }

        return "" + score;
    }

    public static String puzzleB() {
        return "";
    }

    private static void pathsFrom(int[][] map, int i, int j, int next, Set<String> coordsReached) {
        if (next == 10) {
            coordsReached.add(i + "," + j);
            return;
        }

        if (i >= 1 && map[i-1][j] == next) {
            pathsFrom(map, i-1, j, next+1, coordsReached);
        }
        if (i < map.length-1 && map[i+1][j] == next) {
            pathsFrom(map, i+1, j, next+1, coordsReached);
        }
        if (j >= 1 && map[i][j-1] == next) {
            pathsFrom(map, i, j-1, next+1, coordsReached);
        }
        if (j < map[0].length-1 && map[i][j+1] == next) {
            pathsFrom(map, i, j+1, next+1, coordsReached);
        }
    }

    private static int[][] readAndParseMap() {
        char[][] charMap = FileUtils.getCharGrid("2024/day10input.txt");
        int[][] result = new int[charMap.length][charMap[0].length];
        for (int i=0; i<charMap.length; i++) {
            for (int j=0; j<charMap[0].length; j++) {
                result[i][j] = Character.digit(charMap[i][j], 10);
            }
        }
        return result;
    }

}
