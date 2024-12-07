package aoc2024;

import util.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class Day2 {

    public static void main (String args[]) {
        System.out.println("Puzzle 1");
        puzzle1();
        System.out.println("Puzzle 2");
        puzzle2();
    }

    public static void puzzle1() {
        List<List<String>> tokens = FileUtils.getTokensByLine("2024/day2input.txt");
        int safeCount = 0;
        for (List<String> line : tokens) {
            if (isSafe(line)) {
                safeCount += 1;
            }
        }
        System.out.println(safeCount);
    }

    private static boolean isSafe(List<String> vals) {
        ValType type = ValType.UNKNOWN;
        Integer prev = null;
        for (String val : vals) {
            Integer intVal = Integer.parseInt(val);
            if (prev != null) {
                int diff = intVal - prev;
                if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                    return false;
                }
                if (diff > 0 && type == ValType.DECREASING) {
                    return false;
                }
                if (diff < 0 && type == ValType.INCREASING) {
                    return false;
                }
                type = diff > 0 ? ValType.INCREASING : ValType.DECREASING;
            }
            prev = intVal;
        }
        return true;
    }

    private enum ValType { INCREASING, UNKNOWN, DECREASING };

    public static void puzzle2() {
        List<List<String>> tokens = FileUtils.getTokensByLine("2024/day2input.txt");
        int safeCount = 0;
        outerLoop: for (List<String> line : tokens) {
            if (isSafe(line)) {
                safeCount += 1;
            } else {
                for (int i=0; i<line.size(); i++) {
                    List<String> lineWithDelete = new ArrayList<>(line);
                    lineWithDelete.remove(i);
                    if (isSafe(lineWithDelete)) {
                        safeCount += 1;
                        continue outerLoop;
                    }
                }
            }
        }
        System.out.println(safeCount);
    }

}
