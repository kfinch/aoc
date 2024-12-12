package aoc2024;

import util.FileUtils;
import util.MultiSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11 {

    public static void main (String args[]) {
        System.out.println("Puzzle A: " + puzzleA());
        System.out.println("Puzzle B: " + puzzleB());
    }

    public static String puzzleA() {
        List<Long> input = getInput();
        for (int i=0; i<25; i++) {
            input = step(input);
        }
        return "" + input.size();
    }

    public static String puzzleB() {
        List<Long> input = getInput();
        MultiSet<Long> mapInput = new MultiSet<>();
        for (Long val : input) {
            mapInput.add(val, 1);
        }
        for (int i=0; i<75; i++) {
            System.out.println("Step " + i);
            mapInput = step(mapInput);
        }
        return "" + mapInput.count();
    }

    private static List<Long> step(List<Long> input) {
        List<Long> result = new ArrayList<>();
        for (Long l : input) {
            String s = String.valueOf(l);
            if (l == 0) {
                result.add(1L);
            } else if (s.length() % 2 == 0) {
                result.add(Long.parseLong(s.substring(0, s.length()/2)));
                result.add(Long.parseLong(s.substring(s.length()/2)));
            } else {
                result.add(l * 2024L);
            }
        }
        return result;
    }

    private static MultiSet<Long> step(MultiSet<Long> input) {
        MultiSet<Long> result = new MultiSet<>();
        for (Map.Entry<Long, Long> entry : input.entries()) {
            Long l = entry.getKey();
            Long count = entry.getValue();
            String s = String.valueOf(l);
            if (l == 0) {
                result.add(1L, count);
            } else if (s.length() % 2 == 0) {
                result.add(Long.parseLong(s.substring(0, s.length()/2)), count);
                result.add(Long.parseLong(s.substring(s.length()/2)), count);
            } else {
                result.add(l * 2024L, count);
            }
        }
        return result;
    }

    private static List<Long> getInput() {
        return FileUtils.getTokenList("2024/day11input.txt").stream().map(Long::parseLong).collect(Collectors.toList());
    }

}
