package io.demjened.riff.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils {

    private CollectionUtils() {}

    /**
     * Converts the supplied collection into a map in which each element acts as its own key.
     *
     * @param coll The collection
     * @return Converted map
     * @param <T> Type of elements
     */
    public static <T> Map<T, T> mapify(Collection<T> coll) {
        return coll.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        Function.identity()));
    }

}
