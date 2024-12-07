package aoc2024;

import util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    public static void main (String args[]) {
        System.out.println("Puzzle 1");
        puzzle1();
        System.out.println("Puzzle 2");
        puzzle2();
    }

    public static void puzzle1() {
        Day5Input input = getAndParseInput();
        int sum = 0;
        outerLoop: for (List<Integer> line : input.sequences) {
            // reset rules
            for (Rule rule : input.rules) {
                rule.firstSeen = false;
                rule.secondSeen = false;
            }
            // run through list and check
            for (Integer num : line) {
                for (Rule rule : input.rules) {
                    if (rule.firstSeen) {
                        continue;
                    } else if (rule.secondSeen && num == rule.first) {
                        continue outerLoop;
                    }
                    if (num == rule.first) {
                        rule.firstSeen = true;
                    }
                    if (num == rule.second) {
                        rule.secondSeen = true;
                    }
                }
            }
            int index = line.size() / 2;
            sum += line.get(index);
        }
        System.out.println(sum);
    }

    public static void puzzle2() {
        Day5Input input = getAndParseInput();
        int sum = 0;
        for (List<Integer> line : input.sequences) {
            // reset rules
            for (Rule rule : input.rules) {
                rule.firstSeen = false;
                rule.secondSeen = false;
            }
            // get only rules that apply to this line
            Set<Integer> numSet = new HashSet<>(line);
            List<Rule> applicableRules = input.rules.stream()
                    .filter(r -> numSet.contains(r.first) && numSet.contains(r.second))
                    .collect(Collectors.toList());

            boolean reorderNeeded = false;
            List<Integer> workingLine = new LinkedList<>(line);
            List<Integer> reorderedLine = new ArrayList<>();
            while(!workingLine.isEmpty()) {
                Iterator<Integer> workingLineIter = workingLine.iterator();
                while (workingLineIter.hasNext()) {
                    Integer num = workingLineIter.next();
                    if (applicableRules.stream().allMatch(r -> r.firstSeen || num != r.second)) {
                        workingLineIter.remove();
                        reorderedLine.add(num);
                        for (Rule rule : applicableRules) {
                            if (rule.first == num) {
                                rule.firstSeen = true;
                            }
                        }
                    } else {
                        reorderNeeded = true;
                    }
                }
            }

            if (reorderNeeded) {
                int index = reorderedLine.size() / 2;
                sum += reorderedLine.get(index);
            }
        }
        System.out.println(sum);
    }

    private static Day5Input getAndParseInput() {
        List<String> lines = FileUtils.getLineList("2024/day5input.txt");
        Day5Input result = new Day5Input();
        boolean reachedBreak = false;
        for(String line : lines) {
            if (line.isEmpty()) {
                reachedBreak = true;
                continue;
            }
            if (reachedBreak) {
                result.sequences.add(Arrays.stream(line.split(","))
                        .map(Integer::valueOf).collect(Collectors.toList()));
            } else {
                Rule rule = new Rule();
                String[] items = line.split("\\|");
                rule.first = Integer.valueOf(items[0]);
                rule.second = Integer.valueOf(items[1]);
                result.rules.add(rule);
            }
        }
        return result;
    }

    private static class Day5Input {
        List<Rule> rules = new ArrayList<>();
        List<List<Integer>> sequences = new ArrayList<>();
    }

    private static class Rule {
        int first;
        int second;
        boolean firstSeen = false;
        boolean secondSeen = false;
    }

}
