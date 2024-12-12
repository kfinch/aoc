package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MultiSet <T> {

    private Map<T, Long> internal = new HashMap<>();

    public long get(T val) {
        Long res = internal.get(val);
        return res == null ? 0 : res;
    }

    public void add(T val, long count) {
        Long curr = internal.get(val);
        if (curr == null) {
            internal.put(val, count);
        } else {
            internal.put(val, curr+count);
        }
    }

    public Set<Map.Entry<T, Long>> entries() {
        return internal.entrySet();
    }

    public long count() {
        return internal.values().stream().reduce(0L, (a,b) -> a + b);
    }

}
