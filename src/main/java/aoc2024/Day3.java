package aoc2024;

import util.FileUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public static void main (String args[]) {
        System.out.println("Puzzle 1");
        puzzle1();
        System.out.println("Puzzle 2");
        puzzle2();
    }

    public static void puzzle1() {
        StringBuilder input = FileUtils.getAsStringBuilder("2024/day3input.txt");
        Pattern pattern = getMulPattern();
        Matcher matcher = pattern.matcher(input);
        int sum = 0;
        while (matcher.find()) {
            sum += Integer.valueOf(matcher.group(1)) * Integer.valueOf(matcher.group(2));
        }
        System.out.println(sum);
    }

    public static void puzzle2() {
        StringBuilder input = FileUtils.getAsStringBuilder("2024/day3input.txt");
        Pattern mulPattern = getMulPattern();
        Pattern execPattern = Pattern.compile("do\\(\\)(.*?)don't\\(\\)");
        StringBuilder updatedInput = new StringBuilder().append("do()").append(input).append("don't()");

        int sum = 0;
        Matcher execMatcher = execPattern.matcher(updatedInput);
        while (execMatcher.find()) {
            String subSeq = execMatcher.group(1);
            Matcher mulMatcher = mulPattern.matcher(subSeq);
            while (mulMatcher.find()) {
                sum += Integer.valueOf(mulMatcher.group(1)) * Integer.valueOf(mulMatcher.group(2));
            }
        }

        System.out.println(sum);
    }

    private static Pattern getMulPattern()
    {
        return Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    }

}
