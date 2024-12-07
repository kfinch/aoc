package aoc2024;

import util.FileUtils;

import java.util.HashSet;
import java.util.Set;

public class Day6 {

    public static void main (String args[]) {
        System.out.println("Puzzle A");
        puzzleA();
        System.out.println("Puzzle B");
        puzzleB();
    }

    public static void puzzleA() {
        Board board = getBoard();
        while(true) {
            int destX = board.guardX + board.moveX;
            int destY = board.guardY + board.moveY;
            if (destX < 0 || destX >= board.xDim || destY < 0 || destY >= board.yDim) {
                // we've gone off board
                break;
            } else if (board.obstacles[destY][destX]) {
                // we're going to hit an obstacle -> turn
                if (board.moveY == -1) {
                    board.moveY = 0;
                    board.moveX = 1;
                } else if (board.moveX == 1) {
                    board.moveX = 0;
                    board.moveY = 1;
                } else if (board.moveY == 1) {
                    board.moveY = 0;
                    board.moveX = -1;
                } else if (board.moveX == -1) {
                    board.moveX = 0;
                    board.moveY = -1;
                }
            } else {
                // we're clear ahead -> move
                board.guardX = destX;
                board.guardY = destY;
                board.visited[destY][destX] = true;
            }
        }

        int visitedCount = 0;
        for (int x=0; x<board.xDim; x++) {
            for (int y=0; y<board.yDim; y++) {
                if (board.visited[y][x]) {
                    visitedCount += 1;
                }
            }
        }
        System.out.println(visitedCount);
    }

    public static void puzzleB() {
        Board board = getBoard();
        int guardInitX = board.guardX;
        int guardInitY = board.guardY;
        solveBoard(board);

        int loopSpots = 0;
        for (int x=0; x<board.xDim; x++) {
            for (int y=0; y<board.yDim; y++) {
                // only check the spots guard visits in unmodified route
                if (board.visited[y][x] && !(x==guardInitX && y==guardInitY)) {
                    System.out.println("Trying new board for modification to X=" + x + " Y=" + y);
                    Board modBoard = getBoard();
                    modBoard.obstacles[y][x] = true;
                    boolean hasLoop = !solveBoard(modBoard);
                    if (hasLoop) {
                        loopSpots += 1;
                    }
                }
            }
        }
        System.out.println("Spots: " + loopSpots);
    }

    /**
     * Solves the board
     * @return true iff route finishes, false if route loops
     */
    private static boolean solveBoard(Board board) {
        while(true) {
            int destX=0, destY=0;
            switch (board.guardDirection) {
                case UP:
                    destX = board.guardX;
                    destY = board.guardY-1;
                    break;
                case RIGHT:
                    destX = board.guardX+1;
                    destY = board.guardY;
                    break;
                case DOWN:
                    destX = board.guardX;
                    destY = board.guardY+1;
                    break;
                case LEFT:
                    destX = board.guardX-1;
                    destY = board.guardY;
                    break;
            }

            if (destX < 0 || destX >= board.xDim || destY < 0 || destY >= board.yDim) {
                // we've gone off board
                return true;
            } else if (board.obstacles[destY][destX]) {
                // we're going to hit an obstacle -> turn
                Direction currDir = board.guardDirection;
                switch(currDir) {
                    case UP:
                        board.guardDirection = Direction.RIGHT;
                        break;
                    case RIGHT:
                        board.guardDirection = Direction.DOWN;
                        break;
                    case DOWN:
                        board.guardDirection = Direction.LEFT;
                        break;
                    case LEFT:
                        board.guardDirection = Direction.UP;
                        break;
                }
            } else {
                // we're clear ahead -> move
                board.guardX = destX;
                board.guardY = destY;
                board.visited[destY][destX] = true;
                boolean newDir = board.visitedInDirection[destY][destX].add(board.guardDirection);
                // if we already went this direction on this square, it's a loop!
                if (!newDir) {
                    return false;
                }
            }
        }
    }

    private static Board getBoard() {
        Board board = new Board();
        char[][] input = FileUtils.getCharGrid("2024/day6input.txt");
        board.xDim = input[0].length;
        board.yDim = input.length;
        board.obstacles = new boolean[board.yDim][board.xDim];
        board.visited = new boolean[board.yDim][board.xDim];
        board.visitedInDirection = new Set[board.yDim][board.xDim];
        for (int x=0; x<board.xDim; x++) {
            for (int y=0; y<board.yDim; y++) {
                board.visitedInDirection[y][x] = new HashSet<>();
                char c = input[y][x];
                if (c == '#') {
                    board.obstacles[y][x] = true;
                } else if (c == '^') {
                    board.visited[y][x] = true;
                    board.visitedInDirection[y][x].add(Direction.UP);
                    board.guardX = x;
                    board.guardY = y;
                }
            }
        }
        board.moveX = 0;
        board.moveY = -1;
        board.guardDirection = Direction.UP;
        return board;
    }

    private static class Board {
        int xDim, yDim;
        boolean[][] obstacles;
        boolean[][] visited;
        int guardX, guardY;
        int moveX, moveY;
        // extra stuff / changes for part B
        Direction guardDirection;
        Set<Direction>[][] visitedInDirection;
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

}
