package aoc2024;

import util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {

    public static void main (String args[]) {
        System.out.println("Puzzle A: " + puzzleA());
        System.out.println("Puzzle B: " + puzzleB());
    }

    public static String puzzleA() {
        List<Eq> eqs = getEquations();
        long sum = 0;

        for (Eq eq : eqs) {
            if (hasSolve(eq)) {
                sum += eq.target;
            }
        }

        return "" + sum;
    }

    public static String puzzleB() {
        List<Eq> eqs = getEquations();
        long sum = 0;

        for (Eq eq : eqs) {
            if (hasSolveB(eq)) {
                sum += eq.target;
            }
        }

        return "" + sum;
    }

    private static List<Eq> getEquations() {
        List<String> lines = FileUtils.getLineList("2024/day7input.txt");
        return lines.stream().map(l -> {
            Eq eq = new Eq();
            String[] targetAndOps = l.split(":");
            eq.target = Long.parseLong(targetAndOps[0]);
            String[] ops = targetAndOps[1].split(" ");
            eq.ops = new ArrayList<>();
            for (String opString : ops) {
                if (opString.isEmpty()) { continue; }
                eq.ops.add(Long.parseLong(opString));
            }
            return eq;
        }).collect(Collectors.toList());
    }

    private static boolean hasSolve(Eq eq) {
        return hasSolveRecurse(eq, eq.ops.get(0), 1);
    }

    private static boolean hasSolveRecurse(Eq eq, long valSoFar, int i) {
        if (i == eq.ops.size()) {
            return eq.target == valSoFar;
        }

        try {
            long newVal = Math.addExact(valSoFar, eq.ops.get(i));
            if(hasSolveRecurse(eq, newVal, i+1)) {
                return true;
            }
        } catch (ArithmeticException e) {}

        try {
            long newVal = Math.multiplyExact(valSoFar, eq.ops.get(i));
            if(hasSolveRecurse(eq, newVal, i+1)) {
                return true;
            }
        } catch (ArithmeticException e) {}

        return false;
    }

    private static boolean hasSolveB(Eq eq) {
        return hasSolveRecurseB(eq, eq.ops.get(0), 1);
    }

    private static boolean hasSolveRecurseB(Eq eq, long valSoFar, int i) {
        if (i == eq.ops.size()) {
            return eq.target == valSoFar;
        }

        try {
            long newVal = Math.addExact(valSoFar, eq.ops.get(i));
            if(hasSolveRecurseB(eq, newVal, i+1)) {
                return true;
            }
        } catch (ArithmeticException e) {}

        try {
            long newVal = Math.multiplyExact(valSoFar, eq.ops.get(i));
            if(hasSolveRecurseB(eq, newVal, i+1)) {
                return true;
            }
        } catch (ArithmeticException e) {}

        String concat = String.valueOf(valSoFar) + String.valueOf(eq.ops.get(i));
        try {
            if (hasSolveRecurseB(eq, Long.parseLong(concat), i+1)) {
                return true;
            }
        } catch (NumberFormatException e) {}

        return false;
    }

    private static class Eq {
        long target;
        List<Long> ops;
    }

}
