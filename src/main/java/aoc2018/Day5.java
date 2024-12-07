package aoc2018;

import util.FileUtils;

import java.util.Scanner;

public class Day5 {

    private static final char EMPTY = '.';

    public static void main (String args[]) {
        try {
            day5_1();
            day5_2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void day5_1() {
        Scanner sc = FileUtils.getScanner("2018/day5input.txt");
        String polymer = sc.next();
        char[] elements = polymer.toCharArray();

        int elementsLeft = reactChain(elements);

        System.out.println("#1 : " + elementsLeft + " elements left.");
    }

    private static void day5_2() {
        Scanner sc = FileUtils.getScanner("2018/day5input.txt");
        String polymer = sc.next();
        char[] elements = polymer.toCharArray();
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        for (char toRemove : alphabet) {
            char[] elementsWithoutLetter = removeChar(elements, toRemove);
            int elementsLeft = reactChain(elementsWithoutLetter);
            System.out.println("Without '" + toRemove + "' we're left with " + elementsLeft + " elements.");
        }
    }

    private static char[] removeChar(char[] elements, char toRemove) {
        char[] result = new char[elements.length];
        for (int i=0; i<elements.length; i++) {
            if (Character.toLowerCase(elements[i]) == toRemove) {
                result[i] = EMPTY;
            } else {
                result[i] = elements[i];
            }
        }
        return result;
    }

    private static int reactChain(char[] elements) {
        boolean hadReaction;
        do {
            hadReaction = false;

            char prev = ' ';
            int prevIndex = -1;
            int i=0;
            while (i<elements.length) {
                char curr = elements[i];
                if (curr == EMPTY) {
                    i++;
                    continue;
                }

                if (reacts(prev, curr)) {
                    elements[prevIndex] = EMPTY;
                    elements[i] = EMPTY;
                    hadReaction = true;
                }
                prev = elements[i];
                prevIndex = i;
                i++;
            }

        } while (hadReaction);

        int elementsLeft = 0;
        for (char c : elements) {
            if (c != EMPTY) {
                elementsLeft++;
            }
        }

        return elementsLeft;
    }

    private static boolean reacts (char c1, char c2) {
        return (!(c1 == c2)) && (Character.toLowerCase(c1) == Character.toLowerCase(c2));
    }
}
