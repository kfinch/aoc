package util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SparseGrid <T> {

    Map<String,T> locPackToData = new HashMap<>();

    public T add(T data, int x, int y) {
        return locPackToData.put(packCoords(x, y), data);
    }

    public T get(int x, int y) {
        return locPackToData.get(packCoords(x, y));
    }

    public Collection<T> getAll() {
        return locPackToData.values();
    }

    private int[] getCoords(String pack) {
        String[] coordStrings = pack.split(",");
        return new int[] { Integer.parseInt(coordStrings[0]), Integer.parseInt(coordStrings[1]) };
    }

    private String packCoords(int x, int y) {
        return x + "," + y;
    }

}
