package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    public static final ClassLoader loader = FileUtils.class.getClassLoader();

    public static Scanner getScanner(String fileName) {
        InputStream stream = loader.getResourceAsStream(fileName);
        return new Scanner(stream);
    }

    public static List<String> getTokenList(String fileName) {
        Scanner sc = getScanner(fileName);
        List<String> result = new ArrayList<>();
        while (sc.hasNext()) {
            result.add(sc.next());
        }
        return result;
    }

    public static List<String> getLineList(String fileName) {
        Scanner sc = getScanner(fileName);
        List<String> result = new ArrayList<>();
        while (sc.hasNextLine()) {
            result.add(sc.nextLine());
        }
        return result;
    }

    public static List<List<String>> getTokensByLine(String fileName) {
        Scanner sc = getScanner(fileName);
        List<List<String>> result = new ArrayList<>();
        while (sc.hasNextLine()) {
            List<String> lineTokens = new ArrayList<>();
            Scanner lineScanner = new Scanner(sc.nextLine());
            while (lineScanner.hasNext()) {
                lineTokens.add(lineScanner.next());
            }
            result.add(lineTokens);
        }
        return result;
    }

    public static StringBuilder getAsStringBuilder(String fileName) {
        Scanner sc = getScanner(fileName);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        return sb;
    }

    public static char[][] getCharGrid(String fileName) {
        List<String> lines = getTokenList(fileName);
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
}
