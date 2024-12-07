package util;

import java.util.HashMap;
import java.util.Map;

public class FreqMap <T> {

    Map<T, Integer> map = new HashMap<>();

    public void put(T obj) {
        if (map.containsKey(obj)) {
            map.put(obj, map.get(obj) + 1);
        } else {
            map.put(obj, 1);
        }
    }

    public int get(T obj) {
        Integer res = map.get(obj);
        return res == null ? 0 : res;
    }

}
