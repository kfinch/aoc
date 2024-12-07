package aoc2018;

import util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8 {

    public static void main (String args[]) {
        try {
            day8();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void day8() {
        Scanner sc = FileUtils.getScanner("2018/day8input.txt");

        Node head = buildTree(sc);
        System.out.println("Sum: " + sumTree(head));
        System.out.println("Value: " + sumValues(head));
    }

    private static Node buildTree(Scanner sc) {
        int nodeCount = sc.nextInt();
        int metadataCount = sc.nextInt();
        Node result = new Node(nodeCount, metadataCount);
        for (int i=0; i<nodeCount; i++) {
            result.nodes.add(buildTree(sc));
        }
        for (int i=0; i<metadataCount; i++) {
            result.metadata.add(sc.nextInt());
        }
        return result;
    }

    private static int sumTree(Node head) {
        int sum = 0;
        sum += head.nodes.stream().reduce(0, (s, n) -> s + sumTree(n), (i, j) -> i + j);
        sum += head.metadata.stream().mapToInt(Integer::intValue).sum();
        return sum;
    }

    private static int sumValues(Node head) {
        for (Node n : head.nodes) {
            sumValues(n);
        }
        if (head.nodes.isEmpty()) {
            head.value = head.metadata.stream().mapToInt(Integer::intValue).sum();
        } else {
            head.value = 0;
            for (int index : head.metadata) {
                index--; // so we're indexed from zero
                if (index >= head.nodes.size()) {
                    continue;
                }
                head.value += head.nodes.get(index).value;
            }
        }
        return head.value;
    }



    private static class Node {
        int nodeCount, metadataCount;
        List<Node> nodes;
        List<Integer> metadata;
        int value = 0; // for use in part 2
        private Node(int nodeCount, int metadataCount) {
            this.nodeCount = nodeCount;
            this.metadataCount = metadataCount;
            this.nodes = new ArrayList<>(nodeCount);
            this.metadata = new ArrayList<>(metadataCount);
        }
    }
}
