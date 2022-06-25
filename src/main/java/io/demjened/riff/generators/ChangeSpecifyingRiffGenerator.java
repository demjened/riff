package io.demjened.riff.generators;

/**
 * Riff generator that accepts explicit changes to items and infers the left and right collections from those.
 *
 * @param <T> Type of items to diff
 */
public class ChangeSpecifyingRiffGenerator<T> extends AbstractRiffGenerator<T> {

    @Override
    protected ChangeSpecifyingRiffGenerator<T> added(T item) {
        data.getLeft().remove(item);
        data.getRight().add(item);

        return (ChangeSpecifyingRiffGenerator<T>) super.added(item);
    }

    @Override
    protected ChangeSpecifyingRiffGenerator<T> removed(T item) {
        data.getLeft().add(item);
        data.getRight().remove(item);

        return (ChangeSpecifyingRiffGenerator<T>) super.removed(item);
    }

    @Override
    protected ChangeSpecifyingRiffGenerator<T> modified(T item) {
        data.getLeft().add(item);
        data.getRight().add(item);

        return (ChangeSpecifyingRiffGenerator<T>) super.modified(item);
    }

    @Override
    protected ChangeSpecifyingRiffGenerator<T> unmodified(T item) {
        data.getLeft().add(item);
        data.getRight().add(item);

        return (ChangeSpecifyingRiffGenerator<T>) super.unmodified(item);
    }

}
