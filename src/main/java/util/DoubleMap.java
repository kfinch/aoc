package util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DoubleMap<K1, K2, V> {

    private final Map<K1, Map<K2, V>> backingMap;

    public DoubleMap()
    {
        this.backingMap = new HashMap<>();
    }

    public void clear()
    {
        backingMap.clear();
    }

    public boolean isEmpty()
    {
        return backingMap.isEmpty();
    }

    public V get(K1 key1, K2 key2)
    {
        Map<K2, V> k1Map = backingMap.get(key1);
        return (k1Map != null)
                ? k1Map.get(key2)
                : null;
    }

    public V put(K1 key1, K2 key2, V value)
    {
        Map<K2, V> k1Map = backingMap.putIfAbsent(key1, new HashMap<>());
        return k1Map.put(key2, value);
    }

    public int size()
    {
        return backingMap.values().stream()
                .mapToInt(Map::size).sum();
    }

    public Collection<V> values()
    {
        return backingMap.values().stream()
                .flatMap(map -> map.values().stream())
                .collect(Collectors.toList());
    }



}
