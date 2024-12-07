package aoc2024;

import util.FileUtils;
import util.FreqMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Day1 {

    public static void main(String args[]) {
        puzzle1();
        puzzle2();
    }

    public static void puzzle1() {
        List<String> tokens = FileUtils.getTokenList("2024/day1input.txt");
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        Iterator<String> tokenIter = tokens.iterator();
        while (tokenIter.hasNext()) {
            first.add(Integer.parseInt(tokenIter.next()));
            second.add(Integer.parseInt(tokenIter.next()));
        }

        Collections.sort(first);
        Collections.sort(second);

        int sum = 0;
        for (int i=0; i<first.size(); i++) {
            sum += Math.abs(first.get(i) - second.get(i));
        }
        System.out.println(sum);
    }

    public static void puzzle2() {
        List<String> tokens = FileUtils.getTokenList("2024/day1input.txt");
        List<Integer> first = new ArrayList<>();
        FreqMap<Integer> second = new FreqMap<>();
        Iterator<String> tokenIter = tokens.iterator();
        while (tokenIter.hasNext()) {
            first.add(Integer.parseInt(tokenIter.next()));
            second.put(Integer.parseInt(tokenIter.next()));
        }

        int sum = 0;
        for (int i=0; i<first.size(); i++) {
            int num = first.get(i);
            sum += num * second.get(num);
        }
        System.out.println(sum);
    }

}
