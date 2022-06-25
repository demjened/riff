package io.demjened.riff.generators;

import io.demjened.riff.config.RiffConfig;
import io.demjened.riff.model.RiffData;
import io.demjened.riff.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Riff generator that accepts two collections: left (before) and right (after). When generating the riff it identifies
 * the change to each item by comparing its before/after state.
 *
 * @param <T> Type of items to diff
 */
public class ComparingRiffGenerator<T> extends AbstractRiffGenerator<T> {

    @Override
    protected ComparingRiffGenerator<T> withConfig(RiffConfig<T> config) {
        return (ComparingRiffGenerator<T>) super.withConfig(config);
    }

    @Override
    protected ComparingRiffGenerator<T> setLeft(Collection<T> left) {
        return (ComparingRiffGenerator<T>) super.setLeft(left);
    }

    @Override
    protected ComparingRiffGenerator<T> setRight(Collection<T> right) {
        return (ComparingRiffGenerator<T>) super.setRight(right);
    }

    @Override
    public RiffData<T> generate() {
        Map<T, T> rightItemsBySelf = CollectionUtils.mapify(data.getRight());

        // Items only in right are marked as added
        // Items both equal and deep equal are marked as unmodified
        // Items equal but not deep equal are marked as modified
        data.getLeft().forEach(leftItem -> Optional.ofNullable(rightItemsBySelf.get(leftItem))
                .ifPresentOrElse(rightItem -> evaluateModified(leftItem, rightItem), () -> removed(leftItem)));

        // Items only in left are marked as removed
        data.getRight().stream()
                .filter(rightItem -> !data.getLeft().contains(rightItem))
                .forEach(this::added);

        return data;
    }

    private void evaluateModified(T leftItem, T rightItem) {
        Optional.ofNullable(config.getDeepEqualityCheck())
                .filter(deepEqualityCheck -> !deepEqualityCheck.apply(leftItem, rightItem))
                .map(ignore -> modified(leftItem))
                .orElseGet(() -> unmodified(leftItem));
    }

}
