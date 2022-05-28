package io.demjened.riff.model;

import java.util.*;
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

    public Collection<T> getLeft() {
        return left;
    }

    public Collection<T> getRight() {
        return right;
    }

    public Map<ChangeType, Collection<T>> getChanges() {
        return changes;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RiffData.class.getSimpleName() + "[", "]")
                .add("left=" + left)
                .add("right=" + right)
                .add("changes=" + changes)
                .toString();
    }

    private Map<ChangeType, Collection<T>> initializeChanges() {
        return Arrays.stream(ChangeType.values())
                .collect(Collectors.toMap(
                        Function.identity(),
                        (ignore) -> new LinkedHashSet<>()));

    }

}
