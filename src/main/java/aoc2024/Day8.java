package aoc2024;

import util.FileUtils;
import util.SparseGrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {

    public static void main (String args[]) {
        System.out.println("Puzzle A: " + puzzleA());
        System.out.println("Puzzle B: " + puzzleB());
    }

    public static String puzzleA() {
        AntennaGrid grid = parseGrid();
        SparseGrid<Boolean> antiNodes = new SparseGrid<>();

        for (List<Antenna> freqAntennas : grid.antennasByFreq.values()) {
            for (int a=0; a<freqAntennas.size(); a++) {
                for (int b=0; b<freqAntennas.size(); b++) {
                    if (a!=b) {
                        Antenna aAnt = freqAntennas.get(a);
                        Antenna bAnt = freqAntennas.get(b);
                        int antiX = (aAnt.x - bAnt.x) + aAnt.x;
                        int antiY = (aAnt.y - bAnt.y) + aAnt.y;
                        if (antiX >= 0 && antiX < grid.xDim && antiY >= 0 && antiY < grid.yDim) {
                            antiNodes.add(true, antiX, antiY);
                        }
                    }
                }
            }
        }

        return "" + antiNodes.getAll().size();
    }

    public static String puzzleB() {
        AntennaGrid grid = parseGrid();
        SparseGrid<Boolean> antiNodes = new SparseGrid<>();

        for (List<Antenna> freqAntennas : grid.antennasByFreq.values()) {
            for (int a=0; a<freqAntennas.size(); a++) {
                for (int b=0; b<freqAntennas.size(); b++) {
                    if (a!=b) {
                        Antenna aAnt = freqAntennas.get(a);
                        Antenna bAnt = freqAntennas.get(b);
                        int diffX = aAnt.x - bAnt.x;
                        int diffY = aAnt.y - bAnt.y;
                        int antiX = aAnt.x;
                        int antiY = aAnt.y;
                        while (antiX >= 0 && antiX < grid.xDim && antiY >= 0 && antiY < grid.yDim) {
                            antiNodes.add(true, antiX, antiY);
                            antiX += diffX;
                            antiY += diffY;
                        }
                    }
                }
            }
        }

        return "" + antiNodes.getAll().size();
    }

    private static AntennaGrid parseGrid() {
        char[][] charGrid = FileUtils.getCharGrid("2024/day8input.txt");
        AntennaGrid grid = new AntennaGrid();
        grid.xDim = charGrid[0].length;
        grid.yDim = charGrid.length;
        for (int y=0; y<grid.yDim; y++) {
            for (int x=0; x<grid.xDim; x++) {
                if (charGrid[y][x] != '.') {
                    char freq = charGrid[y][x];
                    Antenna antenna = new Antenna();
                    antenna.x = x;
                    antenna.y = y;
                    antenna.freq = freq;
                    List<Antenna> freqAntennas = grid.antennasByFreq.computeIfAbsent(freq, f -> new ArrayList<>());
                    freqAntennas.add(antenna);
                }
            }
        }
        return grid;
    }

    private static class AntennaGrid {
        int xDim, yDim;
        Map<Character, List<Antenna>> antennasByFreq = new HashMap<>();
    }

    private static class Antenna {
        int x, y;
        char freq;
    }
}
