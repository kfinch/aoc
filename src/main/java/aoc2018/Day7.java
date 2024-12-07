package aoc2018;

import util.FileUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {

    public static void main (String args[]) {
        try {
            day7_1();
            day7_2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void day7_1() {
        Scanner sc = FileUtils.getScanner("2018/day7input.txt");

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
        System.out.println();
    }

    private static void day7_2() {
        Scanner sc = FileUtils.getScanner("2018/day7input.txt");

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

        int totalWorkers = 5;
        List<Task> inProgress = new ArrayList<>();
        int time = 0;

        while ((!ready.isEmpty()) || inProgress.size() > 0) {
            if (inProgress.size() == totalWorkers || ready.isEmpty()) {
                // if no one is available for tasking or no tasks are ready to start, 'step' forward in time
                time++;
                Iterator<Task> taskIter = inProgress.iterator();
                while (taskIter.hasNext()) {
                    Task task = taskIter.next();
                    task.timeLeft--;
                    if (task.timeLeft == 0) {
                        // finish this node, unblock, etc
                        taskIter.remove();
                        Node done = task.node;
                        for (Node n : done.blocking) {
                            n.prereqs.remove(done);
                            if (n.prereqs.isEmpty()) {
                                ready.add(n);
                            }
                        }
                    }
                }
                continue;
            }

            Node next = ready.stream().min(Comparator.comparing(Node::getId)).get();
            inProgress.add(new Task(next));
            ready.remove(next);
        }

        System.out.println(time);
    }

    private static int getTimeForChar(char c) {
        return 61 + c - 'A';
    }

    private static class Task {
        int timeLeft;
        Node node;
        private Task(Node node) {
            this.node = node;
            this.timeLeft = getTimeForChar(node.id);
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
