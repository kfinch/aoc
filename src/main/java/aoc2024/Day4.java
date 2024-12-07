package aoc2024;

import util.FileUtils;

import java.util.List;

public class Day4 {

    public static void main (String args[]) {
        System.out.println("Puzzle 1");
        puzzle1();
        System.out.println("Puzzle 2");
        puzzle2();
    }

    public static void puzzle1() {
        char[][] input = readAndParseInput();
        int rows = input.length;
        int cols = input[0].length;
        int count = 0;
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                count += getMatchesAt(input, rows, cols, i, j);
            }
        }
        System.out.println(count);
    }

    public static void puzzle2() {
        char[][] input = readAndParseInput();
        int rows = input.length;
        int cols = input[0].length;
        int count = 0;
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                count += getMatches2At(input, rows, cols, i, j);
            }
        }
        System.out.println(count);
    }

    private static char[][] readAndParseInput()
    {
        List<String> lines = FileUtils.getTokenList("2024/day4input.txt");
        int rows = lines.size();
        int cols = lines.get(0).length();

        char[][] result = new char[rows][cols];
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                result[i][j] = lines.get(i).charAt(j);
            }
        }
        return result;
    }

    private static int getMatchesAt(char[][] input, int rowCount, int colCount, int row, int col) {
        if (input[row][col] != 'X') {
            return 0;
        }
        int matches = 0;
        for (int rowOffset = -1; rowOffset<=1; rowOffset++) {
            for (int colOffset = -1; colOffset<=1; colOffset++) {
                matches += getMatchAt(input, rowCount, colCount, row, col, rowOffset, colOffset);
            }
        }

        return matches;
    }

    private static int getMatchAt(char[][] input, int rowCount, int colCount, int row, int col, int rowOffset, int colOffset) {
        int finalRow = row + rowOffset*3;
        if (finalRow < 0 || finalRow >= rowCount) {
            return 0;
        }
        int finalCol = col + colOffset*3;
        if (finalCol < 0 || finalCol >= colCount) {
            return 0;
        }

        // assume already checked start point for 'X'
        if (input[row+rowOffset*1][col+colOffset*1] == 'M' &&
            input[row+rowOffset*2][col+colOffset*2] == 'A' &&
            input[row+rowOffset*3][col+colOffset*3] == 'S') {
            return 1;
        }
        return 0;
    }

    private static int getMatches2At(char[][] input, int rowCount, int colCount, int row, int col) {
        if (row == 0 || col == 0 || row == rowCount-1 || col == colCount-1) {
            return 0;
        }
        if (input[row][col] != 'A') {
            return 0;
        }
        if ((input[row-1][col-1] == 'M' && input[row+1][col+1] == 'S' || input[row-1][col-1] == 'S' && input[row+1][col+1] == 'M') &&
            (input[row+1][col-1] == 'M' && input[row-1][col+1] == 'S' || input[row+1][col-1] == 'S' && input[row-1][col+1] == 'M')) {
            return 1;
        }
        return 0;
    }

}
