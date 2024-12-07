package util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Memoizer {

    public static <A, R> Function<A, R> memoizeFunction(Function<A, R> fn)
    {
        return new Function<A, R>() {

            Map<A, R> cache = Collections.synchronizedMap(new HashMap<>());

            @Override
            public R apply(A a) {
                return cache.computeIfAbsent(a, aa -> fn.apply(a));
            }
        };
    }


}
