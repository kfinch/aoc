package aoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static final ClassLoader loader = Utils.class.getClassLoader();

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
}
