package io.demjened.riff.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO: complete
 * @param <T> Type of elements to compare
 */
public class RiffData<T> {

    private final Collection<T> left;
    private final Collection<T> right;
    private final Map<ChangeType, Collection<T>> changes;

    public RiffData() {
        this.left = new LinkedHashSet<>();
        this.right = new LinkedHashSet<>();
        this.changes = initializeChanges();
    }

    private Map<ChangeType, Collection<T>> initializeChanges() {
        return Arrays.stream(ChangeType.values())
                .collect(Collectors.toMap(
                        Function.identity(),
                        (ignore) -> new LinkedHashSet<>()));

    }

}
