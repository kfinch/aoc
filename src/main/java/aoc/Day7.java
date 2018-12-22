package aoc;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {

    public static void main (String args[]) {
        try {
            day7_1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void day7_1() {
        Scanner sc = Utils.getScanner("day7input.txt");

        // build out nodes
        Map<Character, Node> nodeMap = new HashMap<>();
        Pattern linePattern = Pattern.compile("Step (.) must be finished before step (.) can begin\\.");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Matcher lineMatcher = linePattern.matcher(line);
            lineMatcher.matches();
            char nodeId = lineMatcher.group(2).charAt(0);
            char prereqId = lineMatcher.group(1).charAt(0);

            Node node = nodeMap.computeIfAbsent(nodeId, id -> new Node(id));
            Node prereq = nodeMap.computeIfAbsent(prereqId, id -> new Node(id));
            node.prereqs.add(prereq);
            prereq.blocking.add(node);
        }

        // seed initial ready list
        Set<Node> ready = new HashSet<>();
        for (Node n : nodeMap.values()) {
            if (n.prereqs.isEmpty()) {
                ready.add(n);
            }
        }

        // loop through taking highest priority on ready list and then unblocking as needed, printing answers as we go
        System.out.println();
        while (!ready.isEmpty()) {
            Node next = ready.stream().min(Comparator.comparing(Node::getId)).get();
            System.out.print(next.id);
            for (Node n : next.blocking) {
                n.prereqs.remove(next);
                if (n.prereqs.isEmpty()) {
                    ready.add(n);
                }
            }
            ready.remove(next);
        }
    }

    private static class Node {
        char id;
        boolean done = false;
        Set<Node> prereqs = new HashSet<>();
        Set<Node> blocking = new HashSet<>();

        private Node(char id) {
            this.id = id;
        }

        char getId () { return id; }
    }
}
