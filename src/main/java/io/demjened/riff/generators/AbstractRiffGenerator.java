package io.demjened.riff.generators;

import io.demjened.riff.config.RiffConfig;
import io.demjened.riff.model.ChangeType;
import io.demjened.riff.model.RiffData;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * Base class for riff generators. Exposes methods for setting up and executing the generation. Subclasses should
 * override select methods they need for their diff process.
 *
 * @param <T> Type of items to diff
 */
public abstract class AbstractRiffGenerator<T> implements RiffGenerator<T> {

    private final RiffConfig<T> defaultConfig = new RiffConfig<>();

    protected final RiffData<T> data;
    protected RiffConfig<T> config;

    public AbstractRiffGenerator() {
        this.data = new RiffData<>();
        this.config = defaultConfig;
    }

    @Override
    public RiffData<T> generate() {
        return data;
    }

    /**
     * Sets the riff config that controls the comparison logic.<br/>
     * Subclasses should override this method and downcast the return type.
     *
     * @param config The config
     * @return This generator object
     */
    protected AbstractRiffGenerator<T> withConfig(RiffConfig<T> config) {
        this.config = config;

        return this;
    }

    /**
     * Sets the left collection. Replaces the previous one if there's one.<br/>
     * Subclasses should override this method and downcast the return type.
     *
     * @param left Left collection
     * @return This generator object
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
     * @return This generator object
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
     * @return This generator object
     */
    protected AbstractRiffGenerator<T> added(T item) {
        changed(item, ChangeType.ADDED);

        return this;
    }

    /**
     * Marks the item as removed.
     *
     * @param item The item
     * @return This generator object
     */
    protected AbstractRiffGenerator<T> removed(T item) {
        changed(item, ChangeType.REMOVED);

        return this;
    }

    /**
     * Marks the item as modified.
     *
     * @param item The item
     * @return This generator object
     */
    protected AbstractRiffGenerator<T> modified(T item) {
        changed(item, ChangeType.MODIFIED);

        return this;
    }

    /**
     * Marks the item as unmodified.
     *
     * @param item The item
     * @return This generator object
     */
    protected AbstractRiffGenerator<T> unmodified(T item) {
        changed(item, ChangeType.UNMODIFIED);

        return this;
    }

    private void changed(T item, ChangeType changeType) {
        Function<T, T> cloner = Optional.ofNullable(config.getCloner())
                .orElse(Function.identity());

        data.getChanges().get(changeType).add(cloner.apply(item));
    }

}
