package aoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {

    // strategy: draw the minimum box around the elements:
    // then fill in the grid with 'closest' markers
    // any closest marker that touches the edge is infinite

    public static void main (String args[]) {
        try {
            day6_1();
            day6_2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void day6_1() {
        List<Point> points = readPoints();

        // shift all the points so the the lowest x that appears is 0 and same with y
        int minX = points.stream().min(Comparator.comparingInt(p -> p.x)).get().x;
        int maxX = points.stream().max(Comparator.comparingInt(p -> p.x)).get().x;
        int minY = points.stream().min(Comparator.comparingInt(p -> p.y)).get().y;
        int maxY = points.stream().max(Comparator.comparingInt(p -> p.y)).get().y;
        points.forEach(p -> {
            p.x -= minX;
            p.y -= minY;
        });
        maxX -= (minX-1);
        maxY -= (minY-1);

        // seed initial
        Point[] pointArray = points.toArray(new Point[0]);
        Integer[][] grid = new Integer[maxX][maxY];
        for (int i=0; i<pointArray.length; i++) {
            Point p = pointArray[i];
            grid[p.x][p.y] = i+1; // zero reserved for ties
        }

        // fill grid by 'tessellating' id and negative id, using sign to track the layers
        // null means not touched yet, 0 means even distance between two
        boolean unfilled;
        int polarity = 1;
        do {
            unfilled = false;
            for (int x=0; x<maxX; x++) {
                for (int y=0; y<maxY; y++) {
                    Integer val = grid[x][y];
                    if (val != null) {
                        continue;
                    } else {
                        Integer toFill = fill(x, y, grid, maxX, maxY, polarity);
                        if (toFill != null) {
                            unfilled = true;
                            grid[x][y] = toFill;
                        }
                    }
                }
            }
            polarity *= -1;
        } while (unfilled);

        // now tally, and consider any ID touching the edge to extend infinitely
        for (int x=0; x<maxX; x++) {
//            System.out.println();
            for (int y=0; y<maxY; y++) {
                int curr = grid[x][y];
//                System.out.print(curr + " ");
                if (curr != 0) {
                    Point p = pointArray[Math.abs(curr)-1];
                    p.count++;
                    if (x==0 || x==maxX-1 || y==0 || y==maxY-1) {
                        p.isInfinite = true;
                    }
                }
            }
        }

        // now get point with highest count where isInfinite == false
        int best = 0;
        for (Point p : points) {
            if (!p.isInfinite && p.count > best) {
                best = p.count;
            }
        }

        System.out.println(best);
    }

    private static Integer fill(int x, int y, Integer[][] grid, int maxX, int maxY, int polarity) {
        Integer best = null;

        // +y
        if ((y+1 < maxY) && (grid[x][y+1] != null) && (grid[x][y+1] != 0) && (grid[x][y+1] * polarity > 0)) {
            Integer curr = grid[x][y+1];
            if (best != null && best != 0 && best != curr) {
                return 0;
            } else {
                best = curr;
            }
        }

        // -y
        if ((y-1 >= 0) && (grid[x][y-1] != null) && (grid[x][y-1] != 0) && (grid[x][y-1] * polarity > 0)) {
            Integer curr = grid[x][y-1];
            if (best != null && best != 0 && best != curr) {
                return 0;
            } else {
                best = curr;
            }
        }

        // +x
        if ((x+1 < maxX) && (grid[x+1][y] != null) && (grid[x+1][y] != 0) && (grid[x+1][y] * polarity > 0)) {
            Integer curr = grid[x+1][y];
            if (best != null && best != 0 && best != curr) {
                return 0;
            } else {
                best = curr;
            }
        }

        // -x
        if ((x-1 >= 0) && (grid[x-1][y] != null) && (grid[x-1][y] != 0) && (grid[x-1][y] * polarity > 0)) {
            Integer curr = grid[x-1][y];
            if (best != null && best != 0 && best != curr) {
                return 0;
            } else {
                best = curr;
            }
        }

        if (best == null) {
            return null;
        } else {
            return (best * -1);
        }
    }

    private static List<Point> readPoints() {
        Scanner sc = Utils.getScanner("day6input.txt");
        Pattern pointPattern = Pattern.compile("([0-9]+), ([0-9]+)");

        List<Point> points = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Matcher lineMatcher = pointPattern.matcher(line);
            lineMatcher.matches();
            int x = Integer.parseInt(lineMatcher.group(1));
            int y = Integer.parseInt(lineMatcher.group(2));
            points.add(new Point(x, y));
        }

        return points;
    }

    private static class Point {
        int x, y;
        int count = 0; // used for tallying at the end
        boolean isInfinite = false; // used for tallying at the end

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static void day6_2() {
        List<Point> points = readPoints();

        // hopefully the whole region will be within this rectangle... pretty sure that's not guaranteed tho...
        // can always just expand the for loop if it's not...
        int minX = points.stream().min(Comparator.comparingInt(p -> p.x)).get().x;
        int maxX = points.stream().max(Comparator.comparingInt(p -> p.x)).get().x;
        int minY = points.stream().min(Comparator.comparingInt(p -> p.y)).get().y;
        int maxY = points.stream().max(Comparator.comparingInt(p -> p.y)).get().y;

        int minDist = 10000;
        int count = 0;

        for (int x=minX; x<=maxX; x++) {
            for (int y=minY; y<=maxY; y++) {
                int totalDist = 0;
                for (Point p : points) {
                    totalDist += dist(p, x, y);
                }
                if (totalDist <= minDist) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    private static int dist (Point p, int x, int y) {
        return Math.abs(p.x - x) + Math.abs(p.y - y);
    }
}
