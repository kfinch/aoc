package aoc2024;

import util.FileUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day9 {

    public static void main (String args[]) {
        System.out.println("Puzzle A: " + puzzleA());
        System.out.println("Puzzle B: " + puzzleB());
    }

    public static String puzzleA() {
        ArrayList<Integer> blocks = readAndParseBlocks();

        // pack the blocks
        int emptyCursor = 0;
        int fileCursor = blocks.size()-1;
        while (true) {
            // scan cursors to next valid spot
            while (emptyCursor < blocks.size() && blocks.get(emptyCursor) != -1) {
                emptyCursor++;
            }
            while (fileCursor >= 0 && blocks.get(fileCursor) == -1) {
                fileCursor--;
            }
            if (emptyCursor >= fileCursor) {
                break;
            }
            // swap
            int swap = blocks.get(emptyCursor);
            blocks.set(emptyCursor, blocks.get(fileCursor));
            blocks.set(fileCursor, swap);
            // move cursors on
            emptyCursor++;
            fileCursor--;
        }

        //compute checksum
        long checksum = 0;
        for (int i=0; i<blocks.size(); i++) {
            int id = blocks.get(i);
            if (id == -1) {
                break;
            }
            checksum += i * id;
        }

        return "" + checksum;
    }

    public static String puzzleB() {
        List<Block> blocks = readAndParseBlocksB();
        for (int id=blocks.size()-1; id>0; id--) {
            // find selected block index
            int i=0;
            for (; i<blocks.size(); i++) {
                if (blocks.get(i).id == id) {
                    break;
                }
            }
            // scan for a viable place to put it
            for (int j=1; j<=i; j++) {
                if (blocks.get(j).startPos - blocks.get(j-1).startPos - blocks.get(j-1).size >= blocks.get(i).size) {
                    // viable location found - update startPos
                    blocks.get(i).startPos = blocks.get(j-1).startPos + blocks.get(j-1).size;
                    // resort the list and continue
                    blocks.sort(Comparator.comparingInt(b -> b.startPos));
                    break;
                }
            }
        }

        //compute checksum
        long checksum = 0;
        for (Block block : blocks) {
            for (int i=0; i<block.size; i++) {
                checksum += block.id * (block.startPos + i);
            }
        }

        return "" + checksum;
    }

    private static ArrayList<Integer> readAndParseBlocks() {
        String codeString = FileUtils.getScanner("2024/day9input.txt").next();
        ArrayList<Integer> blocks = new ArrayList<>();
        boolean isFile = true;
        int fileId = 0;
        for (int i=0; i<codeString.length(); i++) {
            int size = Integer.parseInt(codeString.substring(i, i+1));
            int numToPopulate = fileId;
            if (isFile) {
                fileId++;
            } else {
                numToPopulate = -1;
            }
            for (int n=0; n<size; n++) {
                blocks.add(numToPopulate);
            }
            isFile = !isFile;
        }
        return blocks;
    }

    private static List<Block> readAndParseBlocksB() {
        String codeString = FileUtils.getScanner("2024/day9input.txt").next();
        List<Block> blocks = new ArrayList<>();
        int fileId = 0;
        int currIndex = 0;
        boolean isFile = true;
        for (int i=0; i<codeString.length(); i+=1) {
            int size = Integer.parseInt(codeString.substring(i, i+1));
            if (isFile) {
                blocks.add(new Block(currIndex, size, fileId));
                fileId++;
            }
            currIndex+=size;
            isFile = !isFile;
        }
        return blocks;
    }

    private static class Block {
        int startPos;
        int size;
        int id;
        private Block (int startPos, int size, int id) {
            this.startPos = startPos;
            this.size = size;
            this.id = id;
        }
    }

}
