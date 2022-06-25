package io.demjened.riff.generators;

import io.demjened.riff.model.ChangeType;
import io.demjened.riff.model.RiffData;

import java.util.Collection;

/**
 * Base class for riff generators. Exposes methods for setting up and executing the generation. Subclasses should
 * override select methods they need for their diff process.
 *
 * @param <T> Type of items to diff
 */
public abstract class AbstractRiffGenerator<T> implements RiffGenerator<T> {

    protected final RiffData<T> data;

    public AbstractRiffGenerator() {
        this.data = new RiffData<>();
    }

    @Override
    public RiffData<T> generate() {
        return data;
    }

    /**
     * Sets the left collection. Replaces the previous one if there's one.<br/>
     * Subclasses should override this method and downcast the return type.
     *
     * @param left Left collection
     * @return The generator object
     */
    protected AbstractRiffGenerator<T> setLeft(Collection<T> left) {
        data.getLeft().clear();
        data.getLeft().addAll(left);

        return this;
    }

    /**
     * Sets the right collection. Replaces the previous one if there's one.<br>
     * Subclasses should override this method and downcast the return type.
     *
     * @param right Right collection
     * @return The generator object
     */
    protected AbstractRiffGenerator<T> setRight(Collection<T> right) {
        data.getRight().clear();
        data.getRight().addAll(right);

        return this;
    }

    /**
     * Marks the item as added.
     *
     * @param item The item
     */
    protected void added(T item) {
        changed(item, ChangeType.ADDED);
    }

    /**
     * Marks the item as removed.
     *
     * @param item The item
     */
    protected void removed(T item) {
        changed(item, ChangeType.REMOVED);
    }

    /**
     * Marks the item as modified.
     *
     * @param item The item
     */
    protected void modified(T item) {
        changed(item, ChangeType.MODIFIED);
    }

    /**
     * Marks the item as unmodified.
     *
     * @param item The item
     */
    protected void unmodified(T item) {
        changed(item, ChangeType.UNMODIFIED);
    }

    private void changed(T item, ChangeType changeType) {
        data.getChanges().get(changeType).add(item);
    }

}
