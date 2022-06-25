package io.demjened.riff.generators;

import io.demjened.riff.config.RiffConfig;
import io.demjened.riff.model.RiffData;

import java.util.Collection;

/**
 * Riff generator that accepts two collections: left (before) and right (after). When generating the riff it identifies
 * the change to each item by comparing its before/after state.
 *
 * @param <T> Type of items to diff
 */
public class ComparingRiffGenerator<T> extends AbstractRiffGenerator<T> {

    @Override
    protected AbstractRiffGenerator<T> withConfig(RiffConfig<T> config) {
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
        // Items only in right are marked as added
        // Items both in left and right are marked as unmodified
        // TODO: Optimize lookup with a pre-generated map
        data.getLeft().forEach(leftItem -> {
            if (data.getRight().contains(leftItem)) {
                T rightItem = data.getRight().stream()
                        .filter(item -> item.equals(leftItem))
                        .findFirst()
                        .get();

                // Items both equal and deep equal are marked as unmodified
                // Items equal but not deep equal are marked as modified
                if (config.hasDeepEqualityCheck() && !config.getDeepEqualityCheck().apply(leftItem, rightItem)) {
                    modified(leftItem);
                } else {
                    unmodified(leftItem);
                }
            } else {
                removed(leftItem);
            }
        });

        // Items only in left are marked as removed
        data.getRight().stream()
                .filter(rightItem -> !data.getLeft().contains(rightItem))
                .forEach(this::added);

        return data;
    }

}
